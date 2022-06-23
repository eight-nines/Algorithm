package com.csu.greedy;

import java.util.*;
import java.util.stream.Collectors;

public class GreedyAll {


    public int canCompleteCircuit(int[] gas, int[] cost) {

        int len = gas.length;
        int sum = 0;
        int[] num = new int[len];//记录每个位置出发的油耗差
        for (int i = 0; i < len; i++) {
            num[i] = gas[i] - cost[i];
            sum += num[i];
        }
        if (sum < 0) return -1;// 总油量小于总的消耗，无解

        //到这里就确定有解，从前向后搜索
        int tank = 0, start = 0;// 记录油箱中的油量、起点
        for (int i = 0; i < len; i++) {
            tank += num[i];//加上i点出发的油耗差，目的地是i+1
            //从start出发走不到 i+1 ，中间的任何位置也走不到
            //故，从 i+1 开始走，tank归0
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }

        return start;
    }


    public int findContentChildren(int[] g, int[] s) {
        //贪心：正序排序，先满足小胃口，满足不了用更大的饼干
        Arrays.sort(g);
        Arrays.sort(s);//都正序排序
        int i = 0, j = 0;
        for (; i < s.length && j < g.length; i++) {
            //可以满足，都移动;不然只移动饼干
            if (s[i] >= g[j]) j++;
        }
        return j;
    }

    class Solution {
        public int maxProfit(int[] prices) {
            //贪心：挣差价，计算每天的差价，并返回最大连续和
            int res = 0, pre = 0;

            for (int i = 1; i < prices.length; i++) {
                int profit = prices[i] - prices[i - 1];//差价
                if (pre > 0) profit = pre + profit;//累加

                res = Math.max(res, profit);//更新结果
                pre = profit;//更新指针
            }
            return res;
        }
    }

    public int findMinArrowShots(int[][] points) {
        //按照右端点排序, 然后每次从最小的右端点射出一支箭
        //去掉被射爆的气球, 重复该过程
        //注意不可用(a, b) -> (a[1] - b[1]) 会越界
        Arrays.sort(points, (a, b) -> a[1] > b[1] ? 1 : -1);

        int res = 1; //先取最右侧气球区间为一个箭
        int end = points[0][1]; //当前区间右端点
        for (int[] point : points) {
            if (point[0] > end) { //新气球左端点大于之前的右端点
                res++; //再来一只箭，更新右端点
                end = point[1];
            }
        }
        return res;
    }

    public int maxProfit(int[] prices, int fee) {
        //贪心：若能卖，持续卖(中间状态要抵消多的手续费)，否则找最佳买入时机
        int min = prices[0], res = 0;

        for (int i = 1; i < prices.length; i++) {
            //不能卖，找合适的买入时机
            if (prices[i] < min) min = prices[i];
            else if (prices[i] - min > fee) {//卖，或买-卖的中间状态
                res += prices[i] - min - fee;//连续买卖多收手续费
                min = prices[i] - fee;//中间状态，抵消多的手续费
            }
        }
        return res;
    }



    public List<Integer> partitionLabels(String s) {
        //贪心：新建数组统计每个字母最终出现的下标
        //遍历并更新当前片段最远距离，直到尽头，算一个片段
        int len = s.length();
        int[] nums = new int[26];
        for (int i = 0; i < len; i++) {
            nums[s.charAt(i) - 'a'] = i;//更新字母右边界下标
        }

        ArrayList<Integer> res = new ArrayList<>(len);
        int start = 0, maxEnd = 0;//片段起始位置，片段字母最远右边界
        for (int i = 0; i < len; i++) {//先更新边界
            maxEnd = Math.max(maxEnd, nums[s.charAt(i) - 'a']);
            if (maxEnd == i) {//到达边界，添加结果，更新新片段起点
                res.add(i - start + 1);
                start = i + 1;
            }
        }
        return res;
    }


    public int maxProfit(int[] prices) {
        //贪心：目标是想赚正差价，差价正则买，负则不买
        //连续买卖相当于没卖（无手续费）
        int res = 0;

        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - prices[i - 1];//差价
            if (profit > 0) res += profit;
        }
        return res;
    }

    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        // 贪心：只要当前位置能种，就直接种
        // 如果花种完了，或者花床检查完了，都停止遍历
        for (int i = 0, len = flowerbed.length; i < len && n > 0; ) {
            // 遍历时，每个位置的前一个位置一定是 0 （起点除外）
            if (flowerbed[i] == 1) {
                //当前为1，下一个可以种花的位置是 i+2 即：101 or 100
                i += 2;
            } else if (i == flowerbed.length - 1 ||
                    flowerbed[i + 1] == 0) {
                //最后一个位置肯定能够种植
                //否则，需要确保紧邻其后的的位置没有种植
                n--;
                i += 2;
            } else {
                //当前为 0 ，后一个位置为 1 ，直接跳到1的下一个0
                i += 3; // 0101 or 0100
            }
        }
        return n == 0;
    }


    public int candy(int[] ratings) {
        //贪心：从左到右，右边评分高的 给左+1个糖，否则给1个
        //再从右到左，左边评分高且糖少的 给右+1个糖

        int len = ratings.length;
        int[] nums = new int[len];//记录每一位孩子得到的糖果数
        //从左到右，后大 = 前+1，否则给1
        nums[0] = 1;
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) nums[i] = nums[i - 1] + 1;
            else nums[i] = 1;
        }

        //从右到左，前大但糖不大+1
        int res = nums[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && nums[i] <= nums[i + 1])
                nums[i] = nums[i + 1] + 1;
            res += nums[i];
        }
        return res;
    }


    //围坐成一圈
    public int candy1(int[] ratings) {
        if (ratings == null || ratings.length < 1) return 0;

        int len = ratings.length;
        int[] nums = new int[len + 2];//记录每一位孩子得到的糖果数

        for (int i = 0; i <= len + 1; i++) nums[i] = 1;

        boolean flag;
        do {
            flag = false;
            //正序，后大 = 前+1，否则给1
            for (int i = 1; i <= len; i++) {
                if (ratings[i] > ratings[i - 1] && nums[i] <= nums[i - 1]) {
                    nums[i] = nums[i] + 1;
                    flag = true;
                }
            }
            int res = nums[len - 1];

            //倒序，前大但糖不大+1
            for (int i = len - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1] && nums[i] <= nums[i + 1])
                    nums[i] = nums[i + 1] + 1;
                flag = true;
            }
            nums[0] = nums[len];
            nums[len + 1] = nums[1];
        } while (flag);

        System.out.println(Arrays.toString(nums));
        return Arrays.stream(nums).sum();
    }


    public int eraseOverlapIntervals(int[][] intervals) {
        //贪心：按右端点排序射气球，气球互不重叠，len-气球，就是要删的区间
        //射气球，左端点小于右端点能一起射破(相反就是大于等于)
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int num = 0, end = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            if (interval[0] >= end) {
                num++;
                end = interval[1];
            }
        }

        return intervals.length - num;
    }


    public int[][] merge(int[][] intervals) {
        //贪心：左端点正序排，只要区间不中断，更新右端点
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        //存放合并后区间的list
        ArrayList<int[]> list = new ArrayList<>(intervals.length);
        list.add(intervals[0]);//先把第一个区间添加上
        int end; //list最后一个区间右端点的值
        for (int[] interval : intervals) {
            //跟已合并的最后一个区间右端点比较
            end = list.get(list.size() - 1)[1];
            if (interval[0] > end) { //不重合，添加为新区间
                list.add(interval);
            } else { //重合，更新最后一个区间的右端点
                list.get(list.size() - 1)[1] = Math.max(end, interval[1]);
            }
        }
        //带参数的toArray，否则返回Object类型
        return list.toArray(new int[0][2]);
    }


    //非贪心不排序做法，时间复杂度更低
    public int[][] merge1(int[][] intervals) {
        List<int[]> res = new ArrayList<>();
        for (int[] interval : intervals) {
            //按序存放，每次存放之前比较已有区间，重叠就合并
            for (int j = 0; j < res.size(); j++) {
                //获取已有区间
                int[] re = res.get(j);
                //i=[1,3],re=[2,4] 包括 i=[1,4],re=[2,3] 这种情况
                if (re[0] <= interval[1] && re[0] >= interval[0])
                    interval[1] = Math.max(re[1], interval[1]);
                else if (re[1] <= interval[1] && re[1] >= interval[0])
                    interval[0] = re[0]; //i=[2,4],re=[1,3]
                else if (re[1] > interval[1] && re[0] < interval[0])
                    interval = re; //i=[2,3],re=[1,4]
                else continue;//当前区间与结果集不连续

                res.remove(re); //结果集的区间 re 已经被合并到当前区间 interval ，从结果集中删除 re
                j--;
            }
            res.add(interval);//将当前区间添加到结果集
        }

        return res.toArray(new int[0][]);
    }


    public boolean canJump(int[] nums) {
        //贪心：从左到右，选取能到达的最远位置，max(之前跳，当前跳)
        int len = nums.length;
        //目前能到达的最大位置，初始为0
        int maxEnd = 0;
        //遍历过程中，遍历的位置必须能到达，即 maxEnd>=i
        for (int i = 0; i < len && maxEnd >= i; i++) {
            //更新覆盖范围，覆盖范围内一定是可以跳过来的
            maxEnd = Math.max(maxEnd, nums[i] + i);
        }
        //查看最大位置是否覆盖了所有位置
        return maxEnd >= len - 1;
    }

    public int jump(int[] nums) {
        //贪心：每次跳之前，找当前范围内最远的位置起跳
        //limit：当前范围边界；maxEnd:下次跳的最远边界
        int len = nums.length, res = 0, maxEnd = 0, curEnd = 0;

        for (int i = 0; i < len && curEnd < len - 1; i++) {
            if (i > curEnd) {//越过边界，必须起跳
                curEnd = maxEnd;
                res++;
            }
            //更新下次跳的最远边界
            maxEnd = Math.max(maxEnd, nums[i] + i);
        }
        return res;
    }


    public int jump1(int[] nums) {
        //贪心：每次都选择当前范围能跳的最远的位置起跳
        int len = nums.length;
        if (len <= 1) return 0;

        int res = 0;//跳的次数
        int curEnd = 0;//当前的最右位置，当前覆盖范围
        int maxEnd = 0;//当前覆盖范围内起跳可能的最远范围

        for (int i = 0; i < len; i++) {
            //更新当前覆盖范围下一步的最右位置
            maxEnd = Math.max(maxEnd, nums[i] + i);
            //当前覆盖范围下一步可以跳到末尾
            if (maxEnd >= len - 1) {
                res++;
                break;
            }
            //当前覆盖范围遍历完了，必须迈出下一步更新覆盖范围
            if (i == curEnd) {
                curEnd = maxEnd;
                res++;
            }
        }
        return res;
    }

    public int wiggleMaxLength(int[] nums) {

        //贪心：每次只记录峰值的个数，中间值直接过掉，即最终的子序列长度
        int res = 1;//1个元素也算，默认是最右侧的峰值
        int preRes = 0, nexRes;//当前节点的左差值，右差值

        //遍历到倒数第二个数，倒数第一个默认已经是峰值了
        for (int i = 0; i < nums.length - 1; i++) {
            nexRes = nums[i + 1] - nums[i];
            //左右差值不同，且差值不为0，除了pre初始值0
            if (preRes * nexRes >= 0 && nexRes != 0) {
                res++;
                preRes = nexRes;
            }
        }
        return res;
    }


    public int largestSumAfterKNegations(int[] nums, int k) {

        //贪心：按绝对值倒序排列，遇到负值就转正，最后重复变化绝对值最小元素
        nums = Arrays.stream(nums) // 获取流
                .boxed() // int->Integer
                .sorted((o1, o2) -> Math.abs(o2) - Math.abs(o1))
                .mapToInt(Integer::intValue) // Integer->int
                .toArray();

        int len = nums.length;
        for (int i = 0; i < len; i++) {
            //从前向后遍历，遇到负数将其变为正数，同时K--
            if (nums[i] < 0 && k > 0) {
                nums[i] = -nums[i];
                k--;
            }
        }
        // k>0 重复翻转绝对值最小元素
        if (k % 2 == 1) nums[len - 1] *= -1;

        return Arrays.stream(nums).sum();
    }


    public boolean lemonadeChange(int[] bills) {
        //贪心：5直接收；10用5找零；20优先用10找零
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) five++; //情况1
            if (bill == 10) { //情况2
                five--;
                ten++;
                if (five < 0) return false;
            }
            if (bill == 20) { //情况3
                if (ten > 0) {
                    ten--;
                    five--;
                } else five -= 3;
                if (five < 0) return false;
            }
        }
        return true;
    }

    public int[][] reconstructQueue(int[][] people) {

        //贪心：两个维度，先定一个，否则顾己失彼
        //先按身高倒序排序，再按个数正序排序，从前向后插队
        Arrays.sort(people, (p1, p2) -> {
            if (p1[0] == p2[0]) return p1[1] - p2[1];
            return p2[0] - p1[0];
        });

        //链表的插入效率更高
        LinkedList<int[]> list = new LinkedList<>();

        for (int[] p : people) {
            list.add(p[1], p);
        }

        return list.toArray(new int[people.length][2]);
    }

    public static int monotoneIncreasingDigits(int n) {

        //贪心：从后向前遍历，i-1>i 前一个-1，后一个为9
        char[] nums = Integer.toString(n).toCharArray();
        //标记‘9’开始的位置，否则如100 的结果为 90 而不是 99
        int start = nums.length;

        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i - 1] > nums[i]) {
                nums[i - 1]--;
                start = i;
            }
        }
        for (int i = start; i < nums.length; i++) {
            nums[i] = '9';
        }
        return Integer.parseInt(new String(nums));
    }



    public String longestDiverseString(int a, int b, int c) {
        //贪心+大根堆：每次添加数量最多的字符
        PriorityQueue<int[]> heap //按字符数量倒序
                = new PriorityQueue<>((o1, o2) -> o2[1] - o1[1]);

        if (a > 0) heap.offer(new int[]{0, a});
        if (b > 0) heap.offer(new int[]{1, b});
        if (c > 0) heap.offer(new int[]{2, c});

        StringBuilder sb = new StringBuilder();

        while (!heap.isEmpty()) {
            int[] cur = heap.poll();//当前堆顶
            int len = sb.length();//当前字符串的长度，用于判断

            //连续两个和堆顶元素一样
            if (len > 1 && sb.charAt(len - 1) - 'a' == cur[0]
                    && sb.charAt(len - 2) - 'a' == cur[0]) {
                if (heap.isEmpty()) break;//没有其他元素了

                int[] sec = heap.poll();//第二多元素
                sb.append((char)(sec[0] + 'a'));
                if (--sec[1] > 0) heap.offer(sec);
            } else { //不到2个，或者前两个不一样
                sb.append((char)(cur[0] + 'a'));
                cur[1]--;
            }
            if (cur[1] > 0) heap.offer(cur);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            Class.forName("ss");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int findMinFibonacciNumbers(int k) {
        //贪心：每次使用最靠近且小于k的斐波那契数字,并向下递归
        if (k == 0) return 0;

        int f1 = 1, f2 = 1, f3;
        while (f2 <= k) {//f2逼近k,当大于k时，f1就是当前最靠近且小于k的数
            f3 = f1 + f2;
            f1 = f2;
            f2 = f3;
        }
        return findMinFibonacciNumbers(k - f1) + 1;
    }




    public static int minAoe2(int[] x, int[] hp, int range) {
        //贪心：从左到右，先清空aoe左边缘，然后不断移动到下一个非空左边缘
        int len = x.length;
        int res = 0;
        for (int i = 0; i < len; i++) {
            if (hp[i] <= 0) continue;//左边缘不为空
            int mid = i;//aoe范围range,找到中心点
            //小于等于都跳，会多跳一步，但如果用<，会不确定是等于还是大于
            while (mid < len && x[mid] - x[i] <= range) mid++;

            int count = hp[i];
            res += count;//把左边缘清空的aoe的次数

            int r = --mid;//aoe右边缘下标,mid之前多跳了一步
            while (r < len && x[r] - x[mid] <= range) r++;
            for (int j = i; j < r; j++) {//[l,r-1]左闭右闭
                hp[j] -= count;
            }
        }
        return res;
    }

    public int numberOfWeakCharacters(int[][] properties) {
        //贪心：先按0降序，相同则按1升序;从前向后，0一定小于之前
        // 1要是也小于之前的最大值（相同0,1升序），必然是弱者
        Arrays.sort(properties, (a, b) ->
                a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);

        int max = properties[0][1];//记录遍历中遇到的最大值
        int res = 0;
        for (int i = 1; i < properties.length; i++) {
            if (max > properties[i][1]) {
                res++;
            }
            max = Math.max(max, properties[i][1]);
        }
        return res;
    }

    class Solution1 {
        int res = 0;

        public int minCameraCover(TreeNode root) {

            //贪心：从下向上，叶子节点的父节点开始设摄像头
            //状态：-1 无覆盖；0 摄像头；1 覆盖
            if (trval(root) == -1) res++; //根节点单独处理
            return res;
        }

        public int trval(TreeNode root) {
            //空节点覆盖，目的是让叶子节点无覆盖
            if (root == null) return 1;

            int left = trval(root.left);
            int right = trval(root.right);

            //后序遍历：确定父节点的状态
            //子节点都覆盖，父节点无覆盖
            if (left == 1 && right == 1) return -1;
            //子节点有一个不覆盖，父节点摄像头
            if (left == -1 || right == -1) {
                res++;
                return 0;
            }
            //子节点有一个摄像头，父节点覆盖
            return 1;
        }
    }



    public int maxSubArray(int[] nums) {

        //贪心：从前向后遍历，只有前一个结果是正值才加
        int pre = nums[0];
        int res = pre;
        for (int i = 1; i < nums.length; i++) {
            if (pre > 0) pre += nums[i];
            else pre = nums[i];
            res = Math.max(pre, res);
        }
        return res;
    }


    public int videoStitching(int[][] clips, int time) {

        //贪心：按左端点正序排，每次扩展，选所有重叠区间中右端点最长的那个
        Arrays.sort(clips, (a, b) -> a[0] - b[0]);

        //当前已合并区间右端点，下一个拟选择的片段的右端点
        int curEnd = 0, nexEnd = 0;//从0开始
        int res = 0, i = 0, len = clips.length;
        //确保下一个片段和当前已有片段存在重合，否则存在断层，-1
        while (i < len && curEnd >= clips[i][0]) {
            //找到下一个重叠且右端点最远的片段
            while (i < len && curEnd >= clips[i][0]) {
                nexEnd = Math.max(nexEnd, clips[i++][1]);
            }
            //找到需要的片段，结果+1
            res++;
            //更新当前右端点
            curEnd = nexEnd;
            //拼上直接返回
            if (curEnd >= time) return res;
        }

        return -1;
    }

    public boolean isSubsequence(String s, String t) {
        //贪心：每次都找子序列中的一个字母，直到遍历完字符串
        int si = 0, ti = 0;//对于频繁取值，还是转字符数组快一些
        char[] chs = s.toCharArray(), cht = t.toCharArray();
        int lens = chs.length, lent = cht.length;
        //有一个遍历完就返回，s到头说明全都有，t到头说明没有
        while (si < lens && ti < lent) {
            if (chs[si] == cht[ti]) si++;
            ti++;
        }
        return si == lens;
    }
}
