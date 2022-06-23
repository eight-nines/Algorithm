package com.csu.recursion;


//汉诺塔
public class Hanoi {
	
	public static void main(String[] args) {
		new Hanoi().hanoi(3, "A", "B", "C");
	}
	
	/**
	 * 将 n 个碟子从 p1 挪动到 p3
	 * @param p2 中间的柱子
	 */
	void hanoi(int n, String p1, String p2, String p3) {
		if (n == 1) {//1个盘子，直接移动
			move(n, p1, p3);
			return;
		}
		hanoi(n - 1, p1, p3, p2);//将上面的盘子移动到中间
		move(n, p1, p3);//将目标移动到右侧（打印）
		hanoi(n - 1, p2, p1, p3);//将中间的盘子移动到右侧
	} 
	// T(n) = 2 * [2 * T(n - 2) + O(1)] + O(1)
	// T(n) = 2^2 * [2 * T(n - 3) + O(1)] + 2 * O(1) + O(1)
	// T(n) = 2^3 * T(n - 3) + (2^2 + 2^1 + 2^0) * O(1)
	// T(n) = 2^(n - 1) * O(1) + (2^(n-2) + ... + 2^2 + 2^1 + 2^0) * O(1)
	// T(n) = [2^(n - 1)+ 2^(n-2) + ... + 2^2 + 2^1 + 2^0] * O(1)
	// T(n) = (2^n - 1) * O(1)
	
	/**
	 * 将 no 号盘子从 from 移动到 to
	 * @param no
	 * @param from
	 * @param to
	 */
	void move(int no, String from, String to) {
		System.out.println("将" + no + "号盘子从" + from + "移动到" + to);
	}
	
}	
