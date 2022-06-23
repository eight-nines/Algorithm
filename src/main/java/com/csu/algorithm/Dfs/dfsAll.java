package com.csu.algorithm.Dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dfsAll {


    static int rows, cols, res = 0;
    static char[][] arr;

    public int numIslands(char[][] grid) {

        //dfs：确定边界，遍历矩阵，遇到1，将该岛所有1变成0
        rows = grid.length;
        cols = grid[0].length;
        arr = grid;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (grid[x][y] == '0') continue;
                res++;//遇到一个新岛
                dfs(x, y);//把该岛沉掉
            }
        }
        return res;
    }

    static void dfs(int x, int y) {
        arr[x][y] = '0';//把当前岛沉掉

        //融合边界条件，向当前目标上下左右扩散，沉周围岛
        if (x != 0 && arr[x - 1][y] == '1') dfs(x - 1, y);
        if (x != rows - 1 && arr[x + 1][y] == '1') dfs(x + 1, y);
        if (y != 0 && arr[x][y - 1] == '1') dfs(x, y - 1);
        if (y != cols - 1 && arr[x][y + 1] == '1') dfs(x, y + 1);
    }


    class Solution3 {
        int rows, cols, res = 0;
        int[][] arr;

        public int closedIsland(int[][] grid) {

            //dfs:把边界岛屿都淹掉，剩下的都是封闭岛屿
            rows = grid.length; cols = grid[0].length;
            arr = grid;

            //先沉边界
            for (int x = 0; x < rows; x++) { //左右
                dfs(x, 0); dfs(x, cols - 1);
            }
            for (int y = 0; y < cols; y++) { //上下
                dfs(0, y); dfs(rows - 1, y);
            }

            //统计封闭岛屿
            for (int x = 1; x < rows - 1; x++) {
                for (int y = 1; y < cols - 1; y++) {
                    if (arr[x][y] == 1) continue;
                    //把该岛沉掉，并更新结果
                    res++; dfs(x, y);
                }
            }
            return res;
        }

        void dfs(int x, int y) {

            if (x < 0 || y < 0 || x >= rows || y >= cols
                    || arr[x][y] == 1) return;

            arr[x][y] = 1;//把当前岛沉掉

            //融合边界条件，向当前目标上下左右扩散，沉周围岛
            dfs(x - 1, y); dfs(x + 1, y);
            dfs(x, y - 1); dfs(x, y + 1);
        }
    }


    class Solution2 {
        int rows, cols, res = 0;

        public int countSubIslands(int[][] grid1, int[][] grid2) {
            //dfs：1中为0,2中为1的岛屿全淹掉，剩下的就是了
            rows = grid1.length;
            cols = grid1[0].length;

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    //1中为0,2中为1的岛屿全淹掉
                    if (grid1[x][y] == 0 && grid2[x][y] == 1)
                        dfs(grid2,x,y);
                }
            }

            //统计子岛屿
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (grid2[x][y] == 0) continue;
                    res++;dfs(grid2,x,y);
                }
            }
            return res;
        }

        void dfs(int[][] arr,int x, int y) {

            if (x < 0 || y < 0 || x >= rows || y >= cols
                    || arr[x][y] == 0) return;

            arr[x][y] = 0;//把当前岛沉掉

            //融合边界条件，向当前目标上下左右扩散，沉周围岛
            dfs(arr,x - 1, y); dfs(arr,x + 1, y);
            dfs(arr,x, y - 1); dfs(arr,x, y + 1);
        }
    }

    class Solution {
        int rows, cols, res = 0;
        char[][] arr;
        boolean flag = false;

        public void solve(char[][] board) {

            //dfs：把边界O换成#，把中间O换成X，再把#还原成O
            rows = board.length;
            cols = board[0].length;
            arr = board;

            //边界O换成#
            change(); flag=true;

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (arr[x][y] == 'O') arr[x][y] = 'X';
                }
            }
            //再把#还原成O
            change();
        }

        void change() {
            for (int x = 0; x < rows; x++) { //左右
                dfs(x, 0); dfs(x, cols - 1);
            }
            for (int y = 0; y < cols; y++) { //上下
                dfs(0, y); dfs(rows - 1, y);
            }
        }

        void dfs(int x, int y) {

            if (x < 0 || y < 0 || x >= rows || y >= cols) return;

            if(!flag && arr[x][y]=='O') arr[x][y] = '#';
            else if(flag && arr[x][y]=='#') arr[x][y] = 'O';
            else return;
            dfs(x - 1, y); dfs(x + 1, y);
            dfs(x, y - 1); dfs(x, y + 1);
        }
    }








    class Solution1 {
        int rows, cols, res = 0;
        int[][] arr;

        public int maxAreaOfIsland(int[][] grid) {

            //dfs：确定边界，遍历矩阵，遇到1沉岛，统计面积，更新结果
            rows = grid.length;
            cols = grid[0].length;
            arr = grid;

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (arr[x][y] == 0) continue;
                    //把该岛沉掉，并更新最大值
                    res = Math.max(res, dfs(x, y));
                }
            }
            return res;
        }

        int dfs(int x, int y) {

            arr[x][y] = 0;//把当前岛沉掉

            int s1 = 0, s2 = 0, s3 = 0, s4 = 0;//统计岛屿面积，默认为0

            //融合边界条件，向当前目标上下左右扩散，沉周围岛
            if (x != 0 && arr[x - 1][y] == 1) s1 = dfs(x - 1, y);
            if (x != rows - 1 && arr[x + 1][y] == 1) s2 = dfs(x + 1, y);
            if (y != 0 && arr[x][y - 1] == 1) s3 = dfs(x, y - 1);
            if (y != cols - 1 && arr[x][y + 1] == 1) s4 = dfs(x, y + 1);

            return 1 + s1 + s2 + s3 + s4;
        }
    }

}
