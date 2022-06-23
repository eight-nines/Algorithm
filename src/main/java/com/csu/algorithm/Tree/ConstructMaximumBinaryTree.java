package com.csu.algorithm.Tree;


public class ConstructMaximumBinaryTree {


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


    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    //构建节点
    public TreeNode build(int[] nums, int left, int right) {
        if (left > right) return null;

        int index = -1, max = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            if(max>=nums[i]) continue;
            max= nums[i];
            index = i;
        }
        //返回当前数组范围内的最大值构成的节点
        TreeNode root = new TreeNode(max);
        root.left = build(nums, left, index - 1);
        root.right = build(nums, index + 1, right);
        return root;
    }

    class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            // 根据题意，1001 为最大值+1，此处设为根节点
            TreeNode root = new TreeNode(1001);
            init(nums, root, 0);
            return root.right;
        }

        private int init(int[] nums, TreeNode parent, int index) {
            while (index >= 0 && index < nums.length) {
                if (nums[index] < parent.val) {//当前值小于父节点值，往下加
                    TreeNode now = new TreeNode(nums[index]);
                    // region 如果当前结点有 right，说明是回溯来的，把右节点做为新节点的左子节点
                    TreeNode tmp = parent.right;
                    parent.right = now;
                    now.left = tmp;
                    // endregion
                    index = init(nums, now, index + 1);
                } else {//当前节点值大于父节点，向上回溯
                    return index;
                }
            }
            return -1;
        }
    }
}
