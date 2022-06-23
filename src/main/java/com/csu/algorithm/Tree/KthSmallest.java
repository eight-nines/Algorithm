package com.csu.algorithm.Tree;

public class KthSmallest {


    public class TreeNode {
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


    class Solution {
        int res,rank;
        public int kthSmallest(TreeNode root, int k) {
            this.rank = k;
            dfs(root);
            return res;
        }
        public void dfs(TreeNode root) {
            if (root == null) return;

            dfs(root.left);

            //按中序遍历的顺序对每个节点执行的语句
            //当前节点是不是目标节点，是的话返回
            if(--rank == 0) {
                res = root.val;
                return;
            }

            dfs(root.right);
        }
    }

}
