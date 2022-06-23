package com.csu.search;

public class SearchRange {


    //100
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1,-1};
        if (nums == null || nums.length == 0) return res;

        int left = 0, right = nums.length - 1;
        //避免只有两个数时陷入死循环(求mid时只会偏向一边)
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            //找到目标值直接向两边扩散即可
            if (nums[mid] == target) {
                left = mid;//复用left指针向左搜索，mid向右搜索
                while(left>=0 && nums[left] == target) left--;
                while(mid<=nums.length - 1 && nums[mid] == target) mid++;
                res[0] = left+1;res[1] = mid-1;
                return res;
            }
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        //只有1-2个值时的返回值
        if (nums[left] == target ) {
            res[0] = left;res[1] = left;//1、左等右不等
            if (nums[right] == target) {
                res[1] = right;//2、左等右等
            }
            return res;
        }
        if (nums[right] == target) {//3、左不等右等
            res[0] = right;res[1] = right;
        }//4、左右都不等

        return res;
    }















}
