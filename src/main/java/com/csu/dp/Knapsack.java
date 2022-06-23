package com.csu.dp;


import java.util.List;

// 0-1背包问题
public class Knapsack {
    public static void main(String[] args) {
        int[] values = {6, 3, 5, 4, 6};
        int[] weights = {2, 2, 6, 5, 4};
        int capacity = 10;
        System.out.println(maxValueExactly(values, weights, capacity));
    }

    /**
     * @return 如果返回-1，代表没法刚好凑到capacity这个容量
     */
    static int maxValueExactly(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int j = 1; j <= capacity; j++) {
            dp[j] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= weights[i - 1]; j--) {
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
            }
        }
        //如果返回-1，代表没法刚好凑到capacity这个容量
        return dp[capacity] < 0 ? -1 : dp[capacity];
    }

    public int maxValue(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;

        //状态 dp[i] i容量的最大价值
        int[] dp = new int[capacity + 1];
        //初始状态 0容量dp[0] = 0

        //状态转移 dp[j] = max(dp[j] 不放i本物品,
        // dp[j - weights[i]] +values[i] 放入i物品);
        for (int i = 0; i < values.length; i++) {
        	//注意此处从后向前遍历，避免重复放入同一个物品
            for (int j = capacity; j >= weights[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }




    //多重背包
    public int multiKnapsack(int[] nums, List<Integer> values, List<Integer> weights, int capacity) {

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 1) {
                values.add(values.get(i));
                weights.add(weights.get(i));
                nums[i]--;
            }
        }

        //状态 dp[i] i容量的最大价值
        int[] dp = new int[capacity + 1];
        //初始状态 0容量dp[0] = 0

        for (int i = 0; i < values.size(); i++) { //物品
            int weight = weights.get(i);
            int value = values.get(i);
            for (int j = capacity; j >= weight; j--) { //背包
                dp[j] = Math.max(dp[j], dp[j - weight] + value);
            }
        }
        return dp[capacity];
    }
















    //二维数组实现
    public static int maxValue1(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;

        int len = weights.length;

        //状态 dp[i][j] j容量，前i个物品的最大价值
        int[][] dp = new int[len + 1][capacity + 1];
        //初始状态  0物品dp[0][j] 0容量dp[i][0] =0

        //状态转移 dp[i][j] = max(dp[i-1][j] 相同容量不放i物品,
        //				dp[i-1][j-weight[i]] + value[i])
        // 相同容量放i物品,要减去i物品的重量，加上i物品的价值)
        for (int i = 1; i <= len; i++) { //遍历物品
            for (int j = 1; j <= capacity; j++) { //遍历容量
                if (j < weights[i - 1]) dp[i][j] = dp[i - 1][j];
                else dp[i][j] = Math.max(
                        dp[i - 1][j], //相同容量不放i物品
                        values[i - 1] + dp[i - 1][j - weights[i - 1]]
                );
            }
        }
        return dp[len][capacity];
    }
}
