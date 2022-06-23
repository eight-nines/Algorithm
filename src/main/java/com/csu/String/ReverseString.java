package com.csu.String;

import java.util.Stack;

public class ReverseString {
    //方法一：分治
    /*
     * 【时间复杂度：】渐渐跟下去就是n,n/2,n/4,....n/2^k，其中k就是循环的次数
                    由于你n/2^k取整后>=1
                    即令n/2^k=1
                    可得k=log2n,（是以2为底，n的对数）
                    所以时间复杂度可以表示O()=O(logn)
                    * 不应该是O(nlogn)吗？
     */
    //存疑：时间复杂度是O(logn)，还是O(nlogn)
    public static String reverse1(String str){

        int len = str.length();
        if(len<=1) return str;
        int mid = len>>1;

        String left = reverse1(str.substring(0,mid));
        String right = reverse1(str.substring(mid,len));

        return right + left;
    }

    //方法二：字符串拼接
    /*
     * 【时间复杂度：】O()=O(n)
     */
    public static String reverse2(String s) {
        int length = s.length();
        String reverse = "";
        for (int i = 0; i < length; i++){
            reverse = s.charAt(i) + reverse;
        }
        return reverse;
    }

    //方法三：字符串拼接同2,
    /*
     * 【时间复杂度：】O()=O(n) 空间复杂度O(n)
     */
    public static String reverse3(String s) {
        char[] array = s.toCharArray();
        String reverse = "";
        for (int i = array.length - 1; i >= 0; i--)
            reverse += array[i];

        return reverse;
    }

    //方法四：StringBuilder(s).reverse()
    /*
     * 【时间复杂度：】O()=O(n)
     */
    public static String reverse4(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    //方法五：二分交换
    /*
     * 【时间复杂度：】O()=O(n)
     */
    public static String reverse5(String orig) {
        char[] s = orig.toCharArray();
        int n = s.length - 1;
        int halfLength = n / 2;
        for (int i = 0; i <= halfLength; i++) {
            char temp = s[i];
            s[i] = s[n - i];
            s[n - i] = temp;
        }
        return new String(s);
    }

    //方法六：异或
    /*
     * 【时间复杂度：】O()=O(n)
     */
    public static String reverse6(String s) {

        char[] str = s.toCharArray();

        int begin = 0;
        int end = s.length() - 1;

        while (begin < end) {
            str[begin] = (char) (str[begin] ^ str[end]);
            str[end] = (char) (str[begin] ^ str[end]);
            str[begin] = (char) (str[end] ^ str[begin]);
            begin++;
            end--;
        }

        return new String(str);
    }

    //方法七：辅助空间栈
    /*
     * 【时间复杂度：】O()=O(n)
     */
    public static String reverse7(String s) {
        char[] str = s.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length; i++)
            stack.push(str[i]);

        String reversed = "";
        for (int i = 0; i < str.length; i++)
            reversed += stack.pop();

        return reversed;
    }

    public static void main(String[] args) {
        System.out.println(reverse2("abcdefg"));
    }
}