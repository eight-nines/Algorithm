package com.csu.algorithm.UnionFind;

import java.util.HashSet;

public class NumberOfDistinctIslands {







    int rows,cols;
    StringBuilder sb;
    public int numberofDistinctIslands(int[][] grid) {
        rows = grid.length; cols = grid[0].length;
        // 记录所有岛屿的序列化结果
        HashSet<String> islands = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) continue;
                // 淹掉这个岛屿，同时存储岛屿的序列化结果
                sb = new StringBuilder();
                dfs(grid, i, j,  'x');
                islands.add(sb.toString());
            }
        }
        // 不相同的岛屿数量
        return islands.size();
    }
    void dfs(int[][] grid, int i, int j, int prefix) {
        if (i < 0 || j < 0 || i >= rows || j >= cols || grid[i][j] == 0) return;

        grid[i][j] = 0;
        //添加本步的标志
        sb.append(prefix);

        dfs(grid, i - 1, j, 'u'); // 上
        dfs(grid, i + 1, j, 'd'); // 下
        dfs(grid, i, j - 1, 'l'); // 左
        dfs(grid, i, j + 1, 'r'); // 右
        //添加回退的标志
        sb.append('z');
    }






}
