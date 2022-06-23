package com.csu.doublePoint;

import java.util.*;

public class doublePointAll {


    //双指针
    public void reverseString(char[] s) {

        int left = 0;
        int right = s.length - 1;

        //左右指针位置元素交换
        while (left < right) {
            s[left] ^= s[right];
            s[right] ^= s[left];
            s[left++] ^= s[right--];
        }
    }


    public String replaceSpace(String s) {
        //双指针：第一遍统计替换后的长度，第二遍填入新数组
        int p1 = 0, p2 = 0, sum = 0;//记录新数组的长度

        while (p1 < s.length()) {
            if (s.charAt(p1) == ' ') sum += 2;
            sum += 1;
            p1++;
        }

        //创建新的长度为替换后大小的数组
        char[] res = new char[sum];

        p1 = 0; //第二遍填入新数组
        while (p1 < s.length()) {
            if (s.charAt(p1) == ' ') {
                res[p2++] = '%';
                res[p2++] = '2';
                res[p2] = '0';
            } else {
                res[p2] = s.charAt(p1);
            }
            p1++;
            p2++;
        }
        return new String(res);
    }

    public String reverseWords(String s) {

        //双指针+快慢指针：快指针如果不是多余的空格，把值赋给满指针位置
        char[] s1 = s.toCharArray();

        //慢指针指向去除多余空格后，最后的数组长度
        int slow = 0, fast = 0;//快指针遍历字符串
        //先删开头的空格
        while (fast < s1.length && s1[fast] == ' ') fast++;
        //再删中间部分空格
        while (fast < s1.length) {
            //慢指针移动：不是空格 || 是空格但前一个不是空格
            if (fast > 0 && s1[fast] == s1[fast - 1] && s1[fast] == ' ')
                fast++;
            else s1[slow++] = s1[fast++];//此处导致slow多走了一步，要-1
        }
        //再删最后的空格,注意slow此时是长度，最后一个元素下标是slow-1
        if (slow > 0 && s1[slow - 1] == ' ') slow--;

        //整体翻转
        reverse(s1, 0, slow - 1);
        //遍历0-slow-1的字符串，逐个翻转还原单词
        int left = 0, right = 0;
        while (left < slow) {//左右分别是一个单词的左右边界
            while (right < slow && s1[right] != ' ') right++;
            int temp = right - 1;//因为此时right指向的是空格，故向左移一位
            reverse(s1, left, temp);
            left = right = ++right;
        }
        return new String(s1, 0, slow);
    }

    static void reverse(char[] s, int i, int j) {
        while (i < j) {
            s[i] ^= s[j];
            s[j] ^= s[i];
            s[i++] ^= s[j--];
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {

        //多指针：返回的是数值而不是下标，故可以排序，从i+1到len-1
        //和小：左指针右移 和大：右指针左移 直到两个指针相遇返回
        int i, left, right, len = nums.length, sum;

        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);
        for (i = 0; i < len - 2; i++) {
            left = i + 1;
            right = len - 1;

            if (nums[i] > 0) return res;//起始点大于0，没有继续的意义
            if (i > 0 && nums[i] == nums[i - 1]) continue;//避免结果重复

            while (left < right) {
                sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {//加入结果，注意 -2 0 0 2 2 要先把重复的right和left过掉
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    res.add(Arrays.asList(nums[i], nums[left++], nums[right--]));
                } else if (sum > 0) right--;
                else left++;
            }
        }
        return res;
    }

    public int lengthOfLongestSubstring(String s) {
        //滑动窗口：保证窗口内的每种字符只有一个
        //遇到每个字符都比较下左边界和该字符上次出现的位置
        int[] nums = new int[256];//字符上次出现的位置
        Arrays.fill(nums, -1);//一开始还没出现过

        int res = 0, start = -1;//窗口左边界

        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i);//字符转int
            //nums[ch[i]]大说明窗口内有重复值，start=重复值+1
            start = Math.max(start, nums[index] + 1);
            res = Math.max(res, i - start + 1);
            nums[index] = i;
        }
        return res;
    }

    public int threeSumClosest(int[] nums, int target) {
        //双指针：升序排序，正序遍历，双指针搜索
        Arrays.sort(nums);
        int len = nums.length, left, right;

        int res = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < len - 2; i++) {
            left = i + 1; right = len - 1;//左右指针

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];//当前和
                if (Math.abs(sum - target) < Math.abs(res - target)) res = sum;
                //数组升序，前两个大于tar，后两个小于tar，都没必要向后搜，更新并返回
                if (nums[i] + nums[left] + nums[left + 1] > target) {
                    int cur = nums[left] + nums[left + 1] + nums[i];
                    if (Math.abs(cur - target) < Math.abs(res - target)) res = cur;
                    break;
                } else if (nums[i] + nums[right] + nums[right - 1] < target) {
                    int cur = nums[right] + nums[right - 1] + nums[i];
                    if (Math.abs(cur - target) < Math.abs(res - target)) res = cur;
                    break;
                }
                if (sum > target) {//过滤重复的数字
                    right--;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < target) {
                    left++;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                } else return target;//刚好是目标值，直接返回
            }
            //过滤重复的nums[i],注意先使用后过滤
            while (i < len - 2 && nums[i] == nums[i + 1]) i++;
        }
        return res;
    }


    public int[] dailyTemperatures(int[] temperatures) {
        //双指针：时间复杂度 n^2
        int fast = 0, slow = 0, len = temperatures.length;
        int[] arr = new int[len];

        for (; slow < len; slow++) {
            fast = slow;
            while (fast < len && temperatures[fast] <= temperatures[slow]) {
                fast++;
            }
            if (fast == len) continue;
            arr[slow] = fast - slow;
        }
        return arr;
    }

    public int findDuplicate(int[] nums) {
        //快慢指针：把数组下标和元素建立映射，存在重复数会在重复数入环
        //nums = [1,3,4,2,2]，链表：1-3-2-4-2-4-2...入环点2，即重复数
        int fast = 0, slow = 0, len = nums.length;
        while (fast != slow || fast == 0) {//先找到入环点
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        fast = 0;
        while (fast != slow) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return fast;//返回入环点
    }


    public List<List<Integer>> fourSum(int[] nums, int target) {
        //多指针：返回的是数值而不是下标，故可以排序，从i+1到len-1
        //和小：左指针右移 和大：右指针左移 直到两个指针相遇返回
        int i, j, left, right, len = nums.length, sum;

        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);
        for (i = 0; i < len - 3; i++) { // i,j,left,right
            //区别于三数之和，此处不能有 如果目标值特别小的负值，直接返回了
            //if(nums[i]>target) return res;
            if (i > 0 && nums[i] == nums[i - 1]) continue;//避免结果重复

            for (j = i + 1; j < len - 2; j++) {
                left = j + 1;
                right = len - 1;

                if (j > i + 1 && nums[j] == nums[j - 1]) continue;//避免结果重复

                while (left < right) {
                    sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {//加入结果，先把重复的right和left过掉
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        res.add(Arrays.asList(
                                nums[i], nums[j], nums[left++], nums[right--]));
                    } else if (sum > target) right--;
                    else left++;
                }
            }
        }
        return res;
    }


    public int removeElement(int[] nums, int val) {

        int len = nums.length;
        int fast = 0;
        int slow = 0;//此处慢指针相当于记录非目标元素的数量

        //快指针遍历所有元素
        while (fast < len) {
            //不是目标元素，慢指针也要+1，并把非目标元素前移
            if (nums[fast] != val) nums[slow++] = nums[fast];
            fast++;//往前遍历
        }
        return slow;
    }


}

