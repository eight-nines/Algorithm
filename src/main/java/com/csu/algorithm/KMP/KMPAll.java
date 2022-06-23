package com.csu.algorithm.KMP;

public class KMPAll {


    class Solution {
        public int strStr(String haystack, String needle) {
            //KMP：next数组+遍历，每次不匹配从前缀后一个字符开始继续匹配
            //next数组既是当前元素前的前后缀匹配长度，也是下次回退的下标
            int len1 = haystack.length(), len2 = needle.length();
            if (len2 == 0) return 0;//2长度为0，返回0
            if (len1 < len2) return -1;//2长度不能大于1

            char[] c1 = haystack.toCharArray(), c2 = needle.toCharArray();
            int p1 = 0, p2 = 0;//遍历12的下标指针
            int[] next = getNext(c2);//获取next数组

            while (p1 < len1 && p2 < len2) {
                if (c1[p1] == c2[p2]) {//当前字符匹配，一起前进
                    p1++; p2++;
                } else if (next[p2] == -1) p1++;//不匹配且2下标为0,1前进
                else p2 = next[p2];//不匹配且2下标不为0，回退前缀后一个字符
            }
            return p2 == len2 ? p1 - p2 : -1;
        }

        private int[] getNext(char[] c2) {
            if (c2.length == 1) return new int[]{-1};//前两个元素固定为-1,0
            int[] next = new int[c2.length];
            next[0] = -1; next[1] = 0;
            int index = 2, pre = 0;//当前元素指针，上个元素要对比的前缀后一个下标

            while (index < c2.length) {
                if (c2[index - 1] == c2[pre]) next[index++] = ++pre;
                else if (pre > 0) pre = next[pre];//不匹配，但大于0，回退
                else next[index++] = 0;//无法回退，只能为0
            }
            return next;
        }
    }


    public boolean repeatedSubstringPattern(String s) {
        // KMP：记录0,len-1全字符串的next值，子串组成的字符串，next值是递增的
        // 且len-next[len]为重复字符串的长度
        //"aba" -1 0 0 1  "abab" -1 0 0 1 2 "abac" -1 0 0 1 0
        //"abc abc abc abc" -1 0 0  0 1 2  3 4 5  6 7 8 9 ->12-9=3 abc
        int len = s.length();
        char[] str = s.toCharArray();
        if (len < 2) return false;

        int[] next = new int[len + 1];//最后多出1位，记录全字符串最大前缀长
        next[0] = -1; next[1] = 0;//固定值
        int i = 2, pre = 0;
        while (i < len + 1) {//同KMP
            if (str[i - 1] == str[pre]) next[i++] = ++pre;
            else if (pre == 0) next[i++] = 0;
            else pre = next[pre];
        }
        //条件1：全字符串的最大前缀长不能为0，不然说明不匹配
        //条件2：子串组成的s,next值是递增的，len-next[len]为重复字符串的长度
        return next[len] > 0 && len % (len - next[len]) == 0;
    }













































}
