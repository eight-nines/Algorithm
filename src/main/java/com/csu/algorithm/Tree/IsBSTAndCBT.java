package com.csu.algorithm.Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class IsBSTAndCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBST(Node head) {
		if (head == null) {
			return true;
		}
		boolean res = true;
		Node pre = null;
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
				}
			}
			if (pre != null && pre.value > cur1.value) {
				res = false;
			}
			pre = cur1;
			cur1 = cur1.right;
		}
		return res;
	}

	public static boolean isBST2(Node head){
		int preValue = Integer.MIN_VALUE;
		boolean res = true;
		if (head != null) {
			Stack<Node> stack = new Stack<>();
			// 栈非空:对应弹栈；节点非空：对应压栈
			while (!stack.isEmpty() || head != null) {
				if (head != null) {
					// 节点非空，压栈-->节点左移
					stack.push(head);
					head = head.left;
				} else {
					// 节点空（叶子节点的下一级）+栈非空
					// 弹栈（父节点）-->节点变成上层节点的右节点
					head = stack.pop();
					if (!res){
						return res;
					}else {
						res = head.value > preValue;
						preValue = head.value;
					}
					head = head.right;
				}
			}
		}
		return res;
	}


	public static boolean isCBT(Node head) {
		// 过滤空树
		if (head == null) {
			return true;
		}
		// 实现双端链表
		Queue<Node> queue = new LinkedList<Node>();
		// 判断是否要开启“叶子节点”阶段
		boolean leaf = false;
		// 可复用左右节点
		Node l = null;
		Node r = null;
		// 把头结点加入队列
		queue.offer(head);
		// 按层遍历树
		while (!queue.isEmpty()) {
			// 弹出按序（从左到右）待处理节点
			head = queue.poll();
			l = head.left;
			r = head.right;
			// 判断一：左空，右不空-->不是CBT
			// 判断二：开启“叶节点阶段”后，左或右不为空
			// 除了开启“阶段”的那个节点，左右必须都为空
			if ( (l == null && r != null) || (leaf && (l != null || r != null)) ) {
				return false;
			}
			// 左不为空，压左
			if (l != null) {
				queue.offer(l);
			}// 右不为空压右
			if (r != null) {
				queue.offer(r);
			} else {
				// 只有三种情况：左空右不空（已被过滤），左空右空，左不空，右空
				// 故此时 右为空，即可开启“阶段”
				leaf = true;
			}
		}
		return true;
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}


	public static void main(String[] args) {
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.left = new Node(1);

		printTree(head);
		System.out.println(isBST(head));
		System.out.println(isCBT(head));
		System.out.println(isBST2(head));


	}
}