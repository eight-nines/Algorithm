
public class SmallerEqualBigger {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// pivot 中轴，即对比数
	public static Node listPartition1(Node head, int pivot) {
		// 过滤空数组
		if (head == null) {
			return head;
		}
		Node cur = head;
		// 记录链表的长度，用于初始化数组
		int i = 0;
		while (cur != null) {
			i++;
			cur = cur.next;
		}
		// 初始化数组
		Node[] nodeArr = new Node[i];
		cur = head;
		// 倒进数组，i变量复用
		for (i = 0; i < nodeArr.length; i++) {
			nodeArr[i] = cur;
			cur = cur.next;
		}
		arrPartition(nodeArr, pivot);
		// 把数组改造成链表
		for (i = 1; i < nodeArr.length; i++) {
			nodeArr[i - 1].next = nodeArr[i];
		}
		// i = nodeArr.length, 使最后一个元素的指向为null
		nodeArr[i - 1].next = null;
		// 即头节点
		return nodeArr[0];
	}

	// 数组的partition
	public static void arrPartition(Node[] nodeArr, int pivot) {
		int small = -1;
		int big = nodeArr.length;
		int index = 0;
		while (index != big) {
			if (nodeArr[index].value < pivot) {
				swap(nodeArr, ++small, index++);
			} else if (nodeArr[index].value == pivot) {
				index++;
			} else {
				swap(nodeArr, --big, index);
			}
		}
	}

	public static void swap(Node[] nodeArr, int a, int b) {
		Node tmp = nodeArr[a];
		nodeArr[a] = nodeArr[b];
		nodeArr[b] = tmp;
	}

	// 6个指针实现
	public static Node listPartition2(Node head, int pivot) {
		Node sH = null; // small head
		Node sT = null; // small tail
		Node eH = null; // equal head
		Node eT = null; // equal tail
		Node mH = null; // big head
		Node mT = null; // big tail
		Node next = null; // save next node

		// 把所有节点拆到三个链表中
		while (head != null) {
			// 下一个节点，此时是第二个节点
			next = head.next;
			// 断开节点指向，此时把头节点的指向断开
			head.next = null;
			// 小于区域链表
			if (head.value < pivot) {
				// 即此时链表为空，首尾都指向传入节点
				if (sH == null) {
					sH = head;
					sT = head;
				} else {
					// 链表不为空，把节点连到尾节点后
					sT.next = head;
					// 并把尾节点更新为此节点
					sT = head;
				}// 等于区域链表
			} else if (head.value == pivot) {
				if (eH == null) {
					eH = head;
					eT = head;
				} else {
					eT.next = head;
					eT = head;
				} // 大于区域链表
			} else {
				if (mH == null) {
					mH = head;
					mT = head;
				} else {
					mT.next = head;
					mT = head;
				}
			}
			// 后挪
			head = next;
		}

		// 条件1
		// 小于区域的尾巴，连等于区域的头，等于区域的尾巴连大于区域的头
		if (sT != null) { // 如果有小于区域
			sT.next = eH;
			// 下一步，谁去连大于区域的头，谁就变成eT
			// 有等于区域，eT -> 等于区域的尾结点
			// 无等于区域，eT -> 小于区域的尾结点
			eT = eT == null ? sT : eT;
		}
		//条件2
		// 下一步，用eT 去接 大于区域的头
		// eT 尽量不为空的尾巴节点
		if (eT != null) { // 如果小于区域和等于区域，不是都没有
			eT.next = mH;
		}
		// 无小无等，12都不中，返回mH,即大于区域头
		// 无小有等，中2，返回eH,此时eT指向mH
		// 有小有等，中12，返回sH,此时sT指向eH,eT指向mH
		// 有小无等，中12(eT->sT)，返回sH,此时sT指向mH
		return sH != null ? sH : (eH != null ? eH : mH);
	}

	public static void printLinkedList(Node node) {
		System.out.print("Linked List: ");
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head1 = new Node(7);
		head1.next = new Node(9);
		head1.next.next = new Node(1);
		head1.next.next.next = new Node(8);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(2);
		head1.next.next.next.next.next.next = new Node(5);
		printLinkedList(head1);
		// head1 = listPartition1(head1, 4);
		head1 = listPartition2(head1, 5);
		printLinkedList(head1);

	}

}
