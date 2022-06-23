package com.csu.divideAndConquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InversionPair {

public static void inversionPair(int[] arr){
    if (arr == null){
        return ;
    }
    //存放逆序对
    ArrayList<int[]> group = new ArrayList<int[]>();
    inversionPairRecur(arr,0,arr.length-1,group);
    //打印
    for (int[] i:group) {
        System.out.println(Arrays.toString(i));
    }
}

public static void inversionPairRecur(int[] arr , int l , int r, List group){
    if(l>=r) return ;
    int mid = l+((r-l)>>1);

    inversionPairRecur(arr, l ,mid ,group);
    inversionPairRecur(arr, mid+1, r,group);
    merge(arr,l,r,mid,group);
}

public static void merge(int[] arr , int l ,int r,int mid,List group){
    int[] help = new int[r-l+1];
    int p1 = l;
    int p2 =mid+1;
    int i =0;
    int[] res;
    while (p1<=mid && p2<=r){
        if (arr[p1]>arr[p2]){
            for (int j = p1; j <= mid; j++) {
                group.add(new int[]{arr[j],arr[p2]});
            }
        }
        help[i++] = arr[p1]>arr[p2]?arr[p2++]:arr[p1++];
    }
    while (p1<=mid){
        help[i++] = arr[p1++];
    }
    while (p2<=r){
        help[i++] = arr[p2++];
    }
    for (int j = 0; j <help.length ; j++) {
        arr[l+j] = help[j];
    }
}

    public static void main(String[] args) {
        int[] arr =new int[]{7,5,6,4};
        inversionPair(arr);
    }
}
