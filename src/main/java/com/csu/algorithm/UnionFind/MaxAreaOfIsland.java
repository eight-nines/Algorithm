package com.csu.algorithm.UnionFind;

public class MaxAreaOfIsland {





    //沉岛思想+dfs:99.96%
    class Solution {
        int row,col;

        public int maxAreaOfIsland(int[][] grid) {
            row = grid.length;
            col = grid[0].length;

            int res = 0;
            for(int i=0; i<row; i++) {//遍历行
                for(int j=0; j<col; j++) {//遍历列
                    if(grid[i][j]==0) continue;//是0直接过
                    res = Math.max(res, dfs(grid, i, j));
                }
            }
            return res;
        }

        int dfs(int[][] grid, int i, int j) {
            // 边界检查
            if(i < 0 || i >= row || j < 0
                    || j >= col || grid[i][j] == 0) return 0;

            // 不为0 即为1  修改成0 避免下次再计算到
            grid[i][j] = 0;

            // 当前块+四个方向的块数
            return 1+dfs(grid, i+1, j)+dfs(grid, i-1, j)
                    +dfs(grid, i, j+1)+dfs(grid, i, j-1);
        }
    }









}
