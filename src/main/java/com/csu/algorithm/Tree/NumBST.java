package com.csu.algorithm.Tree;


import java.util.LinkedList;
import java.util.List;

public class NumBST {











    int[][] dp;//动态规划数组

    public int numTrees(int n) {
        dp = new int[n+1][n+1];
        return count(1,n);
    }

    //返回当前闭区间内的BST数量
    public int count(int left,int right) {
        //此时对应于没有左/右子树,即为null,但也是一种情况，返回1
        //对于下面的for循环,left>i-1没有左子树,i+1>right没有右子树
        if(left > right) return 1;

        if(dp[left][right] !=0) return dp[left][right];

        int res = 0;
        for (int i = left; i <=right; i++) {
            //res += 左子树的BST数*右子树的BST数
            res += count(left,i-1) * count(i+1,right);
        }

        dp[left][right] = res;
        return res;
    }




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



    public List<TreeNode> generateTrees(int n) {
        return build(1,n);
    }

    //返回此闭区间内所有的BST根节点列表
    public List<TreeNode> build(int left,int right) {
        LinkedList<TreeNode> res = new LinkedList<>();
        //没有左/右子树的情况，无可能根节点
        if(left > right) {
            res.add(null);return res;
        }

        for (int i = left; i <=right; i++) {
            //左/右子树可能的根节点列表
            List<TreeNode> leftRoots = build(left, i - 1);
            List<TreeNode> rightRoots = build(i + 1, right);

            for (TreeNode leftRoot : leftRoots) {
                for (TreeNode rightRoot : rightRoots) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftRoot;root.right = rightRoot;
                    res.add(root);
                }
            }
        }
        return res;
    }











}
