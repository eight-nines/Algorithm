package com.csu.algorithm.UnionFind;


//https://leetcode-cn.com/problems/number-of-provinces/submissions/
public class FindCircleNum {




    //[[1,1,0],
    // [1,1,0],
    // [0,0,1]]

    //[[0,1,0],
    // [1,0,0],
    // [0,0,1]]


    //对角线消0:100%
    public int findCircleNum(int[][] M) {
        int ans = 0;
        //每一行、每一列都代表一个城市
        for(int i = 0;i<M.length;i++){//遍历行
            //每一个城市（行）只属于一个省
            //之前没有属于其他省时才算作一个省
            if(dfs(M,i)>0) ans++;
        }
        return ans;
    }

    int dfs(int[][] M,int i){
        //重复遍历（以属于其他省）时，对角线为0
        if(M[i][i] != 1) return 0;

        int ans = 1;//本行还没遍历过（不属于其他省）
        M[i][i] = 0;//对角线归0，标志本城市有归属了

        //dfs所有本城市连同的城市
        //将其对角线设为0：归属本城市的省
        for(int j = 0;j<M.length;j++){
            if(M[i][j] == 1) dfs(M,j);
        }
        return ans;
    }


    //并查集：QU+rank+PH：95.74%
    static class Solution {
        int[] parents;
        int[] rank;

        public int find(int v) {
            if (parents[v] == v) return v;
            while(parents[v] != v){//PH:路径减半
                parents[v] = parents[parents[v]];
                v = parents[v];
            }
            return v;
        }

        public void union(int v1, int v2) {
            int p1 = find(v1);int p2 = find(v2);
            if (p1 == p2) return;

            if(rank[p1]<rank[p2]) parents[p1] = p2;
            else if(rank[p2]<rank[p1]) parents[p2] = p1;
            else {
                parents[p1] = p2;
                rank[p2]++;
            }
        }

        public int findCircleNum(int[][] M) {
            int len = M.length;
            parents = new int[len];
            rank = new int[len];

            //初始化parents[i];rank[i]
            for (int i = 0; i < len; i++) {
                parents[i] = i;rank[i] = 1;
            }
            for (int i = 0; i < len; i++) {
                //对称矩阵
                for (int j = i + 1; j < len; j++) {
                    if (M[i][j] == 1) union(i, j);
                }
            }
            //统计父节点个数
            int res = 0;
            for (int i = 0; i < len; i++) {
                if (parents[i] == i) res++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.findCircleNum(new int[][]{{1,1,1},{1,1,1},{1,1,1}}));
    }



}
