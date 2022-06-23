package com.csu.algorithm.KMP;

public class Code01_KMP {

	public static int getIndexOf(String s1, String s2) {
		int len1 = s1.length(), len2 = s2.length();
		if (len1 < 1 || len1 < len2) return -1;//s2长度要小于等于s1
		char[] str1 = s1.toCharArray(), str2 = s2.toCharArray();
		int p1 = 0, p2 = 0;//遍历 12的指针
		// O(M) m <= n
		int[] next = getNextArray(str2);//获取next数组
		// O(N)
		while (p1 < str1.length && p2 < str2.length) {
			if (str1[p1] == str2[p2]) {//相等，前进
				p1++;
				p2++;
			} else if (next[p2] == -1) { // p2下标为0还不匹配，p1前进
				p1++;
			} else { //当前不匹配，p1回到前缀字符串的下个位置再开始匹配
				p2 = next[p2];
			}
		}
		return p2 == str2.length ? p1 - p2 : -1;//s2匹配完返回匹配起始下标
	}

	public static int[] getNextArray(char[] str2) {
		//next数组既是当前元素前的前后缀匹配长度，也是下次回退的下标
		if (str2.length == 1) return new int[]{-1};
		int[] next = new int[str2.length];
		next[0] = -1;
		next[1] = 0;//前两个元素的默认值 -1,0
		int i = 2; // 目前在哪个位置上求next数组的值
		int cn = 0; // 当前是哪个位置的值再和i-1位置的字符比较
		while (i < next.length) {
			if (str2[i - 1] == str2[cn]) { // 配成功的时候
				next[i++] = ++cn;
			} else if (cn > 0) {//还不是第一个位置，继续回退
				cn = next[cn];
			} else {//是第一个位置了，还不匹配，就是0
				next[i++] = 0;
			}
		}
		return next;
	}

	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);
			if (getIndexOf(str, match) != str.indexOf(match)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
