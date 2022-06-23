package com.csu.algorithm.Tree;

public class DeleteNode {


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


    class Solution {
//        int key;
//        TreeNode min;
//
//        public TreeNode deleteNode(TreeNode root, int key) {
//            this.key = key;
//            return delete(root);
//        }

        //返回删除该节点后 该位置的节点
        public TreeNode delete(TreeNode root, int key) {
            if (root == null) return null;

            if (root.val < key) root.right = delete(root.right,key);
            else if (root.val > key) root.left = delete(root.left,key);
            else {//相等，分三种情况
                //解决只有一个或没有子节点的情况
                if (root.left == null) return root.right;
                if (root.right == null) return root.left;
                //左右都有，获取右侧最小节点替换当前节点
                TreeNode min = getMin(root.right);
                //并把右子树中要删除的key设为min的值
                key = root.val = min.val;
                root.right = delete(root.right,key);
            }
            return root;
        }
        //获取以root为根的树中最小节点，BST中为最左侧叶子节点
        public TreeNode getMin(TreeNode root) {
            while (root.left != null) root = root.left;
            return root;
        }
    }


}
