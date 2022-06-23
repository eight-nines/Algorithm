package com.csu.algorithm.UnionFind;

public class CountSubIslands {




















    int rows,cols;

    //思路：2中为‘1’，1中不为‘1’的岛淹掉，剩下的就是子岛屿
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        //两个岛大小相同
        rows = grid1.length;cols = grid1[0].length;

        //淹掉2中为‘1’，1中不为‘1’的岛
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid2[i][j]==1 && grid1[i][j]==0 ){
                    dfs(grid2,i,j);
                }
            }
        }

        //剩下的都是子岛
        int res= 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid2[i][j]==0) continue;
                res++; dfs(grid2,i,j);//淹掉
            }
        }
        return res;
    }

    void dfs(int[][] grid,int i,int j) {
        if(i<0||i>=rows||j<0||j>=cols||grid[i][j]==0) return;

        grid[i][j]=0;
        dfs(grid,i+1,j);dfs(grid,i-1,j);dfs(grid,i,j+1);dfs(grid,i,j-1);
    }












}
