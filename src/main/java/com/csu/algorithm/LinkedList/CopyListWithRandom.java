
import java.util.HashMap;

public class CopyListWithRandom {

	public static class Node {
		public int value;
		public Node next;
		public Node rand;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node copyListWithRand1(Node head) {
		// key 老节点 ；value 新节点
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		// cur从表头开始遍历，为了不改变原表结构
		Node cur = head;
		// 新老节点一一对应存到map中
		while (cur != null) {
			map.put(cur, new Node(cur.value));
			cur = cur.next;
		}
		// 按顺序依次把新节点的next和rand指针赋值为旧节点的指针
		cur = head;
		while (cur != null) {
			// 新.next ->  cur.next克隆节点找到
			map.get(cur).next = map.get(cur.next);
			map.get(cur).rand = map.get(cur.rand);
			cur = cur.next;
		}
		// 返回新表的头节点
		return map.get(head);
	}

	public static Node copyListWithRand2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		Node next = null;
		// 把每个节点的clone节点插入到节点后面
		// 1 -> 1' -> 2
		while (cur != null) {
			// cur 老 ；next 老的下一个
			next = cur.next;
			cur.next = new Node(cur.value);
			cur.next.next = next;
			cur = next;
		}
		// 按顺序依次把新节点的rand指针赋值为老节点的rand指针
		cur = head;
		Node curCopy = null;
		// 1 -> 1' -> 2 -> 2'
		while (cur != null) {
			// 老节点的下一个节点，跳两下
			next = cur.next.next;
			curCopy = cur.next;
			// 老rand为空，新为空；
			curCopy.rand = cur.rand != null ? cur.rand.next : null;
			cur = next;
		}
		// 新链表的头结点
		Node res = head.next;
		cur = head;
		// 按顺序分离，即给next重新赋值
		while (cur != null) {
			// 记录老节点下一个
			next = cur.next.next;
			// 新节点
			curCopy = cur.next;
			// 还原老节点的next指针
			cur.next = next;
			// 赋值新节点的next指针
			curCopy.next = next != null ? next.next : null;
			cur = next;
		}
		// 返回新链表头节点
		return res;
	}

	public static void printRandLinkedList(Node head) {
		Node cur = head;
		System.out.print("order: ");
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.next;
		}
		System.out.println();
		cur = head;
		System.out.print("rand:  ");
		while (cur != null) {
			System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
			cur = cur.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = null;
		Node res1 = null;
		Node res2 = null;
		printRandLinkedList(head);
		res1 = copyListWithRand1(head);
		printRandLinkedList(res1);
		res2 = copyListWithRand2(head);
		printRandLinkedList(res2);
		printRandLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(4);
		head.next.next.next.next = new Node(5);
		head.next.next.next.next.next = new Node(6);

		head.rand = head.next.next.next.next.next; // 1 -> 6
		head.next.rand = head.next.next.next.next.next; // 2 -> 6
		head.next.next.rand = head.next.next.next.next; // 3 -> 5
		head.next.next.next.rand = head.next.next; // 4 -> 3
		head.next.next.next.next.rand = null; // 5 -> null
		head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

		System.out.println("原始链表：");
		printRandLinkedList(head);
		System.out.println("=========================");
		res1 = copyListWithRand1(head);
		System.out.println("方法一的拷贝链表：");
		printRandLinkedList(res1);
		System.out.println("=========================");
		res2 = copyListWithRand2(head);
		System.out.println("方法二的拷贝链表：");
		printRandLinkedList(res2);
		System.out.println("=========================");
		System.out.println("经历方法二拷贝之后的原始链表：");
		printRandLinkedList(head);
		System.out.println("=========================");

	}

}
