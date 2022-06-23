package class_03;

public class RotateMatrix {

	public static void rotate(int[][] matrix) {
		// 左上右下两个点
		int tR = 0;
		int tC = 0;
		int dR = matrix.length - 1;
		int dC = matrix[0].length - 1;
		// 此时是正方形，等于的话也不用旋转
		while (tR < dR) {
			rotateEdge(matrix, tR++, tC++, dR--, dC--);
		}
	}

	public static void rotateEdge(int[][] m, int tR, int tC, int dR, int dC) {
		// 需要交换的数字的组数，边长-1
		int times = dC - tC;
		// 循环分组，对每个分组中的各个边中的数进行交换
		for (int i = 0; i != times; i++) {
			// 整体是一个逆时针交换
			int tmp = m[tR][tC + i];//记录左上点，列++
			m[tR][tC + i] = m[dR - i][tC];//左下点，行--
			m[dR - i][tC] = m[dR][dC - i];//右下点，列--
			m[dR][dC - i] = m[tR + i][dC];//右上点，行++
			m[tR + i][dC] = tmp;
		}
	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
				{ 13, 14, 15, 16 } };
		printMatrix(matrix);
		rotate(matrix);
		System.out.println("=========");
		printMatrix(matrix);

	}

}
