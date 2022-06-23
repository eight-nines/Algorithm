package class_04;

public class CBTNodeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	
	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}
	// 迭代函数，其中l为当前节点深度，h为整个树的深度
	public static int bs(Node node, int l, int h) {
		// 如果只有根节点，返回1
		if (l == h) {
			return 1;
		}
		// 如果右子树的左边界最后一个点的深度 == 整个树的深度
		// 左子树为满二叉树，总节点数 = 2^n -1 加上根节点1 和 递归右子树的节点数量
		if (mostLeftLevel(node.right, l + 1) == h) {
			return (1 << (h - l)) + bs(node.right, l + 1, h);
		} else { // 右子树为深度h-l-1（比子树总深度小1） 的完全二叉树，对左侧进行递归求节点
			return (1 << (h - l - 1)) + bs(node.left, l + 1, h);
		}
	}
	// 基于某节点和节点所在深度，返回此节点为根的左边界最下位置节点的深度
	public static int mostLeftLevel(Node node, int level) {
	while (node != null) {
		level++;
		node = node.left;
	}
	// 因为无论是否有左节点，直接++，所以会多加一次
	return level -1;
}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));

	}

}
