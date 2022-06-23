package basic_class_01;

import java.util.Arrays;

public class MaxGap {

	public static int maxGap(int[] nums) {
		// 判断
		if (nums == null || nums.length < 2) {
			return 0;
		}
		// 记录数组长度，最大最小值
		int len = nums.length;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i:nums) {
			min = Math.min(min, i);
			max = Math.max(max, i);
		}
		// 排除所有值相等的情况
		if (min == max) {
			return 0;
		}

		// 用三个数组表示桶的三个属性
		// 并不创建桶实体
		boolean[] hasNum = new boolean[len + 1];
		int[] maxs = new int[len + 1];
		int[] mins = new int[len + 1];
		// 桶号
		int bid = 0;
		// 确定数组中每个数应该去几号桶，并修改桶的信息
		for (int i:nums) {
			bid = bucket(i, len, min, max);
			mins[bid] = hasNum[bid] ? Math.min(mins[bid], i) : i;
			maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], i) : i;
			hasNum[bid] = true;
		}

		// 最大差值结果
		int res = 0;
		// 上个非空桶的最大值，初始为第一个桶的最大值
		int lastMax = maxs[0];
		for (int i = 1; i <= len; i++) {
			// 妙：最大值初始取自0桶，最小值在循环中从1桶开始取
			// 如果遇到空桶，最小值位置跳到下个非空桶，最大值没变，还是空桶前的非空桶最大值
			// 产生差值后，最大值被赋为当前非空桶最大值！
			if (hasNum[i]) {
				// 比较下个非空桶的最小值，和上个非空桶的最大值
				res = Math.max(res, mins[i] - lastMax);
				lastMax = maxs[i];
			}
		}
		return res;
	}

	// 给定一个数，确定该数应该去几号桶
	public static int bucket(long num, long len, long min, long max) {
		return (int) ((num - min) * len / (max - min));
	}

	// for test
	public static int comparator(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		Arrays.sort(nums);
		int gap = Integer.MIN_VALUE;
		for (int i = 1; i < nums.length; i++) {
			gap = Math.max(nums[i] - nums[i - 1], gap);
		}
		return gap;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			if (maxGap(arr1) != comparator(arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
