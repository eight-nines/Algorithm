package com.csu.algorithm.Sort;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Emp> list = new ArrayList<>();
        list.add(new Emp("e1",10));
        list.add(new Emp("e2",5));
        list.add(new Emp("e1",2));

        mergeSort(list,0,list.size()-1);
        list.forEach(System.out::println);
    }

    static void mergeSort(List<Emp> list,int l,int r){
        if(l>=r) return;
        int mid = l+(r-l)/2;

        mergeSort(list,l,mid);
        mergeSort(list,mid+1,r);
        merge(list,l,mid,r);
    }

    static void merge(List<Emp> list,int l,int mid ,int r){

        Emp[] help = new Emp[r-l+1];
        int i = 0;
        int p1=l,p2=mid+1;

        while(p1<=mid && p2<=r){
            if(list.get(p1).age<=list.get(p2).age) help[i++]=list.get(p1++);
            else help[i++]=list.get(p2++);
        }
        while(p1<=mid)  help[i++]=list.get(p1++);
        while(p2<=r)help[i++]=list.get(p2++);

        for(i = 0;i<help.length;i++){
            list.set(i+l,help[i]);
        }
    }
}
