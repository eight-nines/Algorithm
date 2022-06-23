package com.csu.algorithm.Tree;

import java.util.LinkedList;

public class IsCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isCBT1(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<Node>();
		// 是否遇到过左右两个孩子不双全的节点
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;
			if (
			// 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
			(leaf && (l != null || r != null)) || (l == null && r != null)

			) {
				return false;
			}
			if (l != null) {
				queue.add(l);
			}
			if (r != null) {
				queue.add(r);
			}
			if (l == null || r == null) {
				leaf = true;
			}
		}
		return true;
	}

	public static boolean isCBT2(Node head) {
		// 返回处理后的信息类中的数据
		return process(head).isCBT;
	}

	// 每棵子树需要向父节点提供的信息
	public static class Info {
		public boolean isFull;
		public boolean isCBT;
		public int height;

		public Info(boolean full, boolean cbt, int h) {
			isFull = full;
			isCBT = cbt;
			height = h;
		}
	}

	// 对信息进行处理
	public static Info process(Node x) {
	// 过滤空
	if (x == null) {
		return new Info(true, true, 0);
	}
	// 对左右信息进行递归
	Info leftInfo = process(x.left);
	Info rightInfo = process(x.right);
	// 高度 = 左右高度的最大值 + 当前节点一层
	int height = Math.max(leftInfo.height, rightInfo.height) + 1;
	// 当前树是满二叉树：左满，右满，高度相等
	boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
	// 初始化isCBT
	boolean isCBT = false;
	// 左满右满，整体满，必是CBT
	if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
		isCBT = true;
	} // 左完右满，左低右高
	else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
		isCBT = true;
	} // 左满右满，左低右高
	else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
		isCBT = true;
	} // 左满右完，高度相等
	else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
		isCBT = true;
	}
	return new Info(isFull, isCBT, height);
}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isCBT1(head) != isCBT2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
