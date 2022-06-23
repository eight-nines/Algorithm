package com.csu.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class dpAll {


    public int lengthOfLIS(int[] nums) {
        int len = nums.length;

        //状态：dp[i] 到i下标元素的最长长度
        int[] dp = new int[len];
        //初始状态：每个位置都为1
        Arrays.fill(dp, 1);

        int res = 1;
        //状态转移：如果当前数大于前面的数，那个数的dp+1
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    public int findLengthOfLCIS(int[] nums) {
        int len = nums.length;

        //状态：dp[i] 到i下标元素的最长长度
        int[] dp = new int[len];
        //初始状态：每个位置都为1
        Arrays.fill(dp, 1);

        int res = 1;

        //状态转移：如果当前数大于前一个数，前个数的dp+1
        for (int i = 1; i < len; i++) {
            if (nums[i] <= nums[i - 1]) continue;
            dp[i] = dp[i - 1] + 1;
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public int findLength(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;

        //状态：dp[i][j] 下标i-1的A，j-1的B的最大重复长度
        //原因：预留第一行第一列作为状态转移的初始化边界，不然要额外初始化
        //dp[i][j] = dp[i - 1][j - 1] + 1;
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态 dp[0][j]=dp[i][0]=0

        int res = 0;

        //状态转移：nums1[i-1]=nums2[j-1] 上一个长度+1
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                if (nums1[i - 1] != nums2[j - 1]) continue;
                dp[i][j] = dp[i - 1][j - 1] + 1;
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }

    public int longestCommonSubsequence(String text1, String text2) {

        int len1 = text1.length(), len2 = text2.length();
        char[] t1 = text1.toCharArray(), t2 = text2.toCharArray();

        //状态：dp[i][j] 下标i-1的t1,和下标j-1的t2
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态：dp[0][j]=dp[i][0]=0

        //状态转移：t1[i-1]=t2[j-1]直接+1，否则等于 左-1、右-1 较大值
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                if (t1[i - 1] == t2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                    //不需要+1，因为之前已经算过了
                else dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }
        return dp[len1][len2];
    }


    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;

        //相当于找最大公共子序列长度
        //状态：dp[i][j] 下标i-1的A，j-1的B的最大重复长度
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态：dp[0][j]=dp[i][0]=0

        int res = 0;

        //状态转移：t1[i-1]=t2[j-1]直接+1，否则等于 左-1、右-1 较大值
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                if (nums1[i - 1] == nums2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                    //不需要+1，因为之前已经算过了
                else dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }
        return dp[len1][len2];
    }


    public int maxSubArray(int[] nums) {

        int len = nums.length;

        //状态：dp[i] 下标i的nums最大子数组和
        int[] dp = new int[len];
        //初始状态：dp[0]=nums[0];
        dp[0] = nums[0];

        int res = dp[0];

        //状态转移：max(加前一个数，不加前一个数)
        for (int i = 1; i < len; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    //编辑距离问题
    public boolean isSubsequence(String s, String t) {
        int len1 = s.length(), len2 = t.length();
        char[] s1 = s.toCharArray(), s2 = t.toCharArray();

        //状态：dp[i][j] 下标i-1的A，j-1的B的公共子序列长度
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态 dp[0][j]=0  dp[i][0] 0

        //状态转移 s1[i-1]==s2[j-1]?
        //相等：dp[i-1][j-1]+1；不相等：dp[i][j-1]
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                if (s1[i - 1] == s2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                    //都向前加一个元素，不相等，比较i和j-1是否相等
                else dp[i][j] = dp[i][j - 1];//i没有必要向前退
            }
        }
        return dp[len1][len2] == len1;
    }

//    public int numDistinct(String s, String t) {
//        int len1 = s.length(), len2 = t.length();
//        char[] s1 = s.toCharArray(), s2 = t.toCharArray();
//
//        //状态：dp[i][j] 下标i-1的s，j-1的t出现的个数
//        int[][] dp = new int[len1 + 1][len2 + 1];
//
//        //状态转移：s1[i-1]==s2[j-1]?
//        //相等：用i-1 dp[i - 1][j - 1]+不用i-1 dp[i - 1][j]
//        // 其中不用i-1相当于
//        //不相等
//
//
//
//        //dp[i - 1][j] 意为t进一步，但s不进一步
//        //相当于baa 和 ba 在2/1处相等，但要加上1/1的结果数
//
//
//
//    }


    public int numDistinct(String s, String t) {
        int len1 = s.length(), len2 = t.length();
        char[] s1 = s.toCharArray(), s2 = t.toCharArray();

        //状态：dp[i][j] 下标i-1的s 中 j-1的t出现的个数
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态：dp[i][0]=1 t长度为0时 初始化为1
        for (int i = 0; i <= len1; i++) dp[i][0] = 1;

        //状态转移：s1[i-1]==s2[j-1]？相等：两部分组成
        //相等：①用s1[i-1]匹配s2[j-1]->dp[i-1][j-1]
        //相等：②用s1[i-2]匹配s2[j-1]->dp[i-1][j]
        //不相等：用s1[i-1]匹配s2[j-1]->dp[i-1][j-1]
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1[i - 1] == s2[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                else dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[len1][len2];
    }

    public int minDistance1(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        char[] s1 = word1.toCharArray(), s2 = word2.toCharArray();

        //状态：dp[i][j] s[i-1] 和 t[j-1]要相等需要删的步数
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态：dp[i][j]，一个为0时，另一个步数为i
        for (int i = 1; i <= len1; i++) dp[i][0] = i;
        for (int j = 1; j <= len2; j++) dp[0][j] = j;

        //状态转移：相等：不用操作，等于s[i-2]和t[j-2]的步数
        //不相等：删s[i-1]，删t[i-1]，两个都删，取最小值
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1[i - 1] == s2[j - 1])//相等，查看上一步
                    dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i - 1][j - 1] + 2,//都删了
                        Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));//删一个
            }
        }
        return dp[len1][len2];
    }


    public int longestValidParentheses(String s) {
        //动态规划
        int len = s.length();
        char[] str = s.toCharArray();
        //状态 dp[i] 包括下标i的字符串最大有效长度
        int[] dp = new int[len];

        int res = 0;
        //状态转移：遇到}开启判断，左侧是{，+左侧之前的有效长度
        //左侧不是{，判断中间有效长度左侧是不是{，匹配后+左侧之前的有效长度
        for (int i = 1; i < len; i++) {
            if (str[i] == '(') continue;//遇到}开启判断

            if (str[i - 1] == '(') {//先判断左边能否构成
                dp[i] = 2;
                if (i - 2 > 0) dp[i] += dp[i - 2];//加上当前{}前的有效长度
            } else {//当前是}，前一个也是}
                int pre = i - dp[i - 1] - 1;//前一个}有效长度外的第一个下标
                if (pre >= 0 && str[pre] == '(') {
                    dp[i] = dp[i - 1] + 2;//中间有效长度+2(两边匹配)
                    if (pre - 1 > 0)
                        dp[i] += dp[pre - 1];//当前匹配左侧的有效长度
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }



    class Solution1 {

        public int maximalSquare(char[][] matrix) {
            //状态：dp[i][j] i行j列的最大边长
            int rows = matrix.length, cols = matrix[0].length;
            int[][] dp = new int[rows + 1][cols + 1];

            int res = 0;
            //遍历出发点
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= cols; j++) {
                    if (matrix[i - 1][j - 1] == '0') continue;
                    //min(左上角，左，上),边长是从之前的传递过来的,有一个是0,全部是0
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],//左上角
                            Math.min(dp[i - 1][j], dp[i][j - 1]));//左、上
                    res = Math.max(res, dp[i][j]);
                }
            }
            return res * res;
        }
    }

    public int maxProduct(int[] nums) {
        //存在负数，每次记录最大乘积(正)，最小乘积(负)，不断更新
        //由于存在负数，那么会导致最大的变最小的，最小的变最大的
        int len = nums.length;
        int max=1,min=1,res=Integer.MIN_VALUE;

        for (int num : nums) {
            //遇到负数，max*n变最小值，min*n变最大值，故交换
            if (num < 0) {int t = max;max = min;min = t;}
            //状态转移：max(当前数，当前数与之前连续)
            max = Math.max(num, num * max);//连续最大值
            min = Math.min(num, num * min);//连续最小值
            res = Math.max(res, max);
        }
        return res;
    }






    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        char[] s1 = word1.toCharArray(), s2 = word2.toCharArray();

        //状态：dp[i][j] s[i-1] 和 t[j-1]要相等需要的操作数
        //操作：添加s相当于删除t,如 ad 和 a
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始状态：dp[i][j]，一个为0时，另一个步数为i
        for (int i = 1; i <= len1; i++) dp[i][0] = i;
        for (int j = 1; j <= len2; j++) dp[0][j] = j;

        //状态转移：相等：不用操作，等于s[i-2]和t[j-2]的步数
        //不相等：删s[i-1]，删t[i-1]，替换其中一个dp[i-1][j-1]，取最小值
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1[i - 1] == s2[j - 1])//相等，查看上一步
                    dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],//替换
                        Math.min(dp[i][j - 1], dp[i - 1][j]));//删一个
            }
        }
        return dp[len1][len2];
    }

    public int minPathSum(int[][] grid) {
        //动态规划：初始化第一行第一列
        int rows = grid.length, cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        //初始状态：初始化第一行第一列
        dp[0][0] = grid[0][0];
        for (int i = 1; i < cols; i++) dp[0][i] = grid[0][i] + dp[0][i - 1];
        for (int i = 1; i < rows; i++) dp[i][0] = grid[i][0] + dp[i - 1][0];

        //状态转移：上一步的最小值+自己的值
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[rows - 1][cols - 1];
    }







    public int countSubstrings(String s) {
        int len = s.length();
        char[] s1 = s.toCharArray();

        //状态：dp[i][j] 区间范围[i,j]子串是否是回文子串
        boolean[][] dp = new boolean[len][len];
        //初始状态：用不着
        int res = 0; //记录个数

        //遍历顺序：根据状态转移方程确定
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (s1[i] != s1[j]) continue;//dp[i][j]=false
                //相等：下标相等，下标差1，下标相差大于1
                if (j - i <= 1 || dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    res++;
                }
            }
        }
        return res;
    }

    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        char[] s1 = s.toCharArray();

        //状态：dp[i][j] 区间范围[i,j]子串的最大长度，左闭右闭
        int[][] dp = new int[len][len];
        //初始状态：i=j时，长度为1
        for (int i = 0; i <len ; i++) dp[i][i] = 1;

        //遍历顺序：根据状态转移方程确定
        for (int i = len - 1; i >= 0; i--) {
            //j=i+1,避免越界,但要单独初始化 j=i的情况
            for (int j = i+1; j < len; j++) {
                if (s1[i] == s1[j]) //相等，回文子序列长度起码是2
                    dp[i][j] =dp[i+1][j-1]+2;
                //不相等：去掉一边，找两个中的最大值
                else dp[i][j] = Math.max(dp[i][j-1],dp[i+1][j]);
            }
        }
        return dp[0][len-1];//区间范围[0,len-1]
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int len = triangle.size();

        //状态 dp[i][j] 第i层选第j个数的最小路径和
        //初始状态：从下至上，最后一行即下标len的初始值为0
        //int[][] dp = new int[len+1][len+1];
        int[] dp = new int[len + 1]; //一维数组优化


        //转移方程：dp[i][j] = min(dp[i-1][j],dp[i-1][j-1])+num
        for (int i = len - 1; i >= 0; i--) { //从最后一行开始，下标为0
            for (int j = 0; j <= i; j++) {
                //获取当前位置数
                int num = triangle.get(i).get(j);
                //下一行邻居节点中的最小值
                //dp[i][j] = Math.min(dp[i+1][j],dp[i+1][j+1])+num;
                dp[j] = Math.min(dp[j], dp[j + 1]) + num;
            }
        }
        //加到最顶上的数中，并返回，每次选邻居节点的最小值
        //return dp[0][0];
        return dp[0];
    }


    public int fib(int n) {

        //滚动数组，记录前两个数的值
        int[] dp = {0, 1};

        for (int i = 2; i <= n; i++) {
            dp[i % 2] = dp[0] + dp[1];
        }

        return dp[n % 2];
    }


    public int climbStairs(int n) {

        //状态  dp[i] 第i个台阶的方法数
        int[] dp = new int[n + 1];
        //初始状态
        dp[0] = 1;
        dp[1] = 1;

        //状态转移：dp[i] = dp[i-1]+dp[i-2]
        //上一层方法数(跳1) + 上上层方法数(跳2)
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];

        //滚动数组优化
        //    int[] dp = new int[2];
        //    dp[0] = 1;dp[1] = 2;
        //    for (int i = 2; i <n ; i++) {
        //        dp[i%2] = dp[0] +dp[1];
        //    }
        //    return dp[(n-1)%2];
    }


    public int minCostClimbingStairs(int[] cost) {

        int len = cost.length;
        //状态   dp[i] 下标i的台阶的最小花费
        int[] dp = new int[len + 1];//多一个是顶部（最后一个）
        //初始状态  下标0、1的台阶都可以 用0 到达

        //状态转移 dp[i] = min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2])
        for (int i = 2; i <= len; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], //一步
                    dp[i - 2] + cost[i - 2]);//两步
        }

        return dp[len];//顶楼

        //滚动数组优化
        //    int[] dp = new int[2];
        //    for (int i = 2; i <=len ; i++) {
        //        dp[i%2] = Math.min(dp[(i-1)%2]+cost[i-1],
        //        dp[(i-2)%2]+cost[i-2]);
        //    }
        //    return dp[len%2];
    }


    public int uniquePaths(int m, int n) {

        //dp[i][j] 到达i,j格的路径数
        int[][] dp = new int[m][n];//m是列数,n是行数

        //初始状态 第一行第一列都只有一种走法
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;

        //状态转移： dp[i][j] = dp[i-1][j] + dp[i][j-1]
        //从第二行第2个开始 下标 dp[1][1]
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                //向下走+向右走
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];

        //滚动数组优化
        //    int[][] dp = new int[2][n+1];//m是列数,n是行数
        //    dp[1][1] =1;dp[0][1] =1;
        //    for (int j = 1; j <= n ; j++) dp[1][j] =1;
        //    for (int i = 2; i <= m; i++) {
        //        for (int j = 2; j <=n ; j++) {
        //            dp[i%2][j] = dp[(i-1)%2][j] + dp[i%2][j-1];
        //        }
        //    }
        //    return dp[m%2][n];
    }


    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        //dp[i][j] 到达i,j格的路径数
        int[][] dp = new int[m][n];//m是列数,n是行数

        //初始状态 第一行第一列都只有一种走法 如果遇到障碍，之后的都是0
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        //状态转移： dp[i][j] = dp[i-1][j] + dp[i][j-1]
        //从第二行第2个开始
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                //遇到障碍，保持初始值0，从前向后保证障碍不通
                if (obstacleGrid[i][j] == 1) continue;
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }


    public int integerBreak(int n) {
        //子问题，[n-i]*i :拆多次 或者 (n-i)*i :拆两次
        //状态  dp[i] 拆分i能得到的最大乘积
        int[] dp = new int[n + 1];

        //初始状态，n>=2  dp[2]=1
        dp[2] = 1;

        //状态转移  dp[i] = max(dp[i-j]*j,(i-j)*j)
        //i是逼近n的数，j是拆分i的步长
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                //取当前i中所有拆分的乘积中的最大值
                dp[i] = Math.max(dp[i], //之间的拆分结果
                        Math.max(dp[i - j] * j, (i - j) * j));
            }
        }
        return dp[n];
    }


    public int rob1(int[] nums) {

        int len = nums.length;

        //状态：dp[i] 前i个屋子的最大金额
        int[] dp = new int[len + 1];
        //初始状态 dp[0] =0 dp[1]=nums[0]
        dp[1] = nums[0];

        //状态转移 dp[i] = max(不偷:dp[i-1],偷:dp[i-2]+nums[i-1])
        //以不偷当前房子的dp[i-1]举例，要的是dp[i-1]的结果，不一定偷i-1
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }

        return dp[len];
    }

    public int rob2(int[] nums) {
        //避免只有一个元素时的越界
        if (nums.length == 1) return nums[0];
        //两种情况：考虑有头无尾；考虑有尾无头，长度是len-1
        //取两者中的最大值返回
        return Math.max(robDo2(nums, 0), robDo2(nums, 1));
    }

    public static int robDo2(int[] nums, int l) {
        int len = nums.length - 1;//有头无尾；有尾无头

        //状态：dp[i] 前i个屋子的最大金额
        int[] dp = new int[len + 1];
        //初始状态 dp[0] =0 dp[1]=nums[l]
        dp[1] = nums[l];

        //状态转移 dp[i] = max(不偷:dp[i-1],偷:dp[i-2]+nums[i-1+l])
        //注意：nums[i-1+l]是为了纠正起始索引带来的影响
        for (int i = 2; i <= len; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1 + l]);
        }

        return dp[len];
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int rob(TreeNode root) {

        //DFS后序遍历：返回偷与不偷根节点的最大值
        int[] res = robDo(root);
        return Math.max(res[0], res[1]);
    }

    public static int[] robDo(TreeNode node) {
        //dp[0] 不偷当前节点的最大金额  dp[1]偷当前节点
        int[] dp = new int[2];
        //空节点返回{0,0}
        if (node == null) return dp;

        int[] dpL = robDo(node.left);
        int[] dpR = robDo(node.right);

        //分两种情况：
        //①不偷当前节点：左右可以偷，选最大值
        dp[0] = Math.max(dpL[0], dpL[1]) + Math.max(dpR[0], dpR[1]);
        //偷当前节点：左右节点不能偷
        dp[1] = node.val + dpL[0] + dpR[0];

        return dp;
    }


    public int maxProfit1(int[] prices) {

        int len = prices.length;

        //状态：dp[i][0]第i天不持有的利润；dp[i][1]第i天持有
        int[][] dp = new int[len][2];
        //初始状态：dp[0][0]=0 第一天不持有，初始值为0
        // dp[0][1]=-prices[0] 第一天持有要掏钱，欠钱
        dp[0][1] = -prices[0];

        //状态转移：dp[i] = max(dp[i],dp[i-])
        for (int i = 1; i < len; i++) {
            //不持有：前天不持有；前天持有，今天卖
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //持有：前天持有；前天不持有，今天买了，注意全程只能买一次
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        //返回最后一天不持有的最大值，如果利润为0的策略是一直不持有
        return dp[len - 1][0];

        //    //滚动数组优化
        //    int[] dp = new int[2];
        //    dp[1]=-prices[0];
        //
        //    for (int i = 1; i < len; i++) {
        //        dp[0] = Math.max(dp[0],dp[1]+prices[i]);
        //        dp[1] = Math.max(dp[1],-prices[i]);
        //    }
        //    return dp[0];
    }


    public int maxProfit2(int[] prices) {
        int len = prices.length;

        //状态：dp[i][0]第i天不持有的利润；dp[i][1]第i天持有
        int[][] dp = new int[len][2];
        //初始状态：dp[0][0]=0 第一天不持有，初始值为0
        // dp[0][1]=-prices[0] 第一天持有要掏钱，欠钱
        dp[0][1] = -prices[0];

        //状态转移：dp[i] = max(dp[i],dp[i-])
        for (int i = 1; i < len; i++) {
            //不持有：前一天不持有，前一天持有今天卖了
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //持有：前一天持有，前一天不持有今天买了
            // 注意全程可买多次，dp[i-1][0]附带了之前可能的利润
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        //返回最后一天不持有的最大值，如果利润为0的策略是一直不持有
        return dp[len - 1][0];

        //    int[][] dp = new int[2][2];
        //    dp[0][1]=-prices[0];
        //
        //    for (int i = 1; i < len; i++) {
        //        dp[i%2][0] = Math.max(dp[(i-1)%2][0],dp[(i-1)%2][1]+prices[i]);
        //        dp[i%2][1] = Math.max(dp[(i-1)%2][1],dp[(i-1)%2][0]-prices[i]);
        //    }
        //    return dp[(len-1)%2][0];
    }


    public int maxProfit3(int[] prices) {

        int len = prices.length;

        //4种状态各自的最大值：
        // 0第一次持有；1第一次卖出；2第二次持有；3第二次卖出
        int[][] dp = new int[len][4];
        //初始状态：持有-price
        dp[0][0] = dp[0][2] = -prices[0];

        for (int i = 1; i < len; i++) {
            //第一次持有：前天第一次持有；今天第一次持有，本金为0
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
            //第一次卖出：前天第一次卖出；前天第一次持有，今天卖出
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
            //第二次持有：前天第二次持有；前天第一次卖出，今天买入
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] - prices[i]);
            //第二次卖出：前天第二次卖出，前天第二次持有，本次卖出
            dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] + prices[i]);
        }
        return dp[len - 1][3];

        //滚动数组优化
        //    int[][] dp = new int[2][4];
        //    dp[0][0] = dp[0][2] = -prices[0];
        //
        //    for (int i = 1; i < len; i++) {
        //        dp[i % 2][0] = Math.max(dp[(i - 1) % 2][0], -prices[i]);
        //        dp[i % 2][1] = Math.max(dp[(i - 1) % 2][1], dp[(i - 1) % 2][0] + prices[i]);
        //        dp[i % 2][2] = Math.max(dp[(i - 1) % 2][2], dp[(i - 1) % 2][1] - prices[i]);
        //        dp[i % 2][3] = Math.max(dp[(i - 1) % 2][3], dp[(i - 1) % 2][2] + prices[i]);
        //    }
        //    return dp[(len - 1) % 2][3];
    }


    public int maxProfit4(int k, int[] prices) {

        int len = prices.length;
        if (len < 2 || k == 0) return 0;

        //2种状态各自的最大值：偶数次持有，奇数次不持有
        // 0第一次持有；1第一次不持有；2第二次持有；3第二次不持有
        int[][] dp = new int[len][2 * k];
        //初始状态：偶数次持有为-price[0]
        for (int i = 0; i < 2 * k; i += 2) {
            dp[0][i] = -prices[0];
        }

        for (int i = 1; i < len; i++) {
            //第一次持有：上一次持有，上一次不持有本次买入，本金为0
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
            //第一次不持有：上一次不持有，上一次持有本次卖出
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
            for (int j = 2; j < 2 * k; j += 2) {
                //偶数次持有：上一次持有，上一次不持有本次买入，本金为上次不持有的利润
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] - prices[i]);
                //奇数次不持有：上一次不持有，上一次持有本次卖出
                dp[i][j + 1] = Math.max(dp[i - 1][j + 1], dp[i - 1][j] + prices[i]);
            }
        }
        return dp[len - 1][2 * k - 1];
    }


    public int maxProfit(int[] prices) {

        int len = prices.length;

        //3种状态：0持有，1不持有，2卖出
        int[][] dp = new int[len][3];

        dp[0][0] = -prices[0];

        for (int i = 1; i < len; i++) {
            //持有：前天不持有，今天买入；前天持有
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            //不持有：前天卖出，今天冷冻期；前天不持有
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][2]);
            //卖出：前天持有
            dp[i][2] = dp[i - 1][0] + prices[i];
        }
        return Math.max(dp[len - 1][1], dp[len - 1][2]);
    }


    public int maxProfit(int[] prices, int fee) {

        int len = prices.length;

        //状态：0持有，1不持有
        int[][] dp = new int[len][2];
        //初始状态：持有-price
        dp[0][0] = -prices[0];

        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
        }
        return dp[len - 1][1];
    }


    public static void main(String[] args) {
        System.out.println(new dpAll().uniquePaths(3, 7));
    }

    public int numTrees(int n) {

        //状态  dp[i] i个节点互不相同的BST数量
        int[] dp = new int[n + 1];

        //初始状态 dp[1]=1
        dp[0] = dp[1] = 1;

        //状态转移，dp[i] = 左子树类数dp[j-1] * 右子树类数dp[i-j]
        //j为根节点 1<=j<=i
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }


}




























