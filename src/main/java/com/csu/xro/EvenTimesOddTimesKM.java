package com.csu.xro;

import java.util.HashMap;
import java.util.HashSet;

public class EvenTimesOddTimesKM {

	//创建一个map,每一个key是32个位上唯一位上是1的32位数
	public static void mapCreate(HashMap<Integer, Integer> map) {
		int value = 1;
		for (int i = 0; i < 32; i++) {
			map.put(value, i);
			value <<= 1;
		}
	}

	//优化，一共也就用了32个key，空间复杂度可以接受
    public static HashMap<Integer, Integer> map = new HashMap<>();

	// 请保证arr中，只有一种数出现了K次，其他数都出现了M次
    public static int onlyKTimes(int[] arr, int k, int m) {
        if (map.size() == 0) {
            mapCreate(map);
        }
        // int32位，使用一个32长的数组存放所有位处1的和
        int[] t = new int[32];
        // t[0] 0位置的1出现了几个
        // t[i] i位置的1出现了几个
        for (int num : arr) {
        	//优化后只需要遍历有1的位
            while (num != 0) {
                int rightOne = num & (-num);//只有最右侧的1对应的32位数
                t[map.get(rightOne)]++;
                num ^= rightOne; //消去num中最右1：rightOne是当前num最右侧1对应的32位数
            }
            //优化前每次都要遍历所有位
//            for (int i = 0; i < 32; i++) {
//                t[i] += (num >> i) & 1; //此位为1则加1，否则加0
//            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0) { //说明k次数在此位有1
                if (t[i] % m == k) {
                    ans |= (1 << i); //将1填在k次数对应的位上
                } else {
                    return -1; //有余数说明不符合规则
                }
            }
        }
        if (ans == 0) { // 说明这个k次数要么为0，要么就是不存在这个数
            int count = 0;
            for (int num : arr) { //统计数组中0出现的次数
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) { //如果k次数不为0，说明不存在所谓的k次数
                return -1;
            }
        }
        return ans;
    }

    //对数器的测试方法
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }

    //获取随机数组
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 真命天子出现的次数
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    // 数字的范围
    // [-range, +range]
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    public static void main(String[] args) {
        int kinds = 5;//数的种类
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }
}
