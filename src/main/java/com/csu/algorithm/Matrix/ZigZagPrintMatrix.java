package class_03;

import java.util.Arrays;

public class ZigZagPrintMatrix {

public static void printMatrixZigZag(int[][] matrix) {
	int tR = 0;
	int tC = 0;
	int dR = 0;
	int dC = 0;
	// 终点坐标
	int endR = matrix.length - 1;
	int endC = matrix[0].length - 1;
	// 打印的方向
	boolean fromUp = true;
	// 打印完最后一个点结束循环
	while (tR != endR + 1) {
		printLevel(matrix, tR, tC, dR, dC, fromUp);
		// 上点：列走完，向下走
		tR = tC == endC ? tR + 1 : tR;
		tC = tC == endC ? tC : tC + 1;
		// 下点:行走完，向右走
		dC = dR == endR ? dC + 1 : dC;
		dR = dR == endR ? dR : dR + 1;
		// 变换打印方向
		fromUp = !fromUp;
	}
}

public static void printLevel(int[][] m, int tR, int tC, int dR, int dC, boolean f) {
	if (f) {
		while (tR != dR + 1) {
			System.out.print(m[tR++][tC--] + " ");
		}
		System.out.println();
	} else {
		while (dR != tR - 1) {
			System.out.print(m[dR--][dC++] + " ");
		}
		System.out.println();
	}
}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		for (int[] i:matrix
			 ) {
			System.out.println(Arrays.toString(i));
		}
		printMatrixZigZag(matrix);

	}

}
