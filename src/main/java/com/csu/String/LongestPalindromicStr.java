package com.csu.String;

import java.util.*;

public class LongestPalindromicStr {


    public static void main(String[] args) {

//        System.out.println(validPalindrome("eeccccbebaeeabebccceea"));

        for (int i = 0; i < 7; i++) {
            System.out.println(i+=2);
        }

    }

    //双重遍历+动态规划优化：57.6%
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;

        char[] ch = s.toCharArray();
        int len = ch.length;
        int maxStart = 0;  //最长回文串的起点
        int maxLen = 1;  //最长回文串的长度
        //dp[i][j]表示i起点，j终点为回文子串
        boolean[][] dp = new boolean[len][len];

        for (int r = 1; r < len; r++) {
            for (int l = 0; l < r; l++) {
                //两个值相等，且对于中间部分：只有一个字符或没有字符，或已是回文串了
                if (ch[l] == ch[r] && (r - l <= 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (r - l + 1 > maxLen) {
                        maxLen = r - l + 1;
                        maxStart = l;
                    }
                }
            }
        }
        return new String(ch,maxStart, maxLen);
    }

    //单次遍历+中心发散：99.59%-3ms
    //一个回文字符串只有1个或多个单相同字符的中心
    public String longestPalindrome1(String s) {
        if (s == null || s.length() < 2) return s;
        char[] ch = s.toCharArray();
        int len = ch.length,start = 0,end = 0;

        //遍历每一个位置，并以此为中心向两边扩散
        for(int i = 0;i<len || (len-i)>=((end-start)>>1);i++){
            //以当前位置为中心向两边发散寻找回文子串
            int left = i,right = i;
            //向右扩散，且与当前位置字符相同
            while(right < len-1 && ch[right+1] == ch[i]){
                right++;
            }
            //i直接跳到当前右指针，因为当前i-right之间都是重复字符
            //重复字母为中心时得到的回文串长度相等，故可以直接跳过
            i = right;
            //前两次扩散找到了以当前位置为中心的所有相同字符，且左右指针为回文串下标
            //合并，并同时向外扩散
            while(left > 0 && right < len-1 && ch[left-1] == ch[right+1]){
                left--;right++;
            }
            if(right - left > end-start){
                start = left;end = right;
            }
        }
        return s.substring(start, end + 1);
    }

    //递归+中心发散：100%-1ms
    class Solution {
        int start = 0;
        int end = 0;
        int length = 0;
        char[] cs = null;

        public String longestPalindrome(String s) {
            if (s==null||s.length()<2) return s;

            cs = s.toCharArray();
            length = cs.length;
            fill(0);
            return s.substring(start, end + 1);
        }

        private void fill(int i){
            //长度-当前中心索引
            if (i == length-1 || 2* (length-i)+1<end-start) {
                return;
            }
            int cur_end = i;
            int cur_start = i;
            //向右扩展，只找了右边的重复值
            while (cur_end < length - 1 && cs[cur_end] == cs[cur_end + 1]) {
                cur_end++;
            }
            //跳过重复值
            i = cur_end;
            //合并
            while (cur_start > 0 && cur_end < length -1  && cs[cur_start - 1] == cs[cur_end + 1]) {
                cur_start--;
                cur_end++;
            }
            //更新最大长度
            if (cur_end - cur_start > end - start) {
                end = cur_end;start = cur_start;
            }
            fill(i+1);
        }
    }


}
