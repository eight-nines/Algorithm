package com.csu.makeCharts;

import java.util.HashSet;

public class MakeChartsAll {


    static HashSet<Integer> set = new HashSet<>();

    static {
        int i = 1;
        set.add(i);
        while (i <= Integer.MAX_VALUE / 3) {
            i *= 3;
            set.add(i);
        }
    }

    public boolean isPowerOfThree1(int n) {
        return set.contains(n);
    }

    //常规解法
    public boolean isPowerOfThree(int n) {

        if (n <= 0) return false;
        while (n % 3 == 0) n /= 3;
        return n == 1;
    }


}
