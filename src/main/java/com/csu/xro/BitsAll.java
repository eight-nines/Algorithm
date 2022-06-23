package com.csu.xro;

import java.util.LinkedList;
import java.util.List;

public class BitsAll {


    public int[] countBits(int n) {
        //利用之前的计算结果，先统计除了最低位的1，再看最低位是不是1
        int[] res = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            //除了最低位的1 + 最低位是不是1
            res[i] = res[i >> 1] + (i & 1);
        }
        return res;
    }


    public static List<Integer> findDisappearedNumbers(int[] nums) {
        //数字会重复，把数字转下标，没被覆盖到的下标再转数字，就是没出现的数字
        //把数字作为下标标记元素，没有被标记的就是没出现过的下标（数字）
        List<Integer> list = new LinkedList<>();

        int len = nums.length;//[1,1]-> 2
        for (int i = 0; i < len; i++) {
            //数字转下标要-1；为了不影响其他元素，把下标覆盖到的元素标记为负值
            int index = Math.abs(nums[i]) - 1;//数字转下标
            nums[index] = nums[index] < 0 ? nums[index] : -nums[index];
        }//[3,2,2] -> 1
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) list.add(i + 1);//大于0表示没被覆盖到
        }
        return list;
    }

    public int hammingDistance(int x, int y) {
        //从低到高，依次对比每一位，不相同则结果+1
        int res = 0;
        while (x != 0 || y != 0) {
            if ((x & 1) != (y & 1)) res++;
            x = x >> 1; y = y >> 1;
        }
        return res;
    }

    public int singleNumber(int[] nums) {
        //异或：0^n=n；n^n=0
        //拿0连续异所有数，出现偶数次的消消乐，结果就是奇数次的那一个
        int res = 0;
        for (int num : nums) res ^= num;

        return res;
    }

    class Solution {
        public int singleNumber(int[] nums) {
            //遍历所有数:统计每一位的1的个数，%3不为0证明目标数此位为1
            int[] bits = new int[32];

            for (int num : nums) {
                //遍历每一位并计数
                for (int i = 0; i < 32 && num != 0; i++, num = num >> 1) {
                    if ((num & 1) == 1) bits[i]++;
                }
            }
            //从前向后赋值
            int res = 0;
            for (int i = 0; i < 32; i++) {
                if ((bits[i] % 3) != 0) res |= (1 << i);
            }
            return res;
        }
    }

    public int numberOfMatches(int n) {
        //模拟：按题意直接写
        int res = 0;

        while (n > 1) {
            if ((n & 1) == 1) {//奇数个队伍
                res += ((n - 1) >> 1);
                n = ((n - 1) >> 1) + 1;
            } else {
                res += (n >> 1);
                n = (n >> 1);
            }
        }
        return res;
    }
    public int numberOfMatches1(int n) {
        //数学方法：n个人产生1个胜者，要进行n-1场比赛
        return n-1;
    }


    public static void main(String[] args) {
        findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1});
    }


}
