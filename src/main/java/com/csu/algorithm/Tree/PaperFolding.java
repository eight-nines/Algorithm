package class_04;

public class PaperFolding {

	public static void printAllFolds(int N) {
		printProcess(1, N, true);
	}
	// 递归函数，i是当前层数，也是当前折纸次数
	// N 是总层数，也是总折纸次数
	// down 为true时为 凹
	public static void printProcess(int i, int N, boolean down) {
		// 递归终止条件
		if (i > N) {
			return;
		}
		// 中序遍历打印
		// 左子树的根节点一定是凹
		// 右子树的根节点一定是凸
		printProcess(i + 1, N, true);
		System.out.println(down ? "down " : "up ");
		printProcess(i + 1, N, false);
	}

	public static void main(String[] args) {
		int N = 4;
		printAllFolds(N);
	}
}
