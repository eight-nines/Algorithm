package com.csu.algorithm.Sort;

import static com.csu.algorithm.Sort.ForTest.*;


public class SelectionSort {

	public static void selectionSort(int[] arr) {
		if(arr==null ||arr.length<2)return;

		for (int i = arr.length-1; i >0; i--) {
			int maxIndex = i;
			for (int j = 0; j <i ; j++) {
				maxIndex=arr[j]>arr[maxIndex]?j:maxIndex;
			}
			swap(arr,maxIndex,i);
		}
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
			selectionSort(arr1);
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
		selectionSort(arr);
		printArray(arr);
	}

}
