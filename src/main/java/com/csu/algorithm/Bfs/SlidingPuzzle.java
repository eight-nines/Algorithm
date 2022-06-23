package com.csu.algorithm.Bfs;

import java.util.*;

public class SlidingPuzzle {




    public int slidingPuzzle(int[][] board) {
        /* 将board展平为一维，存入start作为初始状态 */
        String start = "";
        /* 最终要凑出的目标 */
        String target = "123450";
        /* 初始化start */
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                start += (char) (board[i][j] + '0');
            }
        }

        // 记录一维字符串的索引位置实际的相邻索引(指拼图中相邻)
        int[][] neighbor = {
                { 1, 3 },
                { 0, 4, 2 },
                { 1, 5 },
                { 0, 4 },
                { 3, 1, 5 },
                { 4, 2 }
        };

        //******* BFS 算法框架开始 *******//
        Queue<String> q = new LinkedList<>();
        Set<String> visited= new HashSet<>();
        q.offer(start); visited.add(start);

        int step = 0;

        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                // 判断是否达到目标局面
                if (target.equals(cur)) return step;

                // 找到数字 0 的索引
                int zeroIdx = cur.indexOf('0');
                // 将数字 0 和相邻的数字交换位置
                for (int idx : neighbor[zeroIdx]) {
                    String newBoard = swap(cur, zeroIdx,idx);
                    // 防止走回头路
                    if (!visited.contains(newBoard)) {
                        q.offer(newBoard);visited.add(newBoard);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    //双向bfs
    public int slidingPuzzle1(int[][] board) {
        /* 将board展平为一维，存入start作为初始状态 */
        String start = "";
        /* 最终要凑出的目标 */
        String target = "123450";
        /* 初始化start */
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                start += (char) (board[i][j] + '0');
            }
        }

        // 记录一维字符串的索引位置实际的相邻索引(指拼图中相邻)
        int[][] neighbor = {
                { 1, 3 },
                { 0, 4, 2 },
                { 1, 5 },
                { 0, 4 },
                { 3, 1, 5 },
                { 4, 2 }
        };

        //******* BFS 算法框架开始 *******//
        Set<String> q1 = new HashSet<>();
        Set<String> q2 = new HashSet<>();
        Set<String> visited= new HashSet<>();
        q1.add(start); q2.add(target);//双向起点

        int step = 0;

        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.size() > q2.size()) {
                // 交换 q1 和 q2 , 每次遍历较小的集合
                Set<String> tem = q1;
                q1 = q2;
                q2 = tem;
            }
            Set<String> temp = new HashSet<>();//存放扩散q1结果

            for (String cur : q1) {
                /* 判断是否到达终点,双向集合出现交集 */
                if (q2.contains(cur)) return step;
                visited.add(cur);//在每次弹出的时候添加

                int zeroIdx = cur.indexOf('0');
                for (int idx : neighbor[zeroIdx]) {
                    String newBoard = swap(cur, zeroIdx,idx);
                    if (!visited.contains(newBoard)) temp.add(newBoard);
                }
            }
            step++;
            q1 = q2; q2 = temp;
        }
        return -1;
    }

    public String swap(String s,int i,int j) {
        //此处由上层确保了i,j不同位，所以可以用位运算
        char[] ch = s.toCharArray();
        ch[i]^=ch[j];
        ch[j]^=ch[i];
        ch[i]^=ch[j];
        return new String(ch);
    }





}
