package com.csu.algorithm.Sort;

import java.util.Arrays;
import static com.csu.algorithm.Sort.ForTest.*;


public class QuickSort {

	public static void quickSort(int[] arr) {
		if (arr == null || arr.length < 2) return;
		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(int[] arr, int l, int r) {
		if(l>=r) return;

		swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
		int[] p = partition(arr, l, r);
		quickSort(arr, l, p[0] - 1);
		quickSort(arr, p[1] + 1, r);
	}

	public static int[] partition(int[] arr, int l, int r) {
		int less = l - 1;
		int more = r;
		while (l < more) {
			if (arr[l] < arr[r]) swap(arr, ++less, l++);
			else if (arr[l] > arr[r]) swap(arr, --more, l);
			else l++;
		}
		swap(arr, more, r);
		return new int[] { less + 1, more };
	}








	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
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
			quickSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		quickSort(arr);
		printArray(arr);

	}

}
