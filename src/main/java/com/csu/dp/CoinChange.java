package com.csu.dp;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CoinChange {


	public static void main(String[] args) {
		System.out.println(coins2(11, new int[] {1,5,2}));
		System.out.println(test(11, new int[] {1,5,2}));

	}

	//1,5,20,25,41
	//自己重现的测试
	public static int test(int amount,int[] coins) {
		if(amount == 0) return 0;
		if(amount < 0 || coins==null ||coins.length ==0) return -1;

		int[] dp = new int[amount + 1];
		for(int i = 1; i < dp.length; i++){
			int min = Integer.MAX_VALUE;

			for(int face:coins){
				//面值不能太大
				if(face>i) continue;
				//选取当前面值的下一步的值
				int tem = dp[i-face];
				//等于-1无解，大于min则没必要
				if(tem ==-1 || tem>=min)  continue;
				min = tem;
			}
			if(min==Integer.MAX_VALUE)  dp[i]=-1;
			else dp[i]=min+1;
		}
		return dp[amount];
	}



























	/**
	 * 通用版，自己传入硬币面值数组
	 */
	static int coins5(int n, int[] faces) {
		if (n < 1 || faces == null || faces.length == 0) return -1;
		//dp[0] = 0
		int[] dp = new int[n + 1];
		//从小到大求解，可以直接避免重复求解子问题的问题
		for (int i = 1; i <= n; i++) {
			//此处初始化为最大值，面值数组中不一定有1
			int min = Integer.MAX_VALUE;
			//循环数组
			for (int face : faces) {
				//此处数组可能是乱序的，所以不用break
				if (i < face) continue;
				//求使用当前面值的下一步的步数
				int v = dp[i - face];//如果目标值与面值相等，v=dp[0]=0
				if (v < 0 || v >= min) continue;
				min = v;
			}
			//设置子问题的解
			if (min == Integer.MAX_VALUE) {
				dp[i] = -1;
			} else {
				dp[i] = min + 1;
			}
		}
		return dp[n];
	}

	/**
	 * 打印每步的取值版
	 */
	static int coins4(int n) {
		if (n < 1) return -1;
		int[] dp = new int[n + 1];
		// faces[i]是凑够i分时最后那枚硬币的面值
		int[] faces = new int[dp.length];
		for (int i = 1; i <= n; i++) {
			int min = dp[i - 1];
			faces[i] = 1;

			if (i >= 5 && dp[i - 5] < min) {
				min = dp[i - 5];
				faces[i] = 5;
			}
			if (i >= 20 && dp[i - 20] < min) {
				min = dp[i - 20];
				faces[i] = 20;
			}
			if (i >= 25 && dp[i - 25] < min) {
				min = dp[i - 25];
				faces[i] = 25;
			}
			dp[i] = min + 1;
			print(faces, i);
		}
//		print(faces, n);
		return dp[n];
	}
	
	static void print(int[] faces, int n) {
		System.out.print("[" + n + "] = ");
		while (n > 0) {
			System.out.print(faces[n] + " ");
			n -= faces[n];
		}
		System.out.println();
	}
	
	/**
	 * 步骤3：递推（自底向上，从小到大）
	 */
	static int coins3(int n) {
		if (n < 1) return -1;
		int[] dp = new int[n + 1];
		//从小到大求解，可以直接避免重复求解子问题的问题
		for (int i = 1; i <= n; i++) {
			//因为最小面值为1，故最小值每次可以初始化为dp[i - 1]
			int min = dp[i - 1];
			if (i >= 5) min = Math.min(dp[i - 5], min);
			if (i >= 20) min = Math.min(dp[i - 20], min);
			if (i >= 25) min = Math.min(dp[i - 25], min);
			dp[i] = min + 1;
		}
		return dp[n];
	}
	
	/**
	 * 步骤2：记忆化搜索（自顶向下的调用）
	 */
//记忆化搜索（自顶向下的调用）
static int coins2(int n ,int[] faces) {
	if (n < 1) return -1;//表示不可找零
	int[] dp = new int[n + 1];//记忆子问题的解，避免重复求解
	//初始化dp数组，将可用的硬币面值对应的位置设为1
	for (int face : faces) {
		//硬币面值从小到大，如果当前面值已经大于目标值，后面的也没必要判断了
		if (n < face) continue;
		dp[face] = 1;
	}
	return coins2dp(n, dp,faces);
}

static int coins2dp(int n, int[] dp,int[] faces) {
	if (n < 1) return Integer.MAX_VALUE;
	//如果当前子问题还没有解：求解并记录；否则直接返回已有解
	if (dp[n] == 0) {
		int min = Integer.MAX_VALUE;
		for (int face:faces) {
			min = Math.min(coins1(n - face,faces),min);
		}
		dp[n] = min + 1;
	}
	return dp[n];
}
	
	/**
	 * 步骤1：暴力递归（自顶向下的调用，出现了重叠子问题）
	 * 对于41来说，15,21,1,36,31,27,22,17,40,39,38,37...存在大量重复
	 */
	static int coins1(int n, int[] faces) {
		//此处最小硬币为1，返回最大值用于下面求最小值时过滤
		if (n < 1) return Integer.MAX_VALUE;
		//边界条件，刚好返回一枚硬币
		if (Arrays.binarySearch(faces, n)>=0) return 1;
		//找到“这一步选取哪个硬币”的4种下一步中的最小值-->递归
		int min = Integer.MAX_VALUE;
		for (int face:faces) {
			min = Math.min(coins1(n - face,faces),min);
		}
		return min + 1;
	}
}




















