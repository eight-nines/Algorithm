package com.csu.algorithm.Bfs;

import java.util.LinkedList;
import java.util.Queue;

public class MinDepth {

















    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }



    public int minDepth(TreeNode root) {
        if(root==null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth =1;// root 本身就是一层，depth 初始化为 1

        while(!queue.isEmpty()){
            int size = queue.size();
            /* 将当前队列(一步/一层)中的所有节点向四周扩散 */
            for (int i = 0; i <size; i++) {
                TreeNode cur = queue.poll();
                /* 判断是否到达终点 */
                if(cur.left==null && cur.right==null) return depth;
                /* 将 cur 的相邻节点加入队列 */
                if(cur.left !=null ) queue.offer(cur.left);
                if(cur.right !=null ) queue.offer(cur.right);
            }
            depth++;/* 这里增加步数 */
        }
        return depth;
    }






}
