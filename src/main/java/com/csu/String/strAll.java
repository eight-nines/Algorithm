package com.csu.String;

import java.util.*;

public class strAll {


    public String convertToTitle(int columnNumber) {

        StringBuilder res = new StringBuilder();

        //0-25 对应A-Z 但题目中是1-26 对应A-Z，每次-1
        //A：0%26=0， 对于AA：26%26=0
        while (columnNumber > 0) {
            //第一次-1是为了将A对应的1调整成0
            //之后-1是为了终止循环，此时col代表的是剩下的字符个数
            columnNumber--;

            res.append((char) (columnNumber % 26) + 'A');

            //消去1位，26进制除26
            columnNumber /= 26;
        }

        return res.reverse().toString();
    }


    public static void main(String[] args) {
        System.out.println(25 % 26);
    }


    public boolean isAnagram(String s, String t) {

        //哈希表：数组存放对应字符数量，1加，2减，遍历数组看是否不为0
        char[] s1 = s.toCharArray(), s2 = t.toCharArray();
        int len1 = s1.length, len2 = s2.length;
        if (len1 != len2) return false;

        //存放对应的字符数量
        int[] arr = new int[26];

        for (int i = 0; i < len1; i++) arr[s1[i] - 'a']++;
        for (int i = 0; i < len2; i++) arr[s2[i] - 'a']--;
        //查看验证
        for (int ch : arr) if (ch != 0) return false;

        return true;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        //哈希表：遍历数组1加入set1，遍历数组2比较set1 加入set2
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> res = new HashSet<>();

        //遍历nums1加入set1
        for (int i : nums1) set.add(i);

        //遍历nums2对比set1 加入set2
        for (int i : nums2) {
            if (set.contains(i)) res.add(i);
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public boolean isHappy(int n) {
        //哈希表：如果不是快乐数，结果会重复出现;是的话，结果为1
        HashSet<Integer> set = new HashSet<>();

        while (!set.contains(n)) {
            if (n == 1) return true; //为1 是快乐数
            set.add(n);//加入该数
            char[] nums = String.valueOf(n).toCharArray();
            n = 0; //转字符数组，每个位置平方相加
            for (char ch : nums) n += (ch - '0') * (ch - '0');
        }
        return false;
    }


    public int[] twoSum(int[] nums, int target) {

        //哈希表：<num,index> 边添加边在map中查找差值的下标
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        int temp;//当前数字和目标值的差值

        for (int i = 0; i < nums.length; i++) {
            temp = target - nums[i];
            //如果找到差值，直接返回两者下标
            if (map.containsKey(temp)) {
                return new int[]{i, map.get(temp)};
            }
            map.put(nums[i], i);
        }
        //在这一句之前已经返回了，故随便返回
        return new int[]{};
    }

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {

        //哈希表：时间复杂度n^2 遍历12，存放1+2的和为key 次数为val 再遍历34统计结果个数
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;

        for (int n1 : nums1) { //统计12的和 和出现的次数
            for (int n2 : nums2) map.merge(n1 + n2, 1, Integer::sum);
        }

        for (int n3 : nums3) { //查找34并统计结果的个数
            for (int n4 : nums4) {
                if (map.containsKey(-(n3 + n4))) {
                    res += map.get(-(n3 + n4));
                }
            }
        }

        return res;
    }

    public void reverseString(char[] s) {
        //双指针：左右交换位置
        int left = 0, right = s.length - 1;

        while (left < right) {
            s[left] ^= s[right];
            s[right] ^= s[left];
            s[left++] ^= s[right--];
        }
    }

    public boolean isMatch(String s, String p) {
        //从前向后比，不相同f,是.过一个再比，是*把前一个复制0-多个
        char[] cs = s.toCharArray(), cp = p.toCharArray();
        int ls = s.length(), lp = p.length();

        //dp[i][j] 前i个s,前j个p 能否匹配上
        boolean[][] dp = new boolean[ls + 1][lp + 1];
        //初始状态：dp[0][0]=true，空字符串能匹配,p消成空，也能匹配
        dp[0][0] = true;
        for (int i = 1; i <= lp; i++) {
            //看能不能把 p 通过*消成空字符串 如 2* i=2 dp[0][2]=dp[0][0]
            if (cp[i - 1] == '*') dp[0][i] = dp[0][i - 2];
        }

        //状态转移：dp[i][j]
        for (int i = 1; i <= ls; i++) {//先遍历s,每个s字符最多用整个p匹配
            for (int j = 1; j <= lp; j++) {
                //当前字符完美匹配
                if (cs[i - 1] == cp[j - 1] || cp[j - 1] == '.') dp[i][j] = dp[i - 1][j - 1];
                //当前字符不能匹配,通过*来细化
                if (cp[j - 1] == '*') {//匹配0次，1次，多次
                    //ss s* 当前s和上个*前p相同,考虑*匹配前个字符0次或多次
                    if (cs[i - 1] == cp[j - 2] || cp[j - 2] == '.')
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];//0次 || 多次
                    else dp[i][j] = dp[i][j - 2];//消去前一个字符试试
                }
            }
        }
        return dp[ls][lp];
    }

    class Solution {
        public String addStrings(String num1, String num2) {
            //

            int p1=num1.length()-1,p2=num1.length()-1;
            int  add=0;
            StringBuilder sb = new StringBuilder();

            while(p1>=0 || p2>=0 || add>0){
                int n1 = p1>=0?num1.charAt(p1--)-'0':0;
                int n2 = p2>=0?num2.charAt(p2--)-'0':0;
                int res = n1+n2+add;
                sb.append(res%10);
                add = res/10;
            }
            return sb.reverse().toString();
        }
    }


    public boolean canConstruct(String ransomNote, String magazine) {

        //哈希表：26字符数组 统计m的字符个数 遍历r减1，<=0返回false
        int[] arr = new int[26];

        //遍历m
        for (char ch : magazine.toCharArray()) arr[ch - 'a']++;
        //遍历r
        for (char ch : ransomNote.toCharArray()) {
            if (arr[ch - 'a'] <= 0) return false;
            arr[ch - 'a']--;
        }

        return true;
    }


    public List<List<String>> groupAnagrams(String[] strs) {

        //哈希表：挨个转字符数组，排序后转字符串作为key，存入map中
        HashMap<String, List<String>> map = new HashMap<>();
        char[] ch;
        String s;

        for (String str : strs) {
            ch = str.toCharArray();
            Arrays.sort(ch);//排序后异位词的字符串相同
            s = String.valueOf(ch);
            //没有就添加一个空列表，后面统一添加字符串
            if (!map.containsKey(s)) map.put(s, new ArrayList<String>());
            map.get(s).add(str);
        }
        //返回map中所有值组成的列表
        return new ArrayList<>(map.values());
    }


}
