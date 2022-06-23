package com.csu.algorithm.Tree;

public class ConvertBST {





















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





    int res;
    public TreeNode convertBST(TreeNode root) {
        dfs(root);
        return root;
    }


    public void dfs(TreeNode root) {
        if(root ==null) return;

        //二叉搜索树倒序遍历
        dfs(root.right);

        res+=root.val;
        root.val = res;

        dfs(root.left);
    }

}
