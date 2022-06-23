package com.csu.String;

public class AddStrings {

    public static String addStrings(String num1, String num2) {
        int p1 = num1.length()-1;
        int p2 = num2.length()-1;
        int add = 0;

        StringBuilder ans = new StringBuilder();

        while (p1>=0 || p2>=0 || add>0) {

            int x = p1>=0?num1.charAt(p1--)-'0':0;
            int y = p2>=0?num2.charAt(p2--)-'0':0;
            int res = x+y+add;
            //13取3
            ans.append(res%10);
            //标志进位
            add = res/10;
        }
        return ans.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(addStrings("123","456"));
    }


}
