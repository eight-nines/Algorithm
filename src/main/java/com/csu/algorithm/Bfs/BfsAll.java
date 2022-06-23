package com.csu.algorithm.Bfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class BfsAll {










    


    public int[][] highestPeak(int[][] isWater) {
        //多源bfs:所有水域入队作为bfs起点，标记未被访问过的陆地高度为-1
        //起点遍历下一层，陆地没被访问过=当前高度+1，并入队，作为下层遍历的起点
        int rows = isWater.length, cols = isWater[0].length;
        int[][] res = new int[rows][cols];
        Deque<int[]> que = new LinkedList<>();//队列

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //水域作为bfs起点入队，且高度标记为0
                if (isWater[i][j] == 1) que.offer(new int[]{i, j});
                else res[i][j] = -1;//陆地高度先标记为-1
            }
        }

        int[][] recur = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!que.isEmpty()) {
            int[] cur = que.poll();
            int x = cur[0], y = cur[1];

            for (int[] next : recur) {//bfs:遍历出队节点的下一层
                int nx = x + next[0], ny = y + next[1];
                if (nx < 0 || nx == rows || ny < 0 || ny == cols) continue;

                if (res[nx][ny] != -1) continue;//遍历过 or 水域
                res[nx][ny] = res[x][y] + 1;//当前节点高度+1
                //节点入队，作为下一层遍历的起点
                que.offer(new int[]{nx, ny});
            }
        }
        return res;
    }






















}
