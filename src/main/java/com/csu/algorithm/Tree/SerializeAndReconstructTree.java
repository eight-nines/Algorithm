package class_04;

import java.util.LinkedList;
import java.util.Queue;

public class SerializeAndReconstructTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static String serialByPre(Node head) {
		if (head == null) {
			return "#_";
		}
		String res = head.value + "_";
		res += serialByPre(head.left);
		res += serialByPre(head.right);
		return res;
	}

	// 以下划线为分隔符，生成节点数组，并存到队列中
	// 调用反序列化函数，还原树
	public static Node reconByPreString(String preStr) {
		String[] values = preStr.split("_");
		// 把节点存到队列中
		Queue<String> queue = new LinkedList<String>();
		for (String i:values) {
			queue.offer(i);
		}
		return reconPreOrder(queue);
	}

	public static Node reconPreOrder(Queue<String> queue) {
	String value = queue.poll();
	if (value.equals("#")) {
		// 返回空，不创建节点
		return null;
	}
	Node head = new Node(Integer.valueOf(value));
	head.left = reconPreOrder(queue);
	head.right = reconPreOrder(queue);
	return head;
}

	// 按层序列化成字符串，非迭代写法
	public static String serialByLevel(Node head) {
			// 空树
			if (head == null) {
				return "#_";
			}
			// 头节点
			String res = head.value + "_";
			// 队列,存储非空节点
			Queue<Node> queue = new LinkedList<Node>();
			queue.offer(head);
			while (!queue.isEmpty()) {
				// 弹出先进节点，开始为根节点
				head = queue.poll();
				if (head.left != null) {
					// 有左子树，把字符串连上，加入队列
					res += head.left.value + "_";
					queue.offer(head.left);
				} else {
					// 无左子树，加上null
					res += "#_";
				}
				if (head.right != null) {
					res += head.right.value + "_";
					queue.offer(head.right);
				} else {
					res += "#_";
				}
			}
			return res;
		}

	public static Node reconByLevelString(String levelStr) {
		// 分割成字符串数组，按序每个字符串的后两个字符串是自己的左右子节点
		// 字符串数组中是所有的节点值或"#"
		String[] values = levelStr.split("_");
		// 字符串数组指针,记录全局位置
		int index = 0;
		// 生成根节点，即第一层
		Node head = generateNodeByString(values[index++]);
		// 存放不为空的节点的队列，队列中只存放不为空的节点
		// 在迭代中，每个节点的左右子节点通过字符串数组来获得
		// 同时，如果左右子节点不为空，从左到右依次加入队列等待处理
		Queue<Node> queue = new LinkedList<Node>();
		// 过滤根节点为空的情况
		if (head != null) {
			queue.offer(head);
		}
		Node node = null;
		while (!queue.isEmpty()) {
			// 按序每个节点的后两个是左右子节点
			node = queue.poll();
			node.left = generateNodeByString(values[index++]);
			node.right = generateNodeByString(values[index++]);
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
		return head;
	}

	// 根据字符串内容，生成节点，"#"直接返回null
	public static Node generateNodeByString(String val) {
		if (val.equals("#")) {
			return null;
		}
		return new Node(Integer.valueOf(val));
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
		printInOrder(head.right, height + 1, "右", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "左", len);
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
		Node head = null;
		printTree(head);

		String pre = serialByPre(head);
		System.out.println("serialize tree by pre-order: " + pre);
		head = reconByPreString(pre);
		System.out.print("reconstruct tree by pre-order, ");
		printTree(head);

		String level = serialByLevel(head);
		System.out.println("serialize tree by level: " + level);
		head = reconByLevelString(level);
		System.out.print("reconstruct tree by level, ");
		printTree(head);

		System.out.println("====================================");

		head = new Node(1);
		printTree(head);

		pre = serialByPre(head);
		System.out.println("serialize tree by pre-order: " + pre);
		head = reconByPreString(pre);
		System.out.print("reconstruct tree by pre-order, ");
		printTree(head);

		level = serialByLevel(head);
		System.out.println("serialize tree by level: " + level);
		head = reconByLevelString(level);
		System.out.print("reconstruct tree by level, ");
		printTree(head);

		System.out.println("====================================");

		head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.right.right = new Node(5);
		printTree(head);

		pre = serialByPre(head);
		System.out.println("serialize tree by pre-order: " + pre);
		head = reconByPreString(pre);
		System.out.print("reconstruct tree by pre-order, ");
		printTree(head);

		level = serialByLevel(head);
		System.out.println("serialize tree by level: " + level);
		head = reconByLevelString(level);
		System.out.print("reconstruct tree by level, ");
		printTree(head);

		System.out.println("====================================");

		head = new Node(100);
		head.left = new Node(21);
		head.left.left = new Node(37);
		head.right = new Node(-42);
		head.right.left = new Node(0);
		head.right.right = new Node(666);
		printTree(head);

		pre = serialByPre(head);
		System.out.println("serialize tree by pre-order: " + pre);
		head = reconByPreString(pre);
		System.out.print("reconstruct tree by pre-order, ");
		printTree(head);

		level = serialByLevel(head);
		System.out.println("serialize tree by level: " + level);
		head = reconByLevelString(level);
		System.out.print("reconstruct tree by level, ");
		printTree(head);

		System.out.println("====================================");

	}
}
