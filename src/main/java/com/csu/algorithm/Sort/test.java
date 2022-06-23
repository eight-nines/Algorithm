package com.csu.algorithm.Sort;

import java.util.Arrays;
import java.util.Map;

public class test {
    public static void quickSort(int[] arr){
        if(arr == null || arr.length<2)return;
        // 9.35
        // 快排，两边各选一个指针，分小中大三个区域，其中小是拍好序的位置，直到结束
        quickSort(arr,0,arr.length-1);
    }

    public static void quickSort(int[] arr ,int l , int r){
        if(l>=r)return;
        swap(arr,r, l+(int)(Math.random()*(r-l+1)));
        int[] p = sortRecur(arr,l,r);
        quickSort(arr,l,p[0]-1);
        quickSort(arr,p[1]+1,r);
    }

    public static int[] sortRecur(int[] arr ,int l , int r){
        int p1 = l-1;
        int p2 = r;

        while (l<p2){
            if(arr[l]<arr[r]){
                swap(arr,l++,++p1);
            }else if(arr[l]>arr[r]){
                swap(arr,l,--p2);
            }else l++;
        }
        swap(arr,r,p2);

        return new int[]{p1+1,p2};
    }

    public static void swap(int[] arr,int i ,int j){
        if(i==j)return;
        arr[i]^=arr[j];
        arr[j]^=arr[i];
        arr[i]^=arr[j];

    }



    public static int f(int n) {
        if(n==1)return 0;
        if(n==2)return 1;

        return f(n-1)+f(n-2);

    }

    public static void main(String[] args) {
        int[] a = new int[]{1,5,2,7,3,8,2,4};
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }
}
