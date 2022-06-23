package com.csu.dataStructure.dynamicArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArrayAll {


    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int mid;
        int left = 0; //左右都包括，左闭右闭
        int right = nums.length - 1;

        // 预留两个元素单独判断：可避免只有两个数时的死循环
        // 如 0 1 mid=0 下一轮 还是0 1 不变，mid只会偏向一边
        while (left + 1 < right) {
            // 位运算时 外面要加个 括号
            mid = left + ((right - left) >> 1);
            // 常规二分法
            // if(nums[mid] == target) return mid;
            // 二分法找最右边的目标值
            if (nums[mid] == target) right = mid;
            else if (nums[mid] > target) right = mid - 1;
            else left = mid + 1;
        }
        // 单独判断两个元素，left 可能等于 right
        if (nums[left] == target) return left;
        if (nums[right] == target) return right;
        return -1;
    }

    public int[] searchRange(int[] nums, int target) {

        //二分法:预留两个元素单独判断，中间遇到目标值，左右扩散并返回
        int[] res = new int[]{-1, -1};
        if (nums == null || nums.length == 0) return res;

        int left = 0, right = nums.length - 1;
        //避免只有两个数时陷入死循环(求mid时只会偏向一边)
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            //找到目标值直接向两边扩散即可
            if (nums[mid] == target) {
                left = mid;//复用left指针向左搜索，mid向右搜索
                //注意 要先判断边界，否则会越界
                while (left >= 0 && nums[left] == target) left--;
                while (mid <= nums.length - 1 && nums[mid] == target) mid++;
                res[0] = left + 1;
                res[1] = mid - 1;
                return res;
            } else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        //只有1-2个值时的返回值
        if (nums[left] == target) {
            res[0] = left;
            res[1] = left;//1、左等右不等
            if (nums[right] == target) {
                res[1] = right;//2、左等右等
            }
            return res;
        }
        if (nums[right] == target) {//3、左不等右等
            res[0] = right;
            res[1] = right;
        }//4、左右都不等

        return res;
    }

    public int search1(int[] nums, int target) {
        //二分法：mid两边必至少有一边是有序的 4560123
        //左边有序，判断目标值在不在，在就向左，否则向右；右边同理
        if (nums == null || nums.length == 0) return -1;

        int mid, left = 0, right = nums.length - 1;

        while (left + 1 < right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) return mid;
            // 左边有序
            if (nums[left] < nums[mid]) {
                if (nums[left] <= target && nums[mid] > target)
                    right = mid - 1; //向左搜索
                else left = mid + 1;
            } else { //右边有序
                if (nums[mid] < target && nums[right] >= target)
                    left = mid + 1; //向右搜索
                else right = mid - 1;
            }
        }
        if (nums[left] == target) return left;
        if (nums[right] == target) return right;
        return -1;
    }


    public int removeElement(int[] nums, int val) {

        //双指针：先统计需要删的长度，快指针遇到目标值跨过去
        int num = (int) Arrays.stream(nums).filter(v -> v == val).count();

        int fast = 0, slow = 0;

        while (fast < nums.length) {
            //不相等时，把快指针位置元素赋值给慢指针位置
            if (nums[fast] != val)
                nums[slow++] = nums[fast];
            //相等时，快指针直接跨过去，慢指针不动
            fast++;
        }
        return slow;
    }

    public int[] sortedSquares(int[] nums) {

        //双指针：数组本身有序，平方最大值一定在两端
        //空间换时间，每次比较两个指针位置的数值，逆序赋值
        int left = 0, right = nums.length - 1;
        int[] res = new int[nums.length];

        for (int i = res.length - 1; i >= 0; i--) {
            if (nums[left] * nums[left] > nums[right] * nums[right]) {
                res[i] = nums[left] * nums[left++];
            } else res[i] = nums[right] * nums[right--];
        }
        return res;
    }

    public int minSubArrayLen(int target, int[] nums) {

        //滑动窗口:当窗口内和大于目标值，缩小窗口；小于目标值，扩大窗口
        int left = 0, right = 0, sum = 0, res = Integer.MAX_VALUE;

        while (right < nums.length) {
            sum += nums[right++];//会影响到下面的right

            while (sum >= target) {
                //此处本应是right-left+1，为了抵消上面的+1
                res = Math.min(res, right - left);
                sum -= nums[left++];
            }
        }
        //如果没改变，证明不存在，返回0
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public int totalFruit1(int[] fruits) {

        //滑动窗口：当窗口内出现第3种数字，缩小窗口将最左侧数字删掉
        //map 存放对应数字的个数，map.size代表不同数字的个数
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0, right = 0, res = Integer.MIN_VALUE;

        while (right < fruits.length) {

            //此处right++会影响下方right计算
            map.merge(fruits[right++], 1, Integer::sum);
            while (map.size() > 2) {
                map.merge(fruits[left], 1, (a, b) -> a - b);
                if (map.get(fruits[left]) == 0) map.remove(fruits[left]);
                left++;
            }//去除上方right++的影响，消去1
            res = Math.max(res, right - left);
        }
        return res;
    }

    class Solution904 {
        public int totalFruit(int[] fruits) {
            //滑动窗口，窗口内只有两种数，记录第1/2种数的起始下标n1,n2
            //记录第三种数前一直相同的数字的起始下标 e，遇到第三个数，n1跳到e
            int n1 = 0, n2 = 0, e = 0, res = 0;
            for (int i = 0; i < fruits.length; i++) {
                //遇到第三种数,n1跳到e（n1!=n2）,n2=当前下标
                if (fruits[i] != fruits[n1] && fruits[i] != fruits[n2]) {
                    if (n1 != n2) n1 = e;//如果相等，只需要更新n2(扩展窗口)
                    n2 = i;
                }
                res = Math.max(res, i - n1 + 1);
                if (fruits[i] != fruits[e]) e = i;//更新窗口的连续数字起始下标
            }
            return res;
        }
    }





    public String minWindow(String s, String t) {

        //滑动窗口：进窗字符数--，出窗++，是目标字符就计数
        //计数==t.len 说明集齐了，更新最小长度和结果字符串

        char[] chs = s.toCharArray(), cht = t.toCharArray();
        int lens = chs.length, lent = cht.length;
        //存放 t 中的字符数量(大写+小写 65-122)
        int[] nums = new int[123];
        for (char ch : cht) nums[ch]++;

        //count 对目标字符的收集进度进行计数
        int left = 0, right = 0, count = 0, len = Integer.MAX_VALUE;
        String res = "";

        while (right < lens) {
            //窗口移动，如果是t中的字符，-->=0;否则<0,最后要还原
            nums[chs[right]]--; //进窗--，出窗++
            //收集到一个字符，计数+1
            if (nums[chs[right]] >= 0) count++;

            //因为后面会还原，计数=长度时说明集齐了所有字符，缩小窗口
            while (count == lent) {
                //更新最小长度和结果
                if (len > right - left + 1) {
                    len = right - left + 1;
                    res = s.substring(left, right + 1);
                }
                //判断left是不是目标字符，要么==0(集齐)，要么<0(进窗口时-1了)
                if (nums[chs[left]] == 0) count--;
                nums[chs[left++]]++;//还原字符+1，窗口左边界+1
            }
            right++;
        }
        return res;
    }

    public int[][] generateMatrix1(int n) {

        //循环不变量：左闭右开，上右下左，奇数中间值单独填
        int loop = n / 2;//记录圈数，4和5一样是2圈，5单独中间赋值
        int offset = n - 1;//一个循环的遍历长度（边的长度）
        int startX = 0, startY = 0, count = 1;//count记录当前填入的数值
        int[][] res = new int[n][n];

        while (loop-- >= 0) {
            int i = startX;//当前列索引
            int j = startY;//当前行索引

            for (; i < startX + offset; i++) //上
                res[j][i] = count++;
            for (; j < startY + offset; j++) //右
                res[j][i] = count++;
            for (; i > startX; i--) //下
                res[j][i] = count++;
            for (; j > startY; j--) //右
                res[j][i] = count++;

            offset -= 2;//每圈长度-1
            startX++;
            startY++;
        }
        //注意不是res[n/2+1][n/2+1]
        if (n % 2 == 1) res[n / 2][n / 2] = count;
        return res;
    }

    class Solution59 {
        public int[][] generateMatrix(int n) {
            //确定角点，左闭右闭
            int[][] res = new int[n][n];
            int sx = 0, sy = 0, ex = n - 1, ey = n - 1;//x是列，y是行
            int count = 1;
            while (sx <= ex) {
                int curx = sx, cury = sy;
                //角点相遇说明有中心点，否则会错开
                if (sx == ex) res[sx][sy] = count;
                else {//上右下左打印,每次打印到最后一个位置之前
                    //顺序！上下动y，左右动x
                    while (cury < ey) res[curx][cury++] = count++;
                    while (curx < ex) res[curx++][cury] = count++;
                    while (cury > sy) res[curx][cury--] = count++;
                    while (curx > sx) res[curx--][cury] = count++;
                }
                sx++; sy++; ex--; ey--;
            }
            return res;
        }
    }

    class Solution {
        public List<Integer> luckyNumbers (int[][] matrix) {
            //每个值都唯一：每行最小值可以列出，每一列的最大值也可列出，求交集
            int m=matrix.length,n=matrix[0].length;
            ArrayList<Integer> res = new ArrayList<>();
            int[] arr1 = new int[m];//每行的最小值
            int[] arr2 = new int[n];//每列的最大值
            for (int i = 0; i <m; i++) {//行
                for (int j = 0; j <n ; j++) {//列
                    if(matrix[i][j]>arr2[j]) arr2[j]=matrix[i][j];
                    if(arr1[i]==0 || matrix[i][j]<arr1[i])arr1[i]=matrix[i][j];
                }
            }
            //求交集
            for (int i = 0; i <m ; i++) {
                for (int j = 0; j <n ; j++) {
                    if(arr1[i]==arr2[j]) res.add(arr1[i]);
                }
            }
            return res;
        }
    }


    public List<Integer> spiralOrder(int[][] matrix) {

        //循环不变量：确定对角点坐标，排除特俗情况，左闭右开，上右下左
        int sX = 0, sY = 0;//左上角坐标
        int eX = matrix[0].length - 1, eY = matrix.length - 1;//右下角坐标

        ArrayList<Integer> res = new ArrayList<>(eX * eY);

        //确定两角坐标后打印边框
        while (sX <= eX && sY <= eY) {
            //当前出发坐标点
            int curX = sX, curY = sY;

            if (sX == eX) {//特殊情况：横坐标相同，有且只有1列
                while (curY <= eY) res.add(matrix[curY++][sX]);
            } else if (sY == eY) {//特殊情况：只有1行
                while (curX <= eX) res.add(matrix[sY][curX++]);
            } else {//正常情况：打印边框，上右下左
                while (curX != eX) res.add(matrix[sY][curX++]);//上
                while (curY != eY) res.add(matrix[curY++][curX]);//右
                while (curX != sX) res.add(matrix[curY][curX--]);//下
                while (curY != sY) res.add(matrix[curY--][sX]);//左
            }
            sX++;
            eX--;
            sY++;
            eY--;//更新边框角点坐标
        }
        return res;
    }

    public void rotate(int[][] matrix) {

        //循环不变量：按角点将边分为len-1组，逆序交换
        int sX = 0, sY = 0; //左上角点
        int eX = matrix.length - 1, eY = matrix.length - 1;//右下角点

        while (sX < eX) { //等于时只有最中间的元素，不用旋转
            //分组，0,eX-sX 左闭右开 组数 = 当前边框长度-1
            for (int group = 0; group < eX - sX; group++) {
                //记录上行的位置点值
                int temp = matrix[sY][sX + group];
                //上边 = 左边
                matrix[sY][sX + group] = matrix[eY - group][sX];
                //左边 = 下边
                matrix[eY - group][sX] = matrix[eY][eX - group];
                //下边 = 右边
                matrix[eY][eX - group] = matrix[sY + group][eX];
                //右边 = 上边
                matrix[sY + group][eX] = temp;
            }
            //更新边框角点坐标
            sX++;
            sY++;
            eX--;
            eY--;
        }
    }

    public static int[] findDiagonalOrder(int[][] mat) {

        //循环不变量：三个点 对角线两个点，终点
        //对角线上点先右后下;对角线下点先下后右;直到终点
        int upX = 0, upY = 0;//对角线上点
        int dnX = 0, dnY = 0;//对角线下点
        int cols = mat[0].length, rows = mat.length;//列数、行数
        boolean fromUp = false;//是否从上点出发
        int[] res = new int[cols * rows];
        int count = 0;//计数当前数组中的位置

        //终止条件：上对角线点与终点重合
        while (upY < rows) {
            //打印
            if (fromUp) {//从上到下
                int curX = upX, curY = upY;
                while (curY <= dnY) res[count++] = mat[curY++][curX--];
            } else { //从下到上
                int curX = dnX, curY = dnY;
                while (curY >= upY) res[count++] = mat[curY--][curX++];
            }
            //更新对角线的点坐标
            // 上点：列走完，向下走，注意先行后列，否则列更新会影响到后续判断
            upY = upX == cols - 1 ? upY + 1 : upY;
            upX = upX == cols - 1 ? upX : upX + 1;
            // 下点:行走完，向右走
            dnX = dnY == rows - 1 ? dnX + 1 : dnX;
            dnY = dnY == rows - 1 ? dnY : dnY + 1;
            // 变换打印方向
            fromUp = !fromUp;
        }
        return res;
    }

    public boolean searchMatrix(int[][] matrix, int target) {

        //从第一行最右侧开始，大于目标向左走，小于目标向下走
        int rows = matrix.length;
        int cols = matrix[0].length;
        int row = 0, col = cols - 1;//遍历起始点

        //最下到最后一行，最左到0
        while (row < rows && col >= 0) {
            if (matrix[row][col] > target) col--;
            else if (matrix[row][col] < target) row++;
            else return true;
        }
        return false;
    }


    public static void main(String[] args) {
        findDiagonalOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }

    public int firstGreaterThan(int[] nums, int target) {
        //二分法：该数大于6 && （是第一个数 || 前一个数小于6）
        if (nums == null || nums.length == 0) return -1;

        int mid, left = 0, right = nums.length - 1;
        // 预留两个元素单独判断：可避免只有两个数时的死循环
        // 如 0 1 mid=0 下一轮 还是0 1 不变，mid只会偏向一边
        while (left + 1 < right) {
            mid = left + ((right - left) >> 1);

            // 发现边界
            if (nums[mid] > target
                    && (mid == 0 || nums[mid - 1] <= target))
                return mid;
                // 大于目标值且不是边界 向左搜索
            else if (nums[mid] > target) right = mid - 1;
                // 等于或者小于目标值 向右搜索
            else left = mid + 1;
        }
        // 单独判断两个元素，left 可能等于 right
        if (nums[left] > target) return left;
        if (nums[right] > target) return right;
        return -1;
    }


}
