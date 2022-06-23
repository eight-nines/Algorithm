package com.csu.algorithm.Tree;

import java.util.*;

public class BackTrackingAll {


    class Solution5 {
        ArrayList<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();

        public List<List<Integer>> combine(int n, int k) {
            //回溯:区间回溯，每次在一个区间内变化起始节点并记录节点组合
            dfs(1, n, k);
            return res;
        }

        //左闭右闭，区间[st,en]中k个数的组合
        void dfs(int st, int en, int k) {

            if (k == 0) {//达到叶子节点，记录即可
                res.add(new ArrayList<>(list));
                return;
            }
            //剪枝：区间[1,2]中1个数，i最小为st=1,最大为en-k+1
            for (int i = st; i <= en - k + 1; i++) {
                list.add(i);//处理当前节点
                dfs(i + 1, en, k - 1);//递归
                list.remove(list.size() - 1);//回溯
            }
        }
    }


    class Solution3 {
        String[] map = new String[]{"", "", "abc", "def",
                "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ArrayList<String> res = new ArrayList<>();
        char[] str;

        public List<String> letterCombinations(String digits) {
            //回溯：使用char数组可以避免使用sb时的回溯，数组会自动覆盖
            if (digits.length() < 1) return res;
            str = new char[digits.length()];
            dfs(digits.toCharArray(), 0);
            return res;
        }

        //左闭右闭：[i,len-1]区间的每个数字的字母选择
        void dfs(char[] dig, int i) {
            if (i == dig.length) {
                res.add(new String(str));
                return;
            }

            //遍历当前数字的可选字母
            for (char c : map[dig[i] - '0'].toCharArray()) {
                str[i] = c;//处理当前节点，对应的char元素赋值为当前字符
                dfs(dig, i + 1);//递归，数组会自动覆盖，不用回溯
            }
        }
    }

    class Solution {
        ArrayList<List<String>> res = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        public List<List<String>> partition(String s) {
            dfs(s.toCharArray(), 0);
            return res;
        }

        //左闭右闭区间[st,len-1]上切割，并记录回文串
        void dfs(char[] s, int st) {//st:切割的起始位置
            if (st == s.length) {//st>len-1
                res.add(new ArrayList<>(list));
                return;
            }

            //先确保左侧是一个回文串，第一刀是一个字符，必然是回文串 st=i
            for (int i = st; i < s.length; i++) {
                if (isP(s, st, i)) list.add(new String(s, st, i - st + 1));
                else continue;//向右移，使左侧是回文串

                dfs(s, i + 1);//递归
                list.remove(list.size() - 1);//回溯
            }
        }

        //判断字符数组[i,j]范围是不是回文串
        boolean isP(char[] s, int st, int en) {
            while (st < en) {
                if (s[st++] != s[en--]) return false;
            }
            return true;
        }
    }


    class Solution40 {
        ArrayList<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();

        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            //回溯：数组内存在重复元素，升序排序，“同一层”(每次dfs的区间)选过的元素不可以重复选
            Arrays.sort(candidates);
            dfs(candidates, target, 0, 0);
            return res;
        }

        //左闭右闭[st,len-1]所有数中选数字，每次dfs的区间就是一层
        void dfs(int[] arr, int tar, int sum, int st) {
            if (sum == tar) {
                res.add(new ArrayList<>(list));
                return;
            }

            //排序后可以剪枝，只要当前sum>tar，就停止遍历
            for (int i = st; i < arr.length && sum + arr[i] <= tar; i++) {
                if (i > st && arr[i] == arr[i - 1]) continue;//层去重 112 12 12 重复
                list.add(arr[i]);
                sum += arr[i];//处理当前节点
                dfs(arr, tar, sum, i + 1);//递归
                list.remove(list.size() - 1);
                sum -= arr[i];//回溯
            }
        }
    }


    class Solution7 {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int sum = 0;//统计添加的次数，最多4次

        public List<String> restoreIpAddresses(String s) {
            //回溯：字符串->...->字符串 [start,i]是子串的范围，左闭右闭
            backTrack(s, 0);
            return res;
        }

        void backTrack(String s, int start) {
            if (sum == 4) {
                if (sb.length() == s.length() + 4) {
                    //删掉最后的多余逗号,不能修改sb,不然回溯会失败
                    res.add(sb.toString().substring(0, sb.length() - 1));
                }
                return;
            }

            for (int i = start; i < s.length(); i++) {
                if (isV(s, start, i)) { //1,1
                    sb.append(s.substring(start, i + 1));
                    sb.append(".");
                    sum++;
                } else break;//此处已经不符合了，向后也不可能符合
                backTrack(s, i + 1);
                sb.delete(sb.length() - (i - start + 2), sb.length());
                sum--;
            }
        }

        boolean isV(String s, int l, int r) {
            if (r - l + 1 > 3) return false; //长度不能大于3

            if (l != r && s.charAt(l) == '0') return false; //不能有前导0
            //不能大于255
            return Integer.parseInt(s.substring(l, r + 1)) <= 255;
        }
    }


    class Solution78 {

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        public List<List<Integer>> subsets(int[] nums) {
            //回溯+dfs：没有重复元素，不需要层去重；收集节点，不需要终止条件
            backTrack(nums, 0);
            return res;
        }

        void backTrack(int[] nums, int start) {
            //收集节点，不需要终止条件，走到哪收集到哪，不会重复
            res.add(new ArrayList<>(list));

            //从起始点开始遍历
            for (int i = start; i < nums.length; i++) {
                list.add(nums[i]);
                backTrack(nums, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    class Solution90 {

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        public List<List<Integer>> subsetsWithDup(int[] nums) {
            //回溯+dfs+层去重；收集节点，不需要终止条件
            Arrays.sort(nums);
            backTrack(nums, 0);
            return res;
        }

        void backTrack(int[] nums, int start) {
            //收集节点，不需要终止条件，走到哪收集到哪，不会重复
            res.add(new ArrayList<>(list));

            //从起始点开始遍历
            for (int i = start; i < nums.length; i++) {
                if (i > start && nums[i] == nums[i - 1]) continue;
                list.add(nums[i]);
                backTrack(nums, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    class Solution491 {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        public List<List<Integer>> findSubsequences(int[] nums) {
            //回溯+层去重：标记用过的元素的集合应该放在层中局部变量
            dfs(nums, 0);
            return res;
        }

        void dfs(int[] nums, int start) {
            if (list.size() > 1) res.add(new ArrayList<>(list));

            //标记当前层用过了哪些数，注意是局部变量，只在层有效
            boolean[] flag = new boolean[201];

            for (int i = start; i < nums.length; i++) {
                if (list.isEmpty() || nums[i] >= list.get(list.size() - 1)) {
                    if (flag[nums[i] + 100]) continue;//当前层用过，pass
                    list.add(nums[i]);
                    flag[nums[i] + 100] = true;//标记当前层用过
                    dfs(nums, i + 1);
                    list.remove(list.size() - 1);
                }
            }
        }
    }


    class Solution46 {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        boolean[] flag = new boolean[6];//最多6个数，题目给出

        public List<List<Integer>> permute(int[] nums) {
            //回溯+纵向/枝去重：在一次排列中，已经出现过的元素，不能重复出现
            dfs(nums);
            return res;
        }

        void dfs(int[] nums) {
            if (list.size() == nums.length) res.add(new ArrayList<>(list));

            //全数组搜索
            for (int i = 0; i < nums.length; i++) {
                if (flag[i]) continue; //当前排列出现过
                list.add(nums[i]); flag[i] = true;
                dfs(nums);
                list.remove(list.size() - 1); flag[i] = false;
            }
        }
    }

    class Solution21 {
        List<String> res = new ArrayList<>();
        HashMap<String, Map<String, Integer>> map = new HashMap<>();

        public List<String> findItinerary(List<List<String>> tickets) {
            //Map映射：不同起点的终点，Map<start,treeMap<end,num>>
            //回溯：从起点出发，每层选择当前字典序最小的出发
            for (List<String> ticket : tickets) {
                Map<String, Integer> temp;
                String start = ticket.get(0);
                String end = ticket.get(1);
                if (map.containsKey(start)) {//起点建立过映射
                    temp = map.get(start);
                    temp.put(end, temp.getOrDefault(end, 0) + 1);
                } else {//起点还没有建立过映射
                    temp = new TreeMap<>();
                    temp.put(end, 1);
                    map.put(start, temp);
                }
            }
            res.add("JFK");//设定起点
            backTrack(tickets.size() + 1);//节点数 = 票数+1
            return res;
        }

        //设置返回值的意义是剪枝，提前返回，避免多余的回溯
        boolean backTrack(int tNum) {
            if (res.size() == tNum + 1) return true;

            //先获得当前节点的可遍历集合(<目的地,票数>)
            Map<String, Integer> temp = map.get(res.get(res.size() - 1));
            if (temp == null) return false; //此路不通，无法出发
            for (Map.Entry<String, Integer> target : temp.entrySet()) {
                if (target.getValue() <= 0) continue;//没票了

                res.add(target.getKey());//添加行程
                target.setValue(target.getValue() - 1);
                if (backTrack(tNum)) return true; //有效路径，直接返回
                res.remove(res.size() - 1);
                target.setValue(target.getValue() + 1);
            }
            return false;
        }
    }


    class Solution47 {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        boolean[] flag1 = new boolean[8];//最多8个数，题目给出

        public List<List<Integer>> permuteUnique(int[] nums) {
            //回溯：层去重+纵向/枝去重:flag1+flag2
            //枝去重：当前枝没用过i下标元素；层去重：当前层没用过当前元素
            dfs(nums);
            return res;
        }

        void dfs(int[] nums) {
            if (nums.length == list.size()) res.add(new ArrayList<>(list));

            boolean[] flag2 = new boolean[21];//层去重
            for (int i = 0; i < nums.length; i++) {
                if (flag1[i] || flag2[nums[i] + 10]) continue;//当前枝和层都没用过
                list.add(nums[i]); flag1[i] = true; flag2[nums[i] + 10] = true;
                dfs(nums);
                list.remove(list.size() - 1); flag1[i] = false;
            }
        }
    }




    class Solution51 {
        List<List<String>> res = new ArrayList<>();
        List<String> list = new ArrayList<>();
        int[] flag; //标记每行中Q的位置

        public List<List<String>> solveNQueens(int n) {
            flag = new int[n];
            dfs(0, 0, n);
            return res;
        }

        void dfs(int row, int col, int n) {
            if (row == n) {
                res.add(new ArrayList<>(list));
                return;
            } else if (col == n) return;
            //检测当前位置能否放
            if (check(row, col, n)) {//可以放，放当前位置，递归+回溯
                char[] chars = new char[n];
                Arrays.fill(chars, '.');
                chars[col] = 'Q';
                list.add(new String(chars));//添加当前行
                flag[row] = col;
                dfs(row + 1, 0, n);
                list.remove(list.size() - 1);
            }
            dfs(row, col + 1, n);//遍历下个位置
        }

        boolean check(int row, int col, int n) {
            for (int i = 0; i < row; i++) {//行
                if (flag[i] == col) return false;
                if (row - i == Math.abs(flag[i] - col)) return false;
            }
            return true;
        }
    }



    class Solution15 {
        List<String> res = new ArrayList<>();
        Map<String, Map<String, Integer>> map = new HashMap<>();

        public List<String> findItinerary(List<List<String>> tickets) {
            //回溯:建立机场映射 <起点,<目的地,票数>>，<目的地,票数>要使用treeMap
            //起始->目的地集合->...->目的地集合 找到一条路径(票数+1个节点)并返回
            for (List<String> t : tickets) { //遍历机票
                Map<String, Integer> temp;
                if (map.containsKey(t.get(0))) { //起点有了
                    temp = map.get(t.get(0));//获取起点对应的<目的地,票数>，并更新
                    temp.put(t.get(1), temp.getOrDefault(t.get(1), 0) + 1);
                } else { //起点没有，创建treemap
                    temp = new TreeMap<>();
                    temp.put(t.get(1), 1);
                    map.put(t.get(0), temp);
                }
            }

            res.add("JFK");//设定起点
            backTrack(tickets.size());//传递票数即可
            return res;
        }

        //设置返回值的意义是剪枝，提前返回，避免多余的回溯
        boolean backTrack(int tNum) {
            if (res.size() == tNum + 1) return true;

            //先获得当前节点的可遍历集合(<目的地,票数>)
            Map<String, Integer> temp = map.get(res.get(res.size() - 1));
            if (temp == null) return false; //此路不通，无法出发
            for (Map.Entry<String, Integer> target : temp.entrySet()) {
                if (target.getValue() <= 0) continue;//没票了

                res.add(target.getKey());//添加行程
                target.setValue(target.getValue() - 1);
                if (backTrack(tNum)) return true; //有效路径，直接返回
                res.remove(res.size() - 1);
                target.setValue(target.getValue() + 1);
            }
            return false;
        }
    }

    class Solution16 {
        List<List<String>> res = new ArrayList<>();
        int[] queen;//每行中queen的列位置

        public List<List<String>> solveNQueens(int n) {
            //回溯：数组->...->数组 直到和>=目标值
            queen = new int[n];
            backTrack(0, n);
            return res;
        }

        void backTrack(int row, int n) {//摆放row行的queen (0,n-1)
            if (row == n) {//摆完了,存放结果
                List<String> list = new ArrayList<>();
                for (int col : queen) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < n; i++) sb.append(".");
                    sb.replace(col, col + 1, "Q");
                    list.add(sb.toString());
                }
                res.add(list);
                return;
            }

            //遍历当前行的所有列，尝试摆放queen
            for (int i = 0; i < n; i++) {
                if (!isV(row, i)) continue;//检查可行性
                queen[row] = i;
                backTrack(row + 1, n);
                //不用回溯，下次循环会自动覆盖
                //queen[row] = 0;->回溯：可以但没必要
            }
        }

        //当前位置是否可以摆放
        boolean isV(int row, int col) {
            for (int i = 0; i < row; i++) {//遍历之前的所有行
                if (queen[i] == col) return false;//列相同
                //对角线:注意要加绝对值！
                if (row - i == Math.abs(col - queen[i])) return false;
            }
            return true;
        }
    }


    class Solution17 {
        int[] queen;//每行中queen的列位置
        int res = 0;

        public int totalNQueens(int n) {
            //回溯：数组->...->数组 直到和>=目标值
            queen = new int[n];
            backTrack(0, n);
            return res;
        }

        void backTrack(int row, int n) {//摆放row行的queen (0,n-1)
            if (row == n) {//摆完了,存放结果
                res++;
                return;
            }

            //遍历当前行的所有列，尝试摆放queen
            for (int i = 0; i < n; i++) {
                if (!isV(row, i)) continue;//检查可行性
                queen[row] = i;
                backTrack(row + 1, n);
                //不用回溯，下次循环会自动覆盖
                //queen[row] = 0;->回溯：可以但没必要
            }
        }

        //当前位置是否可以摆放
        boolean isV(int row, int col) {
            for (int i = 0; i < row; i++) {//遍历之前的所有行
                if (queen[i] == col) return false;//列相同
                //对角线:注意要加绝对值！
                if (row - i == Math.abs(col - queen[i])) return false;
            }
            return true;
        }
    }

    class Solution19 {
        public void solveSudoku(char[][] board) {
            //回溯：数组->...->数组 无需终止条件，遍历整个矩阵
            //回溯函数要有返回值，试完最后一个空依次返回，相当于终止条件
            backTrack(board);
        }

        boolean backTrack(char[][] board) {
            for (int i = 0; i < 9; i++) {//行
                for (int j = 0; j < 9; j++) {//列
                    if (board[i][j] != '.') continue;
                    for (char k = '1'; k <= '9'; k++) {//数字

                        if (!isV(board, i, j, k)) continue;
                        board[i][j] = k;
                        //此处充当了终止条件
                        if (backTrack(board)) return true;
                        board[i][j] = '.';
                    }
                    return false;
                }
            }
            return true;
        }

        //判断当前数字是否可以填到当前位置
        boolean isV(char[][] board, int row, int col, char k) {
            for (int i = 0; i < 9; i++) {//判断列
                if (board[i][col] == k) return false;
            }
            for (int i = 0; i < 9; i++) {//判断行
                if (board[row][i] == k) return false;
            }
            int startRow = (row / 3) * 3;//方块左上角行下标
            int startCol = (col / 3) * 3;//方块左上角列下标
            for (int i = startRow; i < startRow + 3; i++) {//检查矩阵
                for (int j = startCol; j < startCol + 3; j++) {
                    if (board[i][j] == k) return false;
                }
            }
            return true;
        }
    }


    class Solution39 {
        ArrayList<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            //回溯：值可重复(纵向不去重)，结果不能重复(横向去重)
            //st横向去重，每次选数字，只选自己和之后的数字，之前的数字不选
            dfs(candidates, target, 0, 0);
            return res;
        }

        //左闭右闭[st,len-1]所有数中选数字，st可以选，横向去重
        void dfs(int[] arr, int tar, int sum, int st) {
            if (sum >= tar) {
                if (sum == tar) res.add(new ArrayList<>(list));
                return;
            }

            //每次都遍历所有元素
            for (int i = st; i < arr.length; i++) {
                list.add(arr[i]);
                sum += arr[i];//处理当前节点
                dfs(arr, tar, sum, i);//递归
                list.remove(list.size() - 1);
                sum -= arr[i];//回溯
            }
        }
    }


}












































