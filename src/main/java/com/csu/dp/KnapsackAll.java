package com.csu.dp;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class KnapsackAll {


    //从nums中拼出 sum/2
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();

        //不能是奇数
        if ((sum & 1) == 1) return false;

        //要拼出的目标值
        int target = sum / 2;

        //状态 dp[i] 容量i,子集凑出的最大总和
        //dp[i]=i说明刚好可以凑出i，此处容量和价值是一个单位
        int[] dp = new int[target + 1];

        //初始状态 凑出0的最大总和dp[0]=0
        //状态转移 dp[i] = max(dp[i],dp[i-nums[j]]+nums[j])
        for (int i = 0; i < nums.length; i++) { //元素
            //对每一个元素都试探下要不要放入
            for (int j = target; j >= nums[i]; j--) { //容量
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[target] == target;
    }


    public int lastStoneWeightII(int[] stones) {

        //石头分两堆，尽量接近sum/2
        int sum = 0;
        for (int stone : stones) sum += stone;

        //尽量找到接近sum/2的石头集合，跟另一半碰
        //(sum - dp[target]) - dp[target]
        int target = sum / 2; //向下取整

        //状态 dp[i] 容量i,子集凑出的最大总和
        int[] dp = new int[target + 1];

        //初始状态 凑出0的最大总和dp[0]=0
        //状态转移 dp[i] = max(dp[i],dp[i-nums[j]]+nums[j])
        for (int i = 0; i < stones.length; i++) { //元素
            //对每一个元素都试探下要不要放入
            for (int j = target; j >= stones[i]; j--) { //容量
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }
        return sum - 2 * dp[target];
    }


    public int findTargetSumWays(int[] nums, int target) {

        int sum = Arrays.stream(nums).sum();

        //全正<正目标值 or 全负>负目标值
        if (Math.abs(sum) < Math.abs(target)) return 0;

        //正数和 num  负数和 sum-num  num-(sum-num) = target
        //拼出num的方法数，就是返回值
        int num = (target + sum) / 2;
        //向下取整的奇数偶数考虑，奇数无解（都是整数，拼不出来）
        if (((target + sum) & 1) == 1) return 0;

        //状态 dp[i] i为目标的方法数
        int[] dp = new int[num + 1];

        //初始状态 拼出0的方法数1  dp[0]=1
        dp[0] = 1; //dp[j - nums[i]]的起始元素是dp[0]

        //状态转移 dp[i] = dp[i-num[j]]
        for (int i = 0; i < nums.length; i++) {
            for (int j = num; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[num];
    }


    //双纬度0-1背包问题
    public int findMaxForm(String[] strs, int m, int n) {

        //状态 dp[i][j] i个0，j个1的最大子集长度
        int[][] dp = new int[m + 1][n + 1];
        //初始状态  dp[0][0] = 0

        //统计每个物品中0,1的个数
        int num_0, num_1;

        for (String str : strs) { //遍历字符串
            num_0 = 0;
            num_1 = 0;//统计该字符串中 0和1 的数量
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') num_0++;
                else num_1++;
            }
            //遍历背包容量，从大到小
            for (int i = m; i >= num_0; i--) {//最小是本字符串0数量
                for (int j = n; j >= num_1; j--) {
                    //状态转移：要当前物品(的最大子集长度)，或不要(的长度)
                    dp[i][j] = Math.max(dp[i][j], //不要本字符串
                            dp[i - num_0][j - num_1] + 1);//要+1
                }
            }
        }
        return dp[m][n];
    }

    //完全背包—组合数
    public int change(int amount, int[] coins) {

        //状态 dp[i] 总数为i时的组合数
        int[] dp = new int[amount + 1];
        //初始状态 dp[0] = 1
        dp[0] = 1;

        //组合数：先物品再容量
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                //状态转移：如果上一步不可行，加的是0
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }


    //完全背包—排列数
    public int combinationSum4(int[] nums, int target) {

        //dp[i] 总和为i的排列数
        int[] dp = new int[target + 1];
        //初始状态dp[0] = 1
        dp[0] = 1;

        //先容量后物品
        for (int i = 0; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i < nums[j]) continue;
                dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }

    //完全背包求最小值
    public int coinChange(int[] coins, int amount) {

        int max = Integer.MAX_VALUE;

        //状态  dp[i] 金额的最少硬币数
        int[] dp = new int[amount + 1];
        //初始状态 0处为0，所有位置处都为最大值;
        for (int j = 1; j < dp.length; j++) dp[j] = max;

        //顺序无要求
        for (int i = 0; i < coins.length; i++) { //物品
            for (int j = coins[i]; j <= amount; j++) { //背包
                //只有dp[j-coins[i]]不是初始最大值时才有选择的必要
                if (dp[j - coins[i]] == max) continue;
                //状态转移：要当前物品的最少硬币数，和不要的最少硬币数
                dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }

    //完全背包求最小值
    public int numSquares(int n) {

        //状态： dp[i] 和为i的最小完全平方数个数
        int[] dp = new int[n + 1];
        //初始状态：dp[0]=0，所有位置处都为最大值;
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;//dp[i - j * j] + 1 初始化dp[0] + 1

        for (int i = 1; i * i <= n; i++) {//物品
            for (int j = i * i; j <= n; j++) {//容量
                dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
            }
        }

//        for (int i = 0; i <= n; i++) { //背包
//            for (int j = 1; j * j <= i; j++) { //物品
//                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
//            }
//        }

        return dp[n];
    }


    //完全背包
    public boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();

        //状态：dp[i] 前i个字符是否可以拆分
        boolean[] dp = new boolean[len + 1];
        //初始状态 dp[0] = true
        dp[0] = true;

        for (int i = 1; i <= len; i++) { //背包
            for (String value : wordDict) { //物品
                int size = value.length();
                if (size > i) continue;
                //单词是dp[i]的结尾子串，且去掉子串dp[i-s]为true
                if (dp[i - size] && value.equals(s.substring(i - size, i))) {
                    dp[i] = true;
                    break;//问的是能否，而不是方案数，故提前返回
                }
            }
        }
        return dp[len];
    }


}
