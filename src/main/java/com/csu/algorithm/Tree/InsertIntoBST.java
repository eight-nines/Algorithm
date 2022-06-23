package com.csu.algorithm.Tree;

public class InsertIntoBST {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(6);
        insertIntoBST(root,8);
        delete(root,2);
        recur(root);
    }

    //中序遍历-升序
    public static void recur(TreeNode root) {
        if(root==null) return;
        recur(root.left);
        System.out.println(root.val);
        recur(root.right);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }




    public static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (root.val > val) root.left = insertIntoBST(root.left,val);
        else if (root.val < val) root.right = insertIntoBST(root.right,val);
        else return root;

        return root;
    }

    public static TreeNode delete(TreeNode root, int key) {
        if (root == null) return null;

        if (root.val < key) root.right = delete(root.right,key);
        else if (root.val > key) root.left = delete(root.left,key);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode min = getMin(root.right);
            key = root.val = min.val;
            root.right = delete(root.right,key);
        }
        return root;
    }

    public static TreeNode getMin(TreeNode root) {
        while (root.left != null) root = root.left;
        return root;
    }


}
