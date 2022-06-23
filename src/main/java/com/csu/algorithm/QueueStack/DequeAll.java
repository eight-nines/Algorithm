package com.csu.algorithm.QueueStack;

import java.util.*;

public class DequeAll {


    class MyStack {

        //两个队列实现栈：1主负责存，2辅负责中转
        Deque<Integer> q1, q2, temp;

        public MyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        public void push(int x) {
            //增：添加到q1中
            q1.offer(x);
        }

        public int pop() {
            //弹：q1弹出到q2中，最后一个就是目标
            if (q1.isEmpty()) return -1;

            while (q1.size() != 1) q2.offer(q1.poll());
            int res = q1.poll();
            temp = q1;
            q1 = q2;
            q2 = temp;//交换引用
            return res;
        }

        public int top() {
            //复用pop代码
            if (q1.isEmpty()) return -1;
            int res = this.pop();
            this.push(res);
            return res;
        }

        public boolean empty() {
            return q1.isEmpty() && q2.isEmpty();
        }
    }


    public boolean isValid(String s) {
        //栈：遇到右括号，比较栈顶，匹配则弹栈
        Deque<Character> stack = new LinkedList<>();

        char first = s.charAt(0);//先判断第一个不能是右括号
        if (first == ')' || first == ']' || first == '}') return false;

        for (char c : s.toCharArray()) {
            //遇到右括号 尝试匹配消去括号 前提是栈非空
            if (c == ')' && !stack.isEmpty()
                    && stack.peek() == '(') stack.pop();
            else if (c == ']' && !stack.isEmpty()
                    && stack.peek() == '[') stack.pop();
            else if (c == '}' && !stack.isEmpty()
                    && stack.peek() == '{') stack.pop();
            else stack.push(c);
        }
        return stack.isEmpty();
    }


    public String removeDuplicates(String s) {
        //栈：遍历比较栈顶，弹栈倒序赋值
        Deque<Character> stack = new LinkedList<>();

        //消消乐
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && c == stack.peek()) stack.pop();
            else stack.push(c);
        }
        //弹栈倒序赋值
        char[] res = new char[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }

        return new String(res);
    }

    public int evalRPN(String[] tokens) {

        //栈：遇到符号弹栈两个，并用符号连接计算，再入栈
        Deque<Integer> stack = new LinkedList<>();
        int i1, i2, res;

        for (String c : tokens) {
            if (c.equals("+") || c.equals("-")
                    || c.equals("*") || c.equals("/")) {
                //统一弹栈
                i1 = stack.pop();
                i2 = stack.pop();

                if (c.equals("+")) stack.push(i2 + i1);
                if (c.equals("-")) stack.push(i2 - i1);
                if (c.equals("*")) stack.push(i2 * i1);
                if (c.equals("/")) stack.push(i2 / i1);
            } else stack.push(Integer.valueOf(c)); //数字
        }
        return stack.pop();
    }




    public int[] maxSlidingWindow(int[] nums, int k) {
        //单调队列：数字依次添加到队列中，队列单调递减，每次返回队首值
        //双端队列，前出：当前窗口最大值；后出：添加时末尾小于当前值
        Deque<Integer> queue = new LinkedList<>();

        int[] res = new int[nums.length - k + 1];

        //移动窗口记录最大值
        for (int i = 0; i < nums.length; i++) {
            //添加之前，先把小于当前元素的元素移除队列
            while (!queue.isEmpty() && queue.peekLast() < nums[i])
                queue.pollLast();

            //添加当前元素
            queue.offer(nums[i]);

            //移动窗口，如果最左侧值是窗口最大值，要删除
            if (i >= k && nums[i - k] == queue.peek())
                queue.poll();

            //统计结果
            if (i >= k - 1) res[i - k + 1] = queue.peek();
        }

        return res;
    }

    class MinStack {
        //双栈：1栈存数据，2栈存当前栈最小值
        Deque<Integer> s1, s2;

        public MinStack() {
            s1 = new LinkedList<>();
            s2 = new LinkedList<>();
        }

        public void push(int val) {
            //存：1栈直接存，2栈需要对比和栈顶数据的大小
            s1.push(val);//2栈不为空，且栈顶元素小于压栈值
            if (!s2.isEmpty() && val > s2.peek()) {
                val = s2.peek();
            }
            s2.push(val);
        }

        public void pop() {
            //弹：直接弹
            if (!s1.isEmpty()) {
                s1.pop();
                s2.pop();
            }
        }

        public int top() {//数据栈顶
            if (!s1.isEmpty()) return s1.peek();
            return -1;
        }

        public int getMin() {//最小值栈顶
            if (!s2.isEmpty()) return s2.peek();
            return -1;
        }
    }








    public int[] dailyTemperatures(int[] temperatures) {
        //单调递减栈：栈内存放对应元素的下标
        //遍历中对比栈顶元素，大于栈顶：栈顶可以弹出了，并得到结果（下标差）
        //小于栈顶：继续加入
        int len = temperatures.length;
        Deque<Integer> stack = new ArrayDeque<>(len);
        int[] res = new int[len];

        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() &&
                    temperatures[stack.peek()] < temperatures[i]) {
                res[stack.peek()] = i - stack.peek();//计算结果
                stack.pop();//弹栈
            }
            stack.push(i);
        }

        return res;
    }






    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        //单调递减栈：对nums1在map中建立映射<num,index>,便于查找和记录
        //nums2遍历，比栈顶小，直接放；比栈顶大，栈顶弹栈查map，如果有，记录
        int[] res = new int[nums1.length];
        Arrays.fill(res, -1);//默认值是-1

        //先对nums1在map中建立映射<num,index>
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) map.put(nums1[i], i);

        //单调递减栈
        Deque<Integer> stack = new ArrayDeque<>(nums2.length);

        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                int cur = stack.pop();
                if (map.containsKey(cur))
                    res[map.get(cur)] = nums2[i];
            }
            stack.push(nums2[i]);//进栈
        }
        return res;
    }

    public int[] nextGreaterElements(int[] nums) {
        //单调递减栈：放下标，搜两遍数组(只进一次栈)如果有大于自己的必能搜到
        int len = nums.length;
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Deque<Integer> st = new LinkedList<>();

        for (int i = 0; i < 2 * len - 1; i++) {
            int cur = i % len;//当前下标
            while (!st.isEmpty() && nums[st.peek()] < nums[cur]) {
                res[st.pop()] = nums[cur];
            }
            if (i < len) st.push(cur);//避免重复进栈
        }
        return res;
    }



    public int trap(int[] height) {
        //单调递减栈：存下标，小放；大弹，形成一个凹槽(大、小、大),记录面积
        // l m i 其中m弹栈，h=min(i,l)-m  s = h*w
        Deque<Integer> st = new ArrayDeque<>();
        int res = 0;

        for (int i = 0; i < height.length; i++) {
            while (!st.isEmpty() && height[i] > height[st.peek()]) {
                int mid = st.pop(); //凹槽的底部高度
                if (st.isEmpty()) break;
                int left = st.peek(); //凹槽的左侧下标
                if (height[left] == height[mid]) continue;//剪枝
                int h = Math.min(height[left], height[i]) - height[mid];//水的高度
                int w = i - left - 1;//凹槽底部/水的宽度 = 右-左-1
                res += h * w;
            }
            st.push(i);//下标进栈
        }
        return res;
    }




    public int largestRectangleArea(int[] heights) {
        //递增栈：存下标，大放；小弹，形成小大小山峰，依次记录最大面积(峰值*宽度)
        Deque<Integer> st = new LinkedList<>();
        int res = 0, len = heights.length;
        int[] arr = new int[len + 2];//确保有最少3个值
        System.arraycopy(heights, 0, arr, 1, len);

        for (int i = 0; i < len + 2; i++) {
            while (!st.isEmpty() && arr[i] < arr[st.peek()]) {
                int mid = st.pop();//峰值的高度
                if (st.isEmpty()) break;
                int left = st.peek();//峰值的左侧下标
                int h = arr[mid];//高度从峰值向左遍历
                int w = i - left - 1;//最小值为1，即峰值的宽度
                res = Math.max(res, h * w);
            }
            st.push(i);
        }
        return res;
    }


    public String decodeString(String s) {
        //双栈：'['压；']'弹，当前字符串 = 弹栈字符串+(弹栈数字*当前字符串)
        //数字栈：依次存放遇到的数字，直到遇到']'弹栈
        Deque<Integer> nums = new LinkedList<>();
        //字符串栈：存放遇到的字符组成的字符串，'['新建，']'弹栈
        Deque<String> strs = new LinkedList<>();
        StringBuilder str = new StringBuilder();
        int num = 0;//存放当前数字，用以拼接多位数

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) num = num * 10 + c - '0';//记录数字

            else if (c == '[') {//左括号，数字压栈，当前字符串压栈
                nums.push(num);
                strs.push(str.toString());
                num = 0;
                str = new StringBuilder();
            } else if (c == ']') {//右括号，字符串弹栈+(数字弹栈*当前字符串)
                StringBuilder tem = new StringBuilder(strs.pop());
                int count = nums.pop();
                while (count-- > 0) tem.append(str);
                str = tem;
            } else str.append(c);//字符直接拼接
        }
        return str.toString();
    }


    public boolean find132pattern(int[] nums) {
        //递减栈：从后向前存元素，小放，大弹更新2，保证右侧存在32对
        //遇到小于2的，返回true，2尽可能大，未更新前是最小值
        Deque<Integer> st = new LinkedList<>();
        int num2 = Integer.MIN_VALUE;

        //从后向前遍历：为了先找到num3+num2组合，其中num2可以不断
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < num2) return true;
            while (!st.isEmpty() && st.peek() < nums[i]) {
                num2 = st.pop();//更新num2,尽可能大
            }
            st.push(nums[i]);//元素进栈
        }
        return false;
    }

    class Solution {
        public int maximalRectangle(char[][] matrix) {
            //从上到下统计每个位置的柱子高度，再求解当前情况的最大面积
            int max = 0, m = matrix.length, n = matrix[0].length;
            int[] heights = new int[n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] - '0' == '0') heights[j] = 0;
                    else heights[j] += matrix[i][j] - '0';
                }
                max = Math.max(max, largestRectangleArea(heights));
            }
            return max;
        }

        int largestRectangleArea(int[] heights) {
            Deque<Integer> st = new LinkedList<>();
            int res = 0, len = heights.length;
            int[] arr = new int[len + 2];//确保有最少3个值
            System.arraycopy(heights, 0, arr, 1, len);

            for (int i = 0; i < len + 2; i++) {
                while (!st.isEmpty() && arr[i] < arr[st.peek()]) {
                    int mid = st.pop();//峰值的高度
                    if (st.isEmpty()) break;
                    int left = st.peek();//峰值的左侧下标
                    int h = arr[mid];//高度从峰值向左遍历
                    int w = i - left - 1;//最小值为1，即峰值的宽度
                    res = Math.max(res, h * w);
                }
                st.push(i);
            }
            return res;
        }
    }





    public int[] topKFrequent(int[] nums, int k) {
        //小顶堆：先用map统计元素的对应频次，再入堆排序，超过k后 每次弹出最小的
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums)//统计元素的频次
            map.put(num, map.getOrDefault(num, 0) + 1);

        //小顶堆：按频率正序排序
        PriorityQueue<Map.Entry<Integer, Integer>> queue
                = new PriorityQueue<>((o1, o2) -> o1.getValue() - o2.getValue());

        //入堆排序
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            queue.offer(entry);
            if (queue.size() > k) queue.poll();
        }

        //赋值返回
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = queue.poll().getKey();
        }

        return res;
    }


    class MyQueue {

        //两个栈实现队列，1主负责进，2辅负责出
        Deque<Integer> s1, s2;

        public MyQueue() {
            s1 = new LinkedList<>();
            s2 = new LinkedList<>();
        }

        public void push(int x) {
            //添加：都添加到1中
            s1.push(x);
        }

        public int pop() {
            //出队列：2为空，把1中的倒进2中，否则弹2
            if (s2.isEmpty() && s1.isEmpty()) return -1;
            else if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.pop();
        }

        public int peek() {
            //代码复用，尽量不要复制粘贴，避免代码混乱
            if (s2.isEmpty() && s1.isEmpty()) return -1;
            int res = this.pop();
            s2.push(res); //s2，而不是this，下次继续弹res
            return res;
        }

        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }


}
