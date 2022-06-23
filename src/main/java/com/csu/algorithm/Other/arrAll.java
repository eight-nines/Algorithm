package com.csu.algorithm.Other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class arrAll {


    public void nextPermutation(int[] nums) {
        //从前向后找升序对ij，找不到直接全数组降序变成升序
        //找到：从后向前找第一个大于i的数k交换ik，并从j把降序变成升序
        int len = nums.length;
        int i = len - 2, j = len - 1, k = len - 1;

        //从后向前，找到第一个升序对 i，j
        while (i >= 0 && nums[i] >= nums[j]) {
            i--;
            j--;
        }
        if (i >= 0) {//找到了升序对(找不到直接全数组升序)
            //从后向前，找到第一个大于i的数(大得尽可能小)
            while (nums[k] <= nums[i]) k--;
            swap(nums, i, k);//交换i 和 k
        }
        //之前操作后j-len-1是降序，要改成升序
        while (j < len - 1) {//没找到升序对时，j=0,即全数组排序
            swap(nums, j++, (len--) - 1);
        }
    }

    private void swap(int[] nums, int i, int k) {
        nums[i] ^= nums[k];
        nums[k] ^= nums[i];
        nums[i] ^= nums[k];
    }


    public void sortColors(int[] nums) {
        //快排双指针：等于0与left交换，等于2与right交换，等于1继续前进
        //left指向最后一个0，right指向第一个2
        int left = -1, right = nums.length;

        for (int i = 0; i < right; ) {
            if (nums[i] == 0) {//下标可能相同，不能异或交换
                swap1(nums, i++, ++left);
            } else if (nums[i] == 2) {//此处i不前进
                swap1(nums, i, --right);
            } else i++;
        }
    }

    private void swap1(int[] nums, int i, int k) {
        int temp = nums[i];
        nums[i] = nums[k];
        nums[k] = temp;
    }

    class Solution5 {
        public int countValidWords(String sentence) {
            //模拟
            String[] str = sentence.split(" ");
            int res = 0;
            for (String s : str) if (check(s)) res++;
            return res;
        }

        private boolean check(String s) {
            int len = s.length();
            if (len == 0) return false;
            char[] chars = s.toCharArray();

            boolean line = false;//是否出现过 '-'
            for (int i = 0; i < len; i++) {
                if (Character.isDigit(chars[i])) return false;//非数字
                //分隔线：左右两侧必须是字母，且只出现一次
                if (chars[i] == '-') {
                    if (line || i == 0 || i == len - 1) return false;
                    else line = true;
                }//其他符号：只能在结尾，且前一个字符不能是'-'
                if (chars[i] == '!' || chars[i] == '.' || chars[i] == ',') {
                    if (i != len - 1 || (i != 0 && chars[i - 1] == '-'))
                        return false;
                }
            }
            return true;
        }
    }


    class Solution {
        int[] nums1, nums2;

        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            //二分查找：每次在两个数组中同时查找k/2，并淘汰其中的较小元素
            this.nums1 = nums1;this.nums2 = nums2;
            int m = nums1.length, n = nums2.length;

            //左右中位数序号:总长奇数时l=r；偶数 r=l+1
            int left = (m + n + 1) / 2, right = (m + n + 2) / 2;
            //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k
            return 0.5 * (search(0, m - 1, 0, n - 1, left)
                    + search(0, m - 1, 0, n - 1, right));
        }

        //循环不变量：下标+左闭右闭
        int search(int l1, int r1, int l2, int r2, int k) {
            int len1 = r1 - l1 + 1, len2 = r2 - l2 + 1;

            //处理当前节点
            if (len1 == 0) return nums2[l2 + k - 1];//序号转下标
            if (len2 == 0) return nums1[l1 + k - 1];
            if (k == 1) return Math.min(nums1[l1], nums2[l2]);

            //两个数组中各自的第 k/2 个数 的下标，防止 k/2越界，取min值
            int i1 = l1 + Math.min(k / 2, len1) - 1, i2 = l2 + Math.min(k / 2, len2) - 1;
            //向下递归搜索
            if (nums1[i1] > nums2[i2]) {//1大，淘汰2中前i2中还没淘汰的数
                return search(l1, r1, i2 + 1, r2, k - (i2 - l2 + 1));
            } else return search(i1 + 1, r1, l2, r2, k - (i1 - l1 + 1));
        }
    }

    public int removeDuplicates(int[] nums) {
        //双指针：后一个数等于当前数，找第一个不等于的，然后赋值
        int left = 0, right = 1;
        while (right < nums.length) {//11234
            while (right < nums.length && nums[left] == nums[right]) right++;
            if (right < nums.length) nums[++left] = nums[right++];
        }
        return left + 1;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //从后向前，依次赋值nums1和2中的最大值
        int index = m-- + n-- - 1;
        while (m >= 0 && n >= 0) {
            nums1[index--] = nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
        }
        //m没完，n完了，不用管；m完了，n没完，依次赋值
        while (n >= 0) nums1[index--] = nums2[n--];
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for(int num : nums) {
            if(set.contains(num)) return true;
            set.add(num);
        }
        return false;
    }







    public int minimumDifference(int[] nums, int k) {
        //升序排，窗口长k，更新移动
        Arrays.sort(nums);
        if(nums.length==1) return 0;
        int res=Integer.MAX_VALUE;
        int left = 0,right=k-1;
        while(right < nums.length){
            res = Math.min(nums[right++]-nums[left++],res);
        }
        return res;
    }

    public int countGoodRectangles(int[][] rectangles) {
        //模拟+一次遍历：取每个矩形的最小边，等于max,res+1;大于max,更新max，结果还原为1
        int maxLen = 0, res = 0, cur;
        for (int[] arr : rectangles) {
            cur = Math.min(arr[0], arr[1]);//当前矩形最小边长
            if (cur == maxLen) res++;//等于现在正方形边长，结果+1
            else if (cur > maxLen) {//大于现有正方形边长，结果归1，更新max
                maxLen = cur;
                res = 1;
            }
        }
        return res;
    }

    public int sumOfUnique(int[] nums) {
        //哈希表：0没出现过+num，1出现过了-num,>1不管
        int[] arr = new int[101];//标记每个数字的状态
        int res = 0;
        for (int num : nums) {
            if (arr[num] == 0) res += num;
            else if (arr[num] == 1) res -= num;
            arr[num] += 1;
        }
        return res;
    }

    public String longestNiceSubstring(String s) {
        //分治：先统计字符个数，找到第一个大小不共存的字符，分左右递归处理
        char[] chars = s.toCharArray();
        int[] nums = new int[128];
        for (char c : chars) nums[c]++;

        String res = "";
        boolean flag = true;//标记s是否为完美字符串

        for (int i = 0; i < s.length(); i++) {
            char c = chars[i];
            if (nums[c ^ 32] == 0) {//小变大，大变小
                String left = longestNiceSubstring(s.substring(0, i));
                String right = longestNiceSubstring(s.substring(i + 1));
                //最长的那个选做结果返回
                res = left.length() >= right.length() ? left : right;
                flag = false;//找到了大小不共存字符，s不完美
                break;//只找第一个
            }
        }
        return flag ? s : res;
    }

    public int strStr(String haystack, String needle) {
        //双指针
        if(needle.length()<1) return 0;
        int p1=0,p2=0,tem1,tem2;
        char[] ch=haystack.toCharArray(),cn = needle.toCharArray();
        int len1 = ch.length,len2 = cn.length;

        while(p1<len1 && p2<len2){
            tem1 = p1;tem2=p2;//记录当前指针
            if(ch[p1]==cn[p2]){
                while(p1<len1 && p2<len2 && ch[p1]==cn[p2]){
                    p1++;p2++;
                }
                if(p2==len2-1) return tem1;
            }
            p1=tem1++; p2 = tem2++;
        }
        return -1;
    }





    public int leastInterval(char[] tasks, int n) {
        //模拟：统计每个字母的频数，结果为 (最大频数 - 1) * (n + 1) + 最大频元素的个数
        //最大频数-1:减去的1个会在'最大频元素的个数'加上，因为最后一个最大频元素不用跟其他
        //n+1:间隔数+最大频本身，n用于塞低频元素或待机
        //["A","A","A","B","B","C"]，n = 2 执行顺序： A->X->X->A->X->X->A

        int[] cs = new int[26];//统计各元素频率
        for (char c : tasks) cs[c - 'A']++;

        Arrays.sort(cs);//升序排序，cs[25]就是最大频数
        int count = 1;//最大频元素的个数 ["A","B"]->两个
        for (int i = 24; i >= 0 && cs[i] == cs[25]; i--) count++;

        //最少也要把所有任务执行完，主要是避免n=0时，公式结果小于len的情况
        return Math.max(tasks.length, (cs[25] - 1) * (n + 1) + count);
    }

    public int findUnsortedSubarray(int[] nums) {
        //贪心：子数组左侧有序升，右侧有序升，内部乱序
        //但是子数组的左边界一定大于左边界右侧最小值，不然左边界就没必要参与重排序
        //同理右侧边界一定小于右边界左侧最大值
        int len = nums.length;//right指向右边界，max记录右边界之前的最大值
        if (len < 1) return 0;
        int right = 0, left = len - 1, max = nums[0], min = nums[len - 1];

        for (int i = 1; i < len; i++) {
            if (max > nums[i]) right = i;
            else max = nums[i];
            //同时倒序遍历，更新左边界右侧的最小值
            if (min < nums[len - 1 - i]) left = len - 1 - i;
            else min = nums[len - 1 - i];
        }
        return right > left ? right - left + 1 : 0;//全升序时初始值 right<=left
    }

    public int subarraySum(int[] nums, int k) {
        //前缀和：到每个位置统计 当前和 - k 出现的次数
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);//单独一个节点就等于k nums = [3,...], k = 3
        int sum = 0, res = 0;

        for (int num : nums) {
            sum += num;//累加当前元素和，即当前元素前缀和
            res += map.getOrDefault(sum - k, 0);//查找差为k的前缀和个数
            map.merge(sum, 1, Integer::sum);
        }
        return res;
    }


    public boolean isPalindrome(int x) {
        //负数、尾数0直接返回
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int num = 0;
        while (x > num) {//偶数长相等，奇数长num/10==x
            num = 10 * num + x % 10;//依次将x的低位数给num
            x /= 10;//x截断低位
        }
        return x == num || x == num / 10;
    }

    public int[] productExceptSelf(int[] nums) {
        //2次遍历：新建数组,1记录每个数左侧的乘积；2乘右侧乘积
        int len = nums.length;
        int[] res = new int[len];

        int pre = 1;//当前数左侧的累计乘积
        for (int i = 0; i < len; pre *= nums[i], i++) res[i] = pre;
        pre = 1;//当前数右侧的累计乘积
        for (int i = len - 1; i >= 0; pre *= nums[i], i--) res[i] *= pre;

        return res;
    }


    public int findKthLargest(int[] nums, int k) {
        //小顶堆:依次加入，超过k个就弹出，只剩下k个最大元素，堆顶就是第k大
        PriorityQueue<Integer> heap = new PriorityQueue<>(k + 1);

        for (int num : nums) {
            //只有堆不够k个元素 或 num大于堆顶才需要更新堆，不然就是加进去也会弹出
            if ((!heap.isEmpty() && num > heap.peek()) || heap.size() < k)
                heap.offer(num);
            if (heap.size() > k) heap.poll();
        }
        return heap.peek();
    }

    class MedianFinder {
        //大顶堆+小顶堆：大顶堆维护前半段，小顶堆维护后半段，两个堆顶是中间位置数
        //后+小：大于小顶，进小堆；前+大：小于大顶，进大堆；size差最多为1
        PriorityQueue<Integer> before;
        PriorityQueue<Integer> after;

        public MedianFinder() {
            before = new PriorityQueue<>();
            after = new PriorityQueue<>((a, b) -> b - a);
        }

        public void addNum(int num) {
            if (before.isEmpty() || num > before.peek()) {
                before.offer(num);//多两个 平衡一下
                if (before.size() - 1 > after.size())
                    after.offer(before.poll());
            } else {
                after.offer(num);//确保多的那个元素在 small堆中
                if (before.size() < after.size())
                    before.offer(after.poll());
            }
        }

        public double findMedian() {
            return before.size() > after.size() ?
                    before.peek() : (after.peek() + before.peek()) / 2.0;
        }
    }

    class Solution540 {
        public int singleNonDuplicate(int[] nums) {
            int len=  nums.length;
            int l=0,r=len-1,mid;
            //二分，左闭右闭
            while(l<r){
                mid = l+(r-l)/2;
                //mid奇-1，偶+1 看当前这一对是否相同，相同向右找
                if(nums[mid]==nums[mid^1]) l=mid+1;
                else r =  mid; //不同向左找，包括当前位置
            }
            return nums[r];
        }
    }




    public void moveZeroes(int[] nums) {
        //双指针：左指针指0，右指针指数字，把右指针值赋给左指针，左指针开始赋0
        int left = 0, right = 0, len = nums.length;
        //把所有非0移动到数组前面，并更新左指针（指向非0的下一位）
        for (; right < len; right++) {
            if (nums[right] != 0) nums[left++] = nums[right];
        }
        //右指针遍历完，左指针开始全部赋0
        for (; left < len; left++) nums[left] = 0;
    }

    public int majorityElement(int[] nums) {
        //该数的数量大于其他数数量的总和
        //故假设任一个数是目标数，遇到相同+1，不同-1，为0就换个数
        int res = 0, count = 0;
        for (int num : nums) {
            if (count == 0) {//之前数抵消完了，更新新目标数
                res = num;
                count++;
            } else {//比较目标数和当前数，相同+1，不同-1
                if (num == res) count++;
                else count--;
            }
        }
        return res;
    }



}


