package com.csu.algorithm.Sort;

import static com.csu.algorithm.Sort.ForTest.*;

public class ShellSort {


	public static void shellSort(int[] arr) {
		if(arr ==null || arr.length<2) return;
		int len = arr.length;
		for(int stride = len/2;stride>0;stride/=2){
			for(int g = 0;g<stride;g++){
				for(int i =g+1;i<len;i+=stride ){
					int j = i;
					int tem=  arr[i];
					for(;j-stride>=0;j-=stride){
						if(arr[j-stride]>tem) arr[j]=arr[j-stride];
						else break;
					}
					arr[j]=tem;
				}
			}
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
			shellSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		shellSort(arr);
		printArray(arr);
	}

}
