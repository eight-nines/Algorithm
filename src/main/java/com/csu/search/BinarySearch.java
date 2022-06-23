package com.csu.search;

public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(binarySearch(new int[]{2, 5}, 2));
    }


    public static int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int left = 0, right = nums.length - 1;
        //避免只有两个数时陷入死循环(求mid时只会偏向一边)
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            //不直接返回是为了找到第一个目标值，换成left则找最右
            if (nums[mid] == target) right = mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        //只有两个数时，0+1<1不成立，不会循环，故需要手动判断两个值
        if (nums[left] == target) return left;
        if (nums[right] == target) return right;
        return -1;
    }

}
