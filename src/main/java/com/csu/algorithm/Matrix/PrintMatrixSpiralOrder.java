package class_03;

public class PrintMatrixSpiralOrder {

	public static void spiralOrderPrint(int[][] matrix) {
		// 左上角点坐标，初始0,0
		int tR = 0;
		int tC = 0;
		// 右下角点坐标，初始[二维数组中数组个数，每个数组的长度]
		int dR = matrix.length - 1;
		int dC = matrix[0].length - 1;
		// 停止条件：左上角点的行或列序号大于右下角点
		while (tR <= dR && tC <= dC) {
			printEdge(matrix, tR++, tC++, dR--, dC--);
		}
	}

	//打印边界函数
	public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
		// 左行 = 右行：只有一条行边界：打印整列
		if (tR == dR) {
			for (int i = tC; i <= dC; i++) {
				System.out.print(m[tR][i] + " ");
			}// 只有一条列边界：打印整行
		} else if (tC == dC) {
			for (int i = tR; i <= dR; i++) {
				System.out.print(m[i][tC] + " ");
			}// 正常情况
		} else {
			int curC = tC;
			int curR = tR;
			// 打印上行，除右角点
			while (curC != dC) {
				System.out.print(m[tR][curC++] + " ");
			}
			// 打印右列，除下角点
			while (curR != dR) {
				System.out.print(m[curR++][dC] + " ");
			}
			// 打印下行，除左角点
			while (curC != tC) {
				System.out.print(m[dR][curC--] + " ");
			}
			// 打印右列，除上角点
			while (curR != tR) {
				System.out.print(m[curR--][tC] + " ");
			}
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
				{ 13, 14, 15, 16 } };
		spiralOrderPrint(matrix);

	}

}
