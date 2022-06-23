package class17;

import java.util.ArrayList;
import java.util.List;

public class PrintAllPermutations {

	public static List<String> permutation1(String s) {
		// 放全排列的列表
		List<String> ans = new ArrayList<>();
		// 过滤空和null
		if (s == null || s.length() == 0) {
			return ans;
		}
		// 转数组
		char[] str = s.toCharArray();
		//
		ArrayList<Character> rest = new ArrayList<Character>();
		for (char cha : str) {
			rest.add(cha);
		}
		String path = "";
		f(rest, path, ans);
		return ans;
	}

	public static void f(ArrayList<Character> rest, String path, List<String> ans) {
		if (rest.isEmpty()) {
			ans.add(path);
		} else {
			int N = rest.size();
			for (int i = 0; i < N; i++) {
				char cur = rest.get(i);
				rest.remove(i);
				f(rest, path + cur, ans);
				rest.add(i, cur);
			}
		}
	}

	public static List<String> permutation2(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g1(str, 0, ans);
		return ans;
	}
	// index 递归指针
	public static void g1(char[] str, int index, List<String> ans) {
		// 不用return，此时下面的for循环不会执行
		if (index == str.length) {
			ans.add(String.valueOf(str));
		}
		for (int i = index; i < str.length; i++) {
			// 交换，递归
			swap(str, index, i);
			g1(str, index + 1, ans);
			// 还原，递归
			swap(str, index, i);
		}
	}

	public static List<String> permutation3(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g2(str, 0, ans);
		return ans;
	}

	public static void g2(char[] str, int index, List<String> ans) {
	if (index == str.length) {
		ans.add(String.valueOf(str));
	}
	// 扩展ascii码用8位表示256个字符
	// 此处充当hashSet
	boolean[] visited = new boolean[256];
	for (int i = index; i < str.length; i++) {
		// 判断字符是否出现过，出现过直接跳过，没出现过进入语句并标为true
		if (!visited[str[i]]) {
			visited[str[i]] = true;
			swap(str, index, i);
			g2(str, index + 1, ans);
			swap(str, index, i);
		}
	}
}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}

	public static void main(String[] args) {
		String s = "acc";
		List<String> ans1 = permutation1(s);
		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans2 = permutation2(s);
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans3 = permutation3(s);
		for (String str : ans3) {
			System.out.println(str);
		}

	}

}
