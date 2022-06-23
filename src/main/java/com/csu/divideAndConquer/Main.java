package com.csu.divideAndConquer;

import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer
                (new BufferedReader(new InputStreamReader(System.in)));

        in.nextToken();
        int len = (int) in.nval;
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            in.nextToken();
            nums[i] = (int) in.nval;
        }
        //输出少时，直接使用原API
        System.out.print(divide(nums, 0, len - 1));
    }

    static long divide(int[] nums, int l, int r) {
        //题目需要考虑返回值越界，故使用long型统计
        if (l >= r) return 0;

        int mid = l + (r - l) / 2;

        long left = divide(nums, l, mid);
        long right = divide(nums, mid + 1, r);

        return left + right + merge(nums, l, mid, r);
    }

    static long merge(int[] nums, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l, p2 = mid + 1, index = 0;
        long res = 0;

        while (p1 <= mid && p2 <= r) {
            if (nums[p1] <= nums[p2]) {//移动左指针，统计右边
                res += nums[p1] * (r - p2 + 1);
                help[index++] = nums[p1++];
            } else {
                help[index++] = nums[p2++];
            }

        }
        while (p1 <= mid) help[index++] = nums[p1++];
        while (p2 <= r) help[index++] = nums[p2++];
        for (int i = 0; i < r - l + 1; i++) {
            nums[i + l] = help[i];
        }
        return res;
    }
}
