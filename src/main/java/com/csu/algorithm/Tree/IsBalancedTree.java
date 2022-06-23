package com.csu.algorithm.Tree;


public class IsBalancedTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}


	public static boolean isBalance(Node head) {
		// 用数组记录不用担心变量的作用域问题
		boolean[] res = new boolean[1];
		res[0] = true;
		// 开始的时候深度为0
		int len = getHeight(head, 0, res);
		System.out.println(len);
		return res[0];
	}
	// 递归函数
	public static int getHeight(Node head, int level, boolean[] res) {
		// 过滤空树，是空树直接返回已有深度
		if (head == null) {
			return level ;
		}
		// 递归左子树，每次深度+1
		// 此时+1的深度是已有的深度的实现，无论下一层是否是null
		int lH = getHeight(head.left, level + 1, res);
		// 如果子树已经是不平衡了，直接返回即可
		if (!res[0]) {
			return level;
		}
		int rH = getHeight(head.right, level + 1, res);
		if (!res[0]) {
			return level;
		}
		// 比较左右子树深度
		if (Math.abs(lH - rH) > 1) {
			res[0] = false;
		}
		// 返回左右子树中深度大的那一个的深度
		return Math.max(lH, rH);
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		System.out.println(isBalance(head));

	}

}
