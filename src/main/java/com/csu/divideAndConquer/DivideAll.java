package com.csu.divideAndConquer;


import java.io.*;

public class DivideAll {


















    class Solution {
        public int reversePairs(int[] nums) {
            //循环不变量：左闭右闭
            return divide(nums, 0, nums.length - 1);
        }

        public int divide(int[] nums, int l, int r) {
            if (l >= r) return 0;

            int mid = l + (r - l) / 2;

            int left = divide(nums, l, mid);
            int right = divide(nums, mid + 1, r);
            //统计左、右、以及合并中产生的所有逆序对
            return left + right + merge(nums, l, mid, r);
        }

        int merge(int[] nums, int l, int mid, int r) {
            //左右两个数组都拍好序了，左大于右，右指针移动，统计左指针及之后的所有数
            int[] help = new int[r - l + 1];
            int p1 = l, p2 = mid + 1, index = 0, res = 0;
            while (p1 <= mid && p2 <= r) {//遇到逆序对，统计左边数组之后的所有数
                if (nums[p1] > nums[p2]) res += mid - p1 + 1;
                help[index++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
            }
            while (p1 <= mid) help[index++] = nums[p1++];
            while (p2 <= r) help[index++] = nums[p2++];

            for (int i = 0; i < r - l + 1; i++) nums[i + l] = help[i];

            return res;
        }
    }


























}
