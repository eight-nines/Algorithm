package com.csu.dataStructure.bloomFilter;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		BloomFilter<Integer> bf = new BloomFilter<>(1_00_0000, 0.01);
//		for (int i = 1; i <= 1_00_0000; i++) {
//			bf.put(i);
//		}
//		
//		int count = 0;
//		for (int i = 1_00_0001; i <= 2_00_0000; i++) {
//			if (bf.contains(i)) {
//				count++;
//			}
//		}
//		System.out.println(count);
		
		// 存放url的数组
		String[] urls = {};
		BloomFilter<String> bf = new BloomFilter<String>(10_0000_0000, 0.01);

		//有概率误爬，但不会重复爬
		for (String url : urls) {
			//如果之前就包含这个url生成的所有hash位（存在误差），直接跳过
			//否则把url存到bf中，并爬url
			if (!bf.put(url)) continue;
			// 爬这个url
			// ......
		}
	}

}
