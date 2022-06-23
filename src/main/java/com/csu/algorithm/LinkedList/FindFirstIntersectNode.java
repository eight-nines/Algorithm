
public class FindFirstIntersectNode {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getIntersectNode(Node head1, Node head2) {
		// 过滤为空的情况
		if (head1 == null || head2 == null) {
			return null;
		}
		// 返回入环点
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		if (loop1 == null && loop2 == null) {
			return noLoop(head1, head2);
		}
		if (loop1 != null && loop2 != null) {
			return bothLoop(head1, loop1, head2, loop2);
		}
		return null;
	}

	// 找到链表第一个入环节点，如果无环，返回null
	public static Node getLoopNode(Node head) {
		// 过滤：空，1，2个节点，都无环
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		// 先跳一下
		Node slow = head.next;
		Node fast = head.next.next;
		// fast第二次入环相遇结束循环
		while (slow != fast) {
			// 只有三或四个节点，且第三个第四个节点的next指向null，无环
			if (fast.next == null || fast.next.next == null) {
				return null;
			}
			fast = fast.next.next;
			slow = slow.next;
		}
		// 出循环，即相遇
		// fast指针从头开始走，每次走一步
		fast = head;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		// 再次相遇，相遇节点为入环点
		return slow;
	}

	// 如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
	public static Node noLoop(Node head1, Node head2) {
		// 过滤空链表
		if (head1 == null || head2 == null) {
			return null;
		}
		// 从头开始遍历两个链表
		Node cur1 = head1;
		Node cur2 = head2;
		// n先++为链表1的长度，再--链表2的长度
		// 则最后为正，链表1长；为负，链表2长
		int n = 0;
		while (cur1.next != null) {
			n++;
			// 最终为链表1的最后节点
			cur1 = cur1.next;
		}
		while (cur2.next != null) {
			n--;
			cur2 = cur2.next;
		}
		// 对比链表最后节点是否相同
		if (cur1 != cur2) {
			return null;
		}
		// n：链表1长度减去链表2长度的值
		// 谁长，谁的头变成cur1
		cur1 = n > 0 ? head1 : head2;
		cur2 = cur1 == head1 ? head2 : head1;
		// 对n求绝对值，则长链表从n开始遍历对比
		n = Math.abs(n);
		while (n != 0) {
			n--;
			cur1 = cur1.next;
		}
		// 按序对比
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	// 两个有环链表，返回第一个相交节点，如果不想交返回null
	public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		// 如果入环结点相同，变成noLoop问题
		if (loop1 == loop2) {
			cur1 = head1;
			cur2 = head2;
			int n = 0;
			while (cur1 != loop1) {
				n++;
				cur1 = cur1.next;
			}
			while (cur2 != loop2) {
				n--;
				cur2 = cur2.next;
			}
			cur1 = n > 0 ? head1 : head2;
			cur2 = cur1 == head1 ? head2 : head1;
			n = Math.abs(n);
			while (n != 0) {
				n--;
				cur1 = cur1.next;
			}
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		}
		// loop1 != loop2
		// 对链表1从loop1开始循环，逐个与loop2对比
		// 遇到这相交返回loop1,遇不到这不相交返回null
		else {
			cur1 = loop1.next;
			while (cur1 != loop1) {
				if (cur1 == loop2) {
					return loop1;
				}
				cur1 = cur1.next;
			}
			return null;
		}
	}

	public static void main(String[] args) {
		// 1->2->3->4->5->6->7->null
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);

		// 0->9->8->6->7->null
		Node head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

		// 1->2->3->4->5->6->7->4...
		head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);
		head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

		// 0->9->8->2...
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next; // 8->2
		System.out.println(getIntersectNode(head1, head2).value);

		// 0->9->8->6->4->5->6..
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

	}

}
