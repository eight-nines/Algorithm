package com.csu.algorithm.Other;

import java.io.*;


public class Main {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            val = val;
        }
    }

    static StreamTokenizer in = new StreamTokenizer(
            new BufferedReader(new InputStreamReader(System.in)));
    static PrintWriter out = new PrintWriter(
            new OutputStreamWriter(System.out));

    public static int nextInt() {
        try {
            in.nextToken();
            return (int) in.nval;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TreeNode read() {
        int fa = nextInt(), l = nextInt(), r = nextInt();

        TreeNode root = new TreeNode(fa);
        if (l != 0) root.left = read();
        if (r != 0) root.right = read();

        return root;
    }

    public static void main(String[] args) {
        nextInt();nextInt();//过掉第一行
        TreeNode root1 = read();

        nextInt();nextInt();
        TreeNode root2 = read();

        out.print(problem(root1, root2));
        out.flush();
    }

    public static boolean problem(TreeNode root1, TreeNode root2) {
        //前序：从上到下，比较节点是否和r2相等，找到第一个等于r2的节点开启比较
        //是：两个一起向下比较; 否：比较左子和root2,右子和root2
        if (root1 == null) return false;//停止向下遍历，null.left

        return compare(root1, root2) || problem(root1.left, root2)
                || problem(root1.right, root2);
    }

    public static boolean compare(TreeNode root1, TreeNode root2) {
        if (root2 == null) return true;
        if (root1 == null || root1.val != root2.val) return false;

        //当前节点相同，且root2还能向下遍历，继续向下比较
        return compare(root1.left, root2.left)
                && compare(root1.right, root2.right);
    }
}