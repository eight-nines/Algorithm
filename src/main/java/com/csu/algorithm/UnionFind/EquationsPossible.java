package com.csu.algorithm.UnionFind;

public class EquationsPossible {


    //1 <= equations.length <= 500
    //equations[i].length == 4
    //equations[i][0] 和 equations[i][3] 是小写字母
    //equations[i][1] 要么是 '='，要么是 '!'
    //equations[i][2] 是 '='

    //常规并查集：QU+PH+rank  92%
    //只有26个字母
    int[] parents = new int[26];
    int[] ranks = new int[26];

    public int find(int v){
        if(parents[v]==v) return v;
        while(parents[v]!=v){
            parents[v] = parents[parents[v]];
            v = parents[v];
        }
        return v;
    }

    public void union(int v1,int v2){
        int p1 = find(v1),p2 = find(v2);
        if(p1==p2) return;
        if(ranks[p1]>ranks[p2]) parents[p2] = p1;
        else if(ranks[p2]>ranks[p1]) parents[p1] = p2;
        else{
            parents[p1] = p2;ranks[p2]++;
        }
    }

    public boolean equationsPossible(String[] equations) {
        for(int i=0;i<parents.length;i++) {
            parents[i]=i;ranks[i]=1;
        }
        int sign,left,right;
        for (String v : equations) {//遍历所有等式
            sign = v.charAt(1);
            left = v.charAt(0) - 'a';
            right = v.charAt(3) - 'a';
            if(sign == '=') union(left,right);
        }
        for (String v : equations) {//遍历所有不等式
            sign = v.charAt(1);
            left = v.charAt(0) - 'a';
            right = v.charAt(3) - 'a';
            if((sign == '!') && (find(left) ==find(right))) return false;
        }
        return true;
    }

    class Solution {
        public boolean equationsPossible(String[] strs) {
            int[] arr = new int[26];
            int count = 1,sign,left,right; //种群的标记
            for (String str : strs) { //遍历所有等式
                sign = str.charAt(1);
                if (sign == '=') {
                    left = str.charAt(0) - 'a';
                    right = str.charAt(3) - 'a';
                    if (arr[left] == 0 && arr[right] == 0) {
                        // 都没有出现过
                        arr[left] = arr[right] = count++;
                    } else if (arr[left] == 0 || arr[right] == 0) {
                        // 只有一个出现过,将他俩的值设置为一样,这里取大取小都行,无所谓
                        int max = Math.max(arr[left], arr[right]);
                        arr[left] = arr[right] = max;
                    } else {
                        // 都出现过,两个集合相交了,将所有等于他俩值的字母的权值设置成同一个,也是取哪个都无所谓
                        int value = arr[left];
                        int value2 = arr[right];
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i] == value || arr[i] == value2) {
                                arr[i] = value;
                            }
                        }
                    }
                }
            }
            // 再次遍历strs,找到所有!=,并判断是否成立
            for (String s : strs) {
                sign = s.charAt(1);
                if (sign == '!') {
                    left = s.charAt(0) - 'a';
                    right = s.charAt(3) - 'a';
                    if (left == right) { // 同一个字母 a!=a 肯定错
                        return false;
                    }
                    if (arr[left] != 0 && arr[right] != 0) {
                        // 都出现过,看两者的权值是否一样,一样就返回false
                        if (arr[left] == arr[right]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }




    public static void main(String[] args) {
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a==b","b!=a"}));
    }

}
