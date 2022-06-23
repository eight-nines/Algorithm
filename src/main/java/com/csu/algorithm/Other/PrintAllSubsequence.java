package class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrintAllSubsequence {

	public static List<String> subs(String s) {
		// 字符串变为字符数组
		char[] str = s.toCharArray();
		// 初始子序列，为空字符串
		String path = "";
		// 存放子序列的数组列表
		List<String> ans = new ArrayList<>();
		// 进入暴力递归求解
		process1(str, 0, ans, path);
		return ans;
	}

	// index：当前指针位置
	public static void process1(char[] str, int index, List<String> ans, String path) {
	// 递归到当前路径的尽头节点，把此路径添加到ans中
	if (index == str.length) {
		ans.add(path);
		return;
	}
	// 没有要index位置的字符，即不添加当前字符进path中
	process1(str, index + 1, ans, path);
	// 要了index位置的字符，添加当前字符进path中
	process1(str, index + 1, ans, path + String.valueOf(str[index]));
}

	// 无重复版本
	public static List<String> subsNoRepeat(String s) {
		char[] str = s.toCharArray();
		String path = "";
		HashSet<String> set = new HashSet<>();
		process2(str, 0, set, path);
		List<String> ans = new ArrayList<>();
		for (String cur : set) {
			ans.add(cur);
		}
		return ans;
	}

	public static void process2(char[] str, int index, HashSet<String> set, String path) {
		if (index == str.length) {
			set.add(path);
			return;
		}
		String no = path;
		process2(str, index + 1, set, no);
		String yes = path + String.valueOf(str[index]);
		process2(str, index + 1, set, yes);
	}

	public static void main(String[] args) {
		String test = "acc";
		List<String> ans1 = subs(test);
		List<String> ans2 = subsNoRepeat(test);

		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=================");
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=================");

	}

}
