package com.csu.algorithm.Tree;

public class ConnectNode {










    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };









    //方法一：60%
    class Solution {
        public Node connect(Node root) {
            if (root == null) return null;

            connectTwoNode(root.left, root.right);
            return root;
        }

        //连接两个节点
        public void connectTwoNode(Node node1, Node node2) {
            if (node1 == null || node2 == null) return;

            // 将传入的两个节点连接
            node1.next = node2;
            // 连接相同父节点的两个子节点
            connectTwoNode(node1.left, node1.right);
            connectTwoNode(node2.left, node2.right);
            // 连接跨越父节点的两个子节点
            connectTwoNode(node1.right, node2.left);
        }
    }

    //方法二：100%
    class Solution1 {
        public Node connect(Node root) {
            if (root == null) return null;

            //左节点不为空，右节点也不会为空
            if (root.left != null) {
                //同父节点连接
                root.left.next = root.right;
                //跨父节点连接（如果父节点有next）
                if (root.next != null) {
                    root.right.next = root.next.left;
                }
            }
            connect(root.left);
            connect(root.right);
            return root;
        }
    }













}
