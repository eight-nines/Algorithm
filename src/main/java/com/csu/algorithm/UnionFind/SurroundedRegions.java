package com.csu.algorithm.UnionFind;

public class SurroundedRegions {










    //dfs:99.94%
    class Solution {
        int rows,cols;
        boolean flag = false;//是否恢复

        public void solve(char[][] board) {
            rows = board.length;
            cols = board[0].length;

            //将边界'O'换成'#'
            change(board);

            for(int i=0; i<rows; i++) {//遍历行
                for(int j=0; j<cols; j++) {//遍历列
                    if(board[i][j]!='O') continue;//是0直接过
                    board[i][j]='X';
                }
            }
            //开启恢复模式
            flag = true;
            //将边界'#'换成'O'
            change(board);
        }

        void change(char[][] board) {
            for (int i = 0; i < rows; i++) {
                dfs(board,i,0);dfs(board,i,cols-1);
            }
            for (int j = 0; j < cols; j++) {
                dfs(board,0,j);dfs(board,rows-1,j);
            }
        }

        void dfs(char[][] board, int i, int j) {
            // 边界检查
            if(i < 0 || i >= rows || j < 0|| j >= cols ) return;
            if(!flag && board[i][j] == 'O'){
                board[i][j] = '#';
            }else if(flag && board[i][j] == '#'){
                board[i][j] = 'O';
            }else return;
            dfs(board,i+1,j);dfs(board,i-1,j);dfs(board,i,j+1);dfs(board,i,j-1);
        }
    }


    //并查集:12.86%
    class Solution1 {
        public void solve(char[][] board) {
            int rows = board.length;
            int cols = board[0].length;
            int dummy = rows * cols;//边界'O'的连同节点
            //dfs遍历的辅助数组
            int[][] d = new int[][]{{1,0}, {0,1}, {0,-1}, {-1,0}};
            UF uf = new UF(dummy);

            for (int i=0; i<rows; i++) {
                for (int j=0; j<cols; j++) {
                    if (board[i][j] == 'O') {// 将边界的 O 与 dummy 连通
                        if (i==0 || i==rows-1 || j==0 || j==cols-1) {
                            uf.union(i*cols+j, dummy);
                        } else {// 将非边界 O 与上下左右的 O 连通
                            for (int k=0; k<4; k++) {
                                int x = i+d[k][0];
                                int y = j+d[k][1];
                                if (board[x][y] != 'O') continue;
                                uf.union(x*cols+y, i*cols+j);
                            }
                        }
                    }
                }
            }// 所有不和 dummy 连通的 O，都要被替换
            for (int i=1; i<rows-1; i++) {
                for (int j=1; j<cols-1; j++) {
                    if (!uf.connect(i*cols+j, dummy)) board[i][j] = 'X';
                }
            }
        }
    }

    class UF {
        int[] parents;
        int[] ranks;

        public UF(int dummy) {
            //索引 [0.. m*n-1] 都是棋盘内坐标的一维映射,dummy 节点占据索引 m * n
            parents = new int[dummy+1];
            ranks = new int[dummy+1];
            for (int i=0; i<=dummy; i++) {
                parents[i] = i;ranks[i] = 1;
            }
        }

        //PH路径减半
        public int find(int v){
            if(parents[v]==v) return v;
            while(parents[v]!=v){
                parents[v] = parents[parents[v]];
                v = parents[v];
            }
            return v;
        }
        //基于rank树高优化
        public void union(int v1,int v2){
            int p1 = find(v1),p2 = find(v2);
            if(p1==p2) return;
            if(ranks[p1]>ranks[p2]) parents[p2] = p1;
            else if(ranks[p2]>ranks[p1]) parents[p1] = p2;
            else{
                parents[p1] = p2;ranks[p2]++;
            }
        }

        public boolean connect(int v1,int v2) {
            return find(v1) == find(v2);
        }
    }


}
