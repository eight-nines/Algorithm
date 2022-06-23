package com.csu.recursion.backTracking;

import java.util.*;

public class BackTrackAll {


    class Solution22 {
        List<String> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        public List<String> generateParenthesis(int n) {
            //回溯：根据剩余的左右括号数，在每个位置都遍历两种可能性
            backTracking(n, n);
            return list;
        }

        void backTracking(int left, int right) {
            if (left == 0 && right == 0) { //左右括号都无，记录并返回
                list.add(sb.toString());
                return;
            }
            //剪枝：括号是加在最后面的，左括号多于右括号，没办法成对
            if (left > right) return;

            if (left > 0) {
                sb.append('(');//处理当前节点
                backTracking(left - 1, right);//递归
                sb.deleteCharAt(sb.length() - 1);//回溯
            }
            if (right > 0) {
                sb.append(')');//处理当前节点
                backTracking(left, right - 1);//递归
                sb.deleteCharAt(sb.length() - 1);//回溯
            }
        }
    }









    class Solution {
        public int longestIncreasingPath(int[][] matrix) {
            //dfs深搜+回溯+备忘录
            int res = 0;
            int m = matrix.length, n = matrix[0].length;
            //记录当前位置延伸出的最大的 递增路径长度
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    res = Math.max(res, dfs(matrix, i, j, dp));
                }
            }
            return res;
        }

        int dfs(int[][] matrix, int x, int y, int[][] dp) {
            if (dp[x][y] != 0) return dp[x][y];

            int m = matrix.length, n = matrix[0].length;
            int n1 = 0, n2 = 0, n3 = 0, n4 = 0;

            int temp = matrix[x][y];
            //matrix[x][y] = -1;//标记走过了，本题其实不用，因为是递增，不会回头
            if (x > 0 && matrix[x - 1][y] > temp) n1 = dfs(matrix, x - 1, y, dp);
            if (y > 0 && matrix[x][y - 1] > temp) n2 = dfs(matrix, x, y - 1, dp);
            if (x < m - 1 && matrix[x + 1][y] > temp) n3 = dfs(matrix, x + 1, y, dp);
            if (y < n - 1 && matrix[x][y + 1] > temp) n4 = dfs(matrix, x, y + 1, dp);
            //matrix[x][y] = temp;//也不需要
            dp[x][y] = Math.max(Math.max(n1, n2), Math.max(n3, n4)) + 1;
            return dp[x][y];
        }
    }




    class Solution2 {
        public int getMaximumGold(int[][] grid) {
            //矩形dfs深搜+回溯：遍历每个节点作为起点，更新最大值
            int res = 0, m = grid.length, n = grid[0].length;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) continue;
                    res = Math.max(res, dfs(grid, i, j));
                }
            }
            return res;
        }

        int dfs(int[][] grid, int x, int y) {
            int m = grid.length, n = grid[0].length;
            if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == 0)
                return 0;

            int temp = grid[x][y];
            grid[x][y] = 0;

            //当前位置出发，4条路径选一条，不能反回
            int n1 = dfs(grid, x + 1, y);
            int n2 = dfs(grid, x - 1, y);
            int n3 = dfs(grid, x, y - 1);
            int n4 = dfs(grid, x, y + 1);

            grid[x][y] = temp;//回溯

            return Math.max(Math.max(n1, n2), Math.max(n3, n4)) + temp;
        }
    }



    class Solution3 {//做法2
        boolean res;
        public boolean exist(char[][] board, String word) {
            //dfs深搜+回溯
            int m = board.length,n=board[0].length;

            for (int i = 0; i <m ; i++) {
                for (int j = 0; j <n ; j++) {
                    //先匹配头字符，从字符串头字符还是递归回溯
                    if(board[i][j]!=word.charAt(0)) continue;
                    dfs(board,word,i,j,0);
                    if(res) return true;
                }
            }
            return res;
        }

        void dfs(char[][] board, String word,int x,int y,int s){
            int m = board.length,n=board[0].length;
            if(x<0||x>=m||y<0||y>=n || res) return;
            if(board[x][y]!=word.charAt(s)) return;

            if(s == word.length()-1) res=true;

            board[x][y]+=100;//相当于标记此位置元素已经使用过了
            dfs(board,word,x+1,y,s+1);
            dfs(board,word,x-1,y,s+1);
            dfs(board,word,x,y-1,s+1);
            dfs(board,word,x,y+1,s+1);
            board[x][y]-=100;//回溯
        }
    }

    class Solution4 {//做法1
        char[][] board;
        char[] word;
        int rows, cols;

        public boolean exist(char[][] board, String word) {
            this.board = board;
            this.word = word.toCharArray();
            rows = board.length;
            cols = board[0].length;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    //先匹配头字符，从字符串头字符还是递归回溯
                    if (this.word[0] == board[i][j] && dfs(i, j, 0))
                        return true;
                }
            }
            return false;
        }

        boolean dfs(int i, int j, int k) {

            if (i > rows - 1 || i < 0 || j > cols - 1 || j < 0) return false;
            if (word[k] != board[i][j]) return false;
            if (k == word.length - 1) return true;

            board[i][j] += 100;//相当于标记此位置元素已经使用过了

            boolean res = dfs(i + 1, j, k + 1) || dfs(i - 1, j, k + 1)
                    || dfs(i, j + 1, k + 1) || dfs(i, j - 1, k + 1);

            board[i][j] -= 100;//回溯

            return res;
        }
    }



}
