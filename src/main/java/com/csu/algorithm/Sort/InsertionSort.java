package com.csu.algorithm.Sort;

import java.util.Arrays;
import static com.csu.algorithm.Sort.ForTest.*;


public class InsertionSort {

	//交换版，失去了插排常数项低的优点
//	public static void insertionSort(int[] arr) {
//		if (arr == null || arr.length < 2) {
//			return;
//		}
//		for (int i = 1; i < arr.length; i++) {
//			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
//				swap(arr, j, j + 1);
//			}
//		}
//	}

//	public static void insertionSort(int[] arr) {
//		if(arr==null||arr.length<2)return;
//
//		for (int i = 1; i <arr.length ; i++) {
//			int j;
//			int tem = arr[i];
//			for (j = i-1; j >= 0 && arr[j]>tem; j--) {
//				arr[j+1]=arr[j];
//			}
//			arr[j+1] =tem;
//		}
//	}

	public static void insertionSort(int[] arr) {
		//每次假定之前已经排序完成，遇到比自己大的就向后挪
		int len=arr.length,tem;

		for(int i = 1;i<len;i++){
			int j = i;
			tem = arr[i];
			for(;j>0;j--){
				if(arr[j-1]>tem) arr[j]=arr[j-1];
				else break;
			}
			arr[j] = tem;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
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
			insertionSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		insertionSort(arr);
		printArray(arr);
	}

}
