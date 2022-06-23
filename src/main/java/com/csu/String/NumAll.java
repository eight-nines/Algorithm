package com.csu.String;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NumAll {








    public static int reverse(int x) {
        // %10得个位，/10退1位，采用long存值，通过转型int判断溢出
        long res = 0;

        while (x != 0) {
            res = res * 10 + x % 10;//x小于0时，%10返回负个位数
            x /= 10;
        }
        return (int) res == res ? (int) res : 0;
    }

    public List<Integer> grayCode(int n) {
        //格雷编码：G(i) = i ^ (i/2);
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = 0; i < (1 << n); i++) {//i<2^n
            res.add(i ^ (i >> 1));//i ^ (i/2);
        }
        return res;
    }

    public List<Integer> grayCode1(int n) {
        //格雷编码：由n-1的res推导 正序前置0+逆序前置1
        //0:0 1:0,1 2: 00,01,11,10 3:000,001,011,010,110,111,101,100
        ArrayList<Integer> res = new ArrayList<>(1 << n);
        res.add(0);//n=0作为推导n的起始值 0
        int head = 1;//前置1初始在第低一位 0 1
        for (int i = 0; i < n; i++) {
            for (int j = res.size() - 1; j >= 0; j--) {
                res.add(head | res.get(i));//逆序前置1
            }
            head <<= 1;//向高位进1位
        }
        return res;
    }

}
