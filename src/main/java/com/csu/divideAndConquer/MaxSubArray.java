package com.csu.divideAndConquer;

import java.util.Arrays;
import java.util.Collections;

//最大子序列和
public class MaxSubArray {
	public static void main(String[] args) {
		int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
		System.out.println(maxSubArray(nums));
		System.out.println(maxSubarray1(nums));
		System.out.println(test(nums));

	}

	static int test(int[] nums) {
		if(nums==null || nums.length==0) return 0 ;

		for(int i=1; i<nums.length; i++) {
			nums[i] = nums[i] + Math.max(nums[i-1],0);
		}
		return Arrays.stream(nums).max().getAsInt();

	}

	static int maxSubArray(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		return maxSubArray(nums, 0, nums.length-1);
	}
	
	/**
	 * 求解[l, r]中最大连续子序列的和
	 * T(n) = T(n/2) + T(n/2) + O(n)
	 * T(n) = 2T(n/2) + O(n)
	 * logba = 1  d = 1
	 */
	static int maxSubArray(int[] nums, int l, int r) {

		if (r-l==0) return nums[l];

		int mid = (r + l) >> 1;
		//两半部分分别最大和
		int leftMax = maxSubArray(nums,l,mid);
		int rightMax = maxSubArray(nums,mid+1,r);
		//当前数组中间可能性最大和（单独计算）
		int mergeMax = compare(nums,l,mid,r);

		return Math.max(mergeMax,Math.max(leftMax,rightMax));
	}

	static int compare(int[] nums, int l, int mid , int r) {
		//左边的最大连续子序列的和[l,mid]
		int leftMax = nums[mid];
		int leftSum = leftMax;//用来统计当前子序列的值
		for (int i = mid - 1; i >= l; i--) {
			leftSum += nums[i];
			leftMax = Math.max(leftMax, leftSum);
		}

		//右边的最大连续子序列的和[mid+1,r]
		int rightMax = nums[mid+1];
		int rightSum = rightMax;
		for (int i = mid + 2; i <= r; i++) {
			rightSum += nums[i];
			rightMax = Math.max(rightMax, rightSum);
		}

		//返回中间可能性的
		return leftMax + rightMax;
	}


	//暴力出奇迹 on2
	static int maxSubarray1(int[] nums) {
		if (nums == null || nums.length < 1) return 0;
		int max = Integer.MIN_VALUE;
		for (int begin = 0; begin < nums.length; begin++) {
			int sum = 0;
			for (int end = begin; end < nums.length; end++) {
				// sum是[begin, end]的和
				sum += nums[end];
				max = Math.max(max, sum);
			}
		}
		return max;
	}

	//动态规划 on1
	static int maxSubarray2(int[] nums) {
		if (nums == null || nums.length <1) return 0;

		//nums[i-1]是第i-1个数的最优决策值（以nums[i-1]结尾的最大子序列和）
		//初始状态：nums[0] = nums[0]
		//即 i-1 上的数，可以选择加上上一个（i-2）最优决策值,前提是对方让自己大于本身已有值（对方必须大于0）
		//一直往前推，最开始的时候，只有前一个数让自己变大，才会出现累加的情况，累加 == 产生比当前单值更大的子序列
		//但向后可能会加上负值（决策位本身为负），只要本身不为负，就有连续的价值，否则连续中断
		//连续的过程中，后面的值可能比前面值小，故最终取的是nums中的最大值
		for (int i = 1; i <nums.length; i++) {
			//状态转移方程
			nums[i] = nums[i] + Math.max(nums[i-1], 0);
		}

		return Arrays.stream(nums).max().getAsInt();
	}
}
