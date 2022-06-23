package com.csu.algorithm.Hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class HashTableAll {

    public int longestConsecutive(int[] nums) {
        //哈希表：set存放所有值，找到每个序列中最小的数，并向上记录序列长，更新结果
        HashSet<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) set.add(num);

        int res = 0;
        for (int num : set) {//遍历哈希表
            //从每个序列的最小值开始计算序列长
            if (set.contains(num - 1)) continue;
            int count = 1;
            while (set.contains(++num)) count++;
            res = Math.max(res, count);
        }
        return res;
    }


    public String[] uncommonFromSentences(String s1, String s2) {
        //哈希表：统计两个字符串中只出现一次的 字符串
        String[] str1 = s1.split(" ");
        String[] str2 = s2.split(" ");
        HashMap<String, Integer> map = new HashMap<>();

        for (String s : str1) map.merge(s, 1, (a, b) -> b = a + 1);
        for (String s : str2) map.merge(s, 1, (a, b) -> b = a + 1);

        LinkedList<String> res = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) res.add(entry.getKey());
        }

        return res.toArray(new String[0]);
    }



    class DetectSquares {
        //map<x,map<y,nums>>:先找同x不同y,确定边长,找左右对应边长的y,三个点数乘积
        HashMap<Integer, HashMap<Integer, Integer>> xs;

        public DetectSquares() {
            xs = new HashMap<>();
        }

        public void add(int[] point) {
            int x = point[0], y = point[1];//先获取x对应的ys
            HashMap<Integer, Integer> ys = xs.getOrDefault(x, new HashMap<>());
            ys.put(y, ys.getOrDefault(y, 0) + 1);//添加ys
            xs.put(x, ys);//添加xs
        }

        public int count(int[] point) {
            //先y轴找不同于(x,y)的点
            int x = point[0], y = point[1];
            HashMap<Integer, Integer> ys = xs.getOrDefault(x, new HashMap<>());

            int res = 0;//每次+其他三个点的个数的乘积
            for (int py : ys.keySet()) { //遍历x相同y不同的点
                if (py == y) continue;

                int len = y - py;//确定正方形边长
                int num1 = ys.get(py);//第一个点的重复个数

                int[] pxs = {x - len, x + len};
                for (int px : pxs) {//遍历当前边长确定的x位置
                    HashMap<Integer, Integer> pxy = //获取当前px点的ys
                            xs.getOrDefault(px, new HashMap<>());
                    int num2 = pxy.getOrDefault(y, 0);//第二个点
                    int num3 = pxy.getOrDefault(py, 0);//第三个点

                    res += num1 * num2 * num3;
                }
            }
            return res;
        }
    }












}
