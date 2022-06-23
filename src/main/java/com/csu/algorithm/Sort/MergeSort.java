package com.csu.algorithm.Sort;

import java.util.Arrays;
import static com.csu.algorithm.Sort.ForTest.*;


public class MergeSort {



	//每次找到中点，左右两段分别排序
//	public static void mergeSort(int[] nums){
//		//循环不变量：左闭右闭
//		mergeSort(nums,0,nums.length-1);
//	}
//
//	static void mergeSort(int[] nums,int l,int r){
//		if(l>=r) return;
//
//		int mid = l+(r-l)/2;
//
//		mergeSort(nums,l,mid);
//		mergeSort(nums,mid+1,r);
//
//		merge(nums,l,mid,r);
//	}
//
//	static void merge(int[] nums,int l,int mid ,int r){
//
//		int[] help = new int[r-l+1];
//		int p1 = l,p2=mid+1,index = 0;
//
//		while(p1<=mid && p2<=r){
//			help[index++] = nums[p1]<=nums[p2]?nums[p1++]:nums[p2++];
//		}
//		while(p1<=mid) help[index++] = nums[p1++];
//		while(p2<=r) help[index++] = nums[p2++];
//
//		for (int i = 0; i <r-l+1 ; i++) {
//			nums[l+i] = help[i];
//		}
//	}











	public static void mergeSort(int[] nums){
		mergeSort(nums,0,nums.length-1);
	}

	static void mergeSort(int[] nums,int l,int r){
		if(l>=r) return;
		int mid = l+(r-l)/2;

		mergeSort(nums,l,mid);
		mergeSort(nums,mid+1,r);
		merge(nums,l,mid,r);
	}

	static void merge(int[] nums,int l,int mid ,int r){

		int[] help = new int[r-l+1];
		int i = 0;
		int p1=l,p2=mid+1;

		while(p1<=mid && p2<=r){
			if(nums[p1]<=nums[p2]) help[i++]=nums[p1++];
			else help[i++]=nums[p2++];
		}
		while(p1<=mid)  help[i++]=nums[p1++];
		while(p2<=r)help[i++]=nums[p2++];

		for(i = 0;i<help.length;i++){
			nums[i+l] = help[i];
		}
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
			mergeSort(arr1);
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
		mergeSort(arr);
		printArray(arr);
	}

}
