package com.csu.algorithm.Bfs;

import java.util.*;

public class graphAll {


    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //aov拓扑排序：int存放节点入度，que存放bfs遍历起点，list存放<节点，出集>
        int[] in = new int[numCourses];//对应课程的入度
        ArrayList<List<Integer>> list = new ArrayList<>(numCourses);//每个课程的出集
        Deque<Integer> que = new LinkedList<>();

        for (int[] arr : prerequisites) in[arr[1]]++;//初始化每个节点的入度
        for (int i : in) list.add(new ArrayList<>());//创建并初始化各节点出集
        for (int[] arr : prerequisites) list.get(arr[0]).add(arr[1]);
        //入度为0入队列
        for (int i = 0; i < numCourses; i++) if (in[i] == 0) que.offer(i);
        while (!que.isEmpty()) {
            int index = que.poll();//出队
            List<Integer> out = list.get(index);//获取出集
            //出集节点入度-1，为0入队
            for (int i : out) if (--in[i] == 0) que.offer(i);
        }
        //aov拓扑排序后，还有入度不为0的节点，说明有环 [[1,0],[0,1]]
        for (int i : in) if (i != 0) return false;
        return true;
    }











}
