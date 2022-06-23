package 链表;

public class _206_反转链表 {
	
	public ListNode reverseList(ListNode head) {
		//排除节点数量<2
		if (head == null || head.next == null) return head;
		//递归，直到单个节点返回自身，两个节点翻转指向，三个节点将两个节点看成一个整体...
		ListNode newHead = reverseList(head.next);
		//右边节点由指向null指向左边
		head.next.next = head;
		//左边节点由指向右边指向null
		head.next = null;
		return newHead;
	}

// 翻转单向链表，参数为头节点,返回头节点
public ListNode reverseList2(ListNode head) {
	//排除节点数量<2
	if (head == null || head.next == null) return head;

	// 三个引用交换两个节点
	// head 当前节点；newHead 新的头节点；next 当前节点的后节点
	ListNode newHead = null;
	ListNode next;
	while (head != null) {
		// 记录下个节点地址
		next = head.next;
		// 当前节点指向新的头节点（即放到新的头结点左边）
		head.next = newHead;
		//让当前节点成为新的头节点（挪动新头节点指针）
		newHead = head;
		//让下一个节点成为当前节点（挪动当前节点指针）
		head = next;
	}

	return newHead;
}


}
