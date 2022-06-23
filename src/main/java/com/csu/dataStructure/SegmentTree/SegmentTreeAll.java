package com.csu.dataStructure.SegmentTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class SegmentTreeAll {


    class Solution {


        class SegmentTree {
            int[] max;
            int[] change;
            boolean[] update;

            public SegmentTree(int size) {
                int len = size + 1;
                max = new int[len * 4];//每个范围节点的范围内最大值
                change = new int[len * 4];//每个范围节点的范围内最大值
                update = new boolean[len * 4];//每个范围节点的范围内最大值
            }

            void pushUp(int rt) {//更新当前节点值，子范围最大值
                max[rt] = Math.max(max[rt * 2], max[rt * 2 + 1]);
            }

            void pushDown(int rt, int ln, int rn) {//向下分发任务
                int l = rt * 2, r = l + 1;//左右子节点下标
                if (update[rt]) {//分发更新任务
                    update[l] = update[r] = true;
                    change[l] = change[r] = change[rt];
                    max[l] = max[r] = change[rt];
                    update[rt] = false;
                }
            }

            void update(int L, int R, int C, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    max[rt] = C;
                    update[rt] = true;
                    change[rt] = C;
                    return;
                }

                int mid = l + (r - l) / 2;
                pushDown(rt, mid - l + 1, r - mid);
                if (L <= mid) update(L, R, C, l, mid, rt * 2);
                if (R > mid) update(L, R, C, mid + 1, r, rt * 2 + 1);
                pushUp(rt);
            }

            int query(int L, int R, int l, int r, int rt) {
                if (L <= l && r <= R) return max[rt];

                int mid = l + (r - l) / 2;
                pushDown(rt, mid - l + 1, r - mid);
                int left = 0, right = 0;
                if (L <= mid) left = query(L, R, l, mid, rt * 2);
                if (R > mid) right = query(L, R, mid + 1, r, rt * 2 + 1);

                return Math.max(left, right);
            }
        }

        //把每个正方形的x轴起点、终点按顺序加到map中，value为索引，1开始
        //为了配合线段树使用，相当于线段树中的arr
        HashMap<Integer, Integer> index(int[][] positions) {
            TreeSet<Integer> set = new TreeSet<>();
            for (int[] pos : positions) {
                //处理成左闭右闭，[1,2]->起点1，终点2
                set.add(pos[0]);//起点
                set.add(pos[0] + pos[1] - 1);//终点
            }
            HashMap<Integer, Integer> map = new HashMap<>();
            int count = 0;
            for (Integer point : set) map.put(point, ++count);
            return map;
        }


        public List<Integer> fallingSquares(int[][] positions) {
            HashMap<Integer, Integer> map = index(positions);
            int len = map.size();
            SegmentTree tree = new SegmentTree(len);

            int max = 0;//当前全局最大高度
            ArrayList<Integer> list = new ArrayList<>(positions.length);
            // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
            for (int[] pos : positions) {
                int L = map.get(pos[0]);
                int R = map.get(pos[0] + pos[1] - 1);
                //查L-R在1-N的最大高度 + 边长
                int height = pos[1] + tree.query(L, R, 1, len, 1);
                max = Math.max(max, height);
                list.add(max);//更新当前范围的高度
                tree.update(L, R, height, 1, len, 1);
            }
            return list;
        }
    }

//    class Solution1 {
//        public int minAoe3(int[] x, int[] hp, int range) {
//            int len = x.length;
//
//            //以i为中心，左/右侧能影响到哪，下标从1开始，不从0开始
//            int[] coverLeft = new int[len + 1];
//            int[] coverRight = new int[len + 1];
//            int left = 0, right = 0;
//
//            for (int i = 0; i < len; i++) {
//                while (x[i] - x[left] > range) left++;
//                while (right < len && x[right] - x[i] <= range) right++;
//
//                coverLeft[i] = left + 1;//下标从1开始，要+1
//                coverRight[i] = right;//多跳1步，-1+1=0
//            }
//
//            int[] mid = new int[len + 1];//以i为左边缘点，中点的下标，下标从1开始
//            int index = 0;
//            for (int i = 0; i < len; i++) {
//                while (index < len && x[index] - x[i] <= range) index++;
//                mid[i + 1] = index;//多跳一步 -1+1=0
//            }
//
//        }


//    }
}