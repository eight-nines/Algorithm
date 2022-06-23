package com.csu.algorithm.LinkedList;


import javax.naming.NameNotFoundException;

public class ReverseList {

	public static void main(String[] args) {
		Node head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(4);
		head.next.next.next =new Node(5);
		head.next.next.next.next =new Node(6);
		head = insert(head,2,3);
		head = delete(head,6);
		recur(head);
	}

	public static class Node {
		int value;
		Node next;
		public Node() {}
		public Node(int data) {
			this.value = data;
		}
	}

	public static void recur(Node head) {
		Node cur = head;
		while(cur!=null){
			System.out.println(cur.value);
			cur = cur.next;
		}
	}
	public static Node insert(Node head,int index , int key) {
		if(index==0) {
			Node root = new Node(key);
			root.next = head;
			return root;
		}
		Node cur = head;
		while(--index!=0){
			cur = cur.next;
		}
		Node tem = cur.next;
		cur.next = new Node(key);
		cur.next.next = tem;
		return head;
	}


	public static Node delete(Node head,int val) {
		Node DHead =new Node();//虚拟头结点
		DHead.next = head;
		Node cur= DHead;

		while(cur.next != null){
			if(cur.next.value==val) cur.next=cur.next.next;
			else cur=cur.next;
		}
		return DHead.next;
	}

	public static Node reverseList(Node head) {
		Node newHead =null;
		Node next;
		while (head!=null){
			next=head.next;
			head.next=newHead;
			newHead=head;
			head=next;
		}
		return newHead;
	}


	public static class DoubleNode {
		public int value;
		// 多一个前指向
		public DoubleNode pre;
		public DoubleNode next;

		public DoubleNode(int data) {
			this.value = data;
		}
	}

	public static DoubleNode reverseList(DoubleNode head) {
		DoubleNode newHead = null;
		DoubleNode next;
		while (head != null) {
			next = head.next;
			head.next = newHead;
			// 前一个指向为当前节点的下一个节点
			head.pre = next;
			newHead = head;
			head = next;
		}
		return newHead;
	}

	public static void printLinkedList(Node head) {
		System.out.print("Linked List: ");
		while (head != null) {
			System.out.print(head.value + " ");
			head = head.next;
		}
		System.out.println();
	}

	public static void printDoubleLinkedList(DoubleNode head) {
		System.out.print("Double Linked List: ");
		DoubleNode end = null;
		while (head != null) {
			System.out.print(head.value + " ");
			end = head;
			head = head.next;
		}
		System.out.print("| ");
		while (end != null) {
			System.out.print(end.value + " ");
			end = end.pre;
		}
		System.out.println();
	}


}
