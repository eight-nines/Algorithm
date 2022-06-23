package com.csu.algorithm.UnionFind;

public class ClosedIsland {















    int rows,cols;


    public int closedIsland(int[][] grid) {
        int rows = grid.length;int cols = grid[0].length;
        //淹上下边界
        for (int i = 0; i < rows; i++) {
            dfs(grid,i,0);dfs(grid,i,cols-1);
        }
        //淹左右边界
        for (int j = 0; j < cols; j++) {
            dfs(grid,0,j);dfs(grid,rows-1,j);
        }
        //剩下的都是封闭岛
        int res= 0;
        for (int i = 1; i < rows-1; i++) {
            for (int j = 1; j < cols-1; j++) {
                if (grid[i][j]==1) continue;
                res++; dfs(grid,i,j);//淹掉
            }
        }
        return res;

    }

    void dfs(int[][] grid,int i,int j) {
        int rows = grid.length;int cols = grid[0].length;

        if(i<0||i>=rows||j<0||j>=cols||grid[i][j]==1) return;

        grid[i][j]=1;
        dfs(grid,i+1,j);dfs(grid,i-1,j);dfs(grid,i,j+1);dfs(grid,i,j-1);
    }





}
