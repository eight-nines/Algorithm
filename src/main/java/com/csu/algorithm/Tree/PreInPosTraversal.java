package com.csu.algorithm.Tree;

import java.util.Stack;

public class PreInPosTraversal {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static void preOrderRecur(Node head) {
		if(head==null)return;
		System.out.println(head.value+" ");
		preOrderUnRecur(head.left);
		preOrderUnRecur(head.right);
	}

	public static void inOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		inOrderRecur(head.left);
		System.out.print(head.value + " ");
		inOrderRecur(head.right);
	}

	public static void posOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		posOrderRecur(head.left);
		posOrderRecur(head.right);
		System.out.print(head.value + " ");
	}

	public static void preOrderUnRecur(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			// 先把根节点压栈
			stack.add(head);
			// 栈不为空，弹根，压右左
			// 后进先出，保证下个循环弹栈左节点
			while (!stack.isEmpty()) {
				// 弹栈
				head = stack.pop();
				System.out.print(head.value + " ");
				if (head.right != null) {
					stack.push(head.right);
				}
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	public static void inOrderUnRecur(Node head) {
		System.out.print("in-order: ");
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
					// 弹栈（父节点）-->节点变成兄弟节点
					head = stack.pop();
					System.out.print(head.value + " ");
					head = head.right;
				}
			}
		}
		System.out.println();
	}

		public static void posOrderUnRecur1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop();
				// 把打印换成辅助栈的压入
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			// 打印辅助栈
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	public static void posOrderUnRecur2(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			// 先把根节点压栈
			stack.push(head);
			// 指向栈顶节点
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				// 左不为空，头不为左，头不为右：左压栈
				if (c.left != null && head != c.left && head != c.right) {
					stack.push(c.left);
				} // 右不为空，头不为右：右压栈
				else if (c.right != null && head != c.right) {
					stack.push(c.right);
				}
				else {
					System.out.print(stack.pop().value + " ");
					head = c;
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(5);
		head.left = new Node(3);
		head.right = new Node(8);
		head.left.left = new Node(2);
		head.left.right = new Node(4);
		head.left.left.left = new Node(1);
		head.right.left = new Node(7);
		head.right.left.left = new Node(6);
		head.right.right = new Node(10);
		head.right.right.left = new Node(9);
		head.right.right.right = new Node(11);

		// recursive
		System.out.println("==============recursive==============");
		System.out.print("pre-order: ");
		preOrderRecur(head);
		System.out.println();
		System.out.print("in-order: ");
		inOrderRecur(head);
		System.out.println();
		System.out.print("pos-order: ");
		posOrderRecur(head);
		System.out.println();

		// unrecursive
		System.out.println("============unrecursive=============");
		preOrderUnRecur(head);
		inOrderUnRecur(head);
		posOrderUnRecur1(head);
		posOrderUnRecur2(head);

	}

}
