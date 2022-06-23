package com.csu.algorithm.Tree;

import sun.reflect.generics.tree.Tree;

import java.util.Stack;

public class SearchBST {


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

//    int key;
//    TreeNode target;
//    public TreeNode searchBST(TreeNode root, int val) {
//        this.key = val;
//        return target;
//    }
//
//    //返回目标节点
//    public void dfs(TreeNode root) {
//        if(root ==null || target !=null) return;
//
//        if(root.val < key) dfs(root.right);
//        else if(root.val> key) dfs(root.left);
//
//        target = root;
//    }

    public boolean isValidBST(TreeNode root) {

        //采用前序遍历，第一次比较为根节点，往左限定最大值；往右限定最小值
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;

        //当前节点和左右子树比较
        if (node.val <= min || node.val >= max) return false;

        //左右子树也必须都是BST,往左，上界缩小，往右，下界扩大
        return validate(node.left, min, node.val)
                && validate(node.right, node.val, max);
    }


    //迭代—中序遍历：升序是BST
    public boolean isValidBST1(TreeNode root){
        int preValue = Integer.MIN_VALUE;
        boolean res = true;

        if (root != null) {
            Stack<TreeNode> stack = new Stack<>();
            // 栈非空:对应弹栈；节点非空：对应压栈
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    //压左
                    stack.push(root);
                    root = root.left;
                } else {
                    //弹栈
                    root = stack.pop();
                    //做事
                    if (!res) return res;
                    res = root.val > preValue;
                    preValue = root.val;
                    //压右
                    root = root.right;
                }
            }
        }
        return res;
    }


}


//    public static void preOrderUnRecur(TreeNode head) {
//
//        if (head != null) {
//            Stack<TreeNode> stack = new Stack<TreeNode>();
//            // 先把根节点压栈
//            stack.add(head);
//            // 栈不为空，弹根(当前节点)、做事、压右左
//            while (!stack.isEmpty()) {
//                // 弹根
//                head = stack.pop();
//                // 做事
//                System.out.print(head.value + " ");
//                // 压右左
//                if (head.right != null) stack.push(head.right);
//                if (head.left != null) stack.push(head.left);
//            }
//        }
//    }



















