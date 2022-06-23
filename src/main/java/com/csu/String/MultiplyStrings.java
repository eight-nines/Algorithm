package com.csu.String;

public class MultiplyStrings {
    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";

        int len1 = num1.length();
        int len2 = num2.length();
        // 存放结果中对应位的值
        // m*n的结果可能是m+n位、m+n-1(首位为0)
        int[] nums = new int[len1 + len2];

        for (int i = len1 - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = len2 - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                // 计算[i+j+1]位的值
                int res = nums[i + j + 1] + x * y;
                // 取个位值：13取3
                nums[i + j + 1] = res % 10;
                // 取进位值并进位：13取1,9取0
                nums[i + j] += res / 10;
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            // 如果首位为0，跳过
            if (i == 0 && nums[0] == 0) continue;
            ans.append(nums[i]);
        }
        return ans.toString();
    }


    public static void main(String[] args) {
        System.out.println(multiply("100","200"));
    }
}