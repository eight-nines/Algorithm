package com.csu.algorithm.Bfs;

import java.util.*;

public class OpenLock {






    public int openLock(String[] deadends, String target) {
        // 记录已经穷举过的和死亡密码，防止走回头路（要跳过的密码）
        Set<String> visited = new HashSet<>(Arrays.asList(deadends));
        // BFS队列
        Queue<String> queue = new LinkedList<>();
        // 避免死亡密码中包含起点
        if(visited.contains("0000")) return -1;
        // 队列 和 记录 中加入起点
        queue.offer("0000");visited.add("0000");
        // 从起点开始启动广度优先搜索
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            /* 将当前队列中的所有节点向周围扩散 */
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();

                /* 判断是否到达终点 */
                if (cur.equals(target)) return step;

                /* 将一个节点的未遍历相邻节点加入队列 */
                for (int j = 0; j < 4; j++) {
                    char[] ch = cur.toCharArray();

                    //当前数字+1
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) {
                        queue.offer(up);visited.add(up);
                    }
                    //当前数字-1
                    String down = minusOne(cur, j);
                    if (!visited.contains(down)) {
                        queue.offer(down);visited.add(down);
                    }
                }
            }
            /* 在这里增加步数 */
            step++;
        }
        // 如果穷举完都没找到目标密码，那就是找不到了
        return -1;
    }

    // 将 s[j] 向上拨动一次
    String plusOne(String s, int j) {
        char[] ch = s.toCharArray();
        // if (ch[j] == '9') ch[j] = '0';
        // else ch[j] += 1;
        ch[j] = (char) ((ch[j] - '0' +1)%10 + '0');
        return new String(ch);
    }
    // 将 s[i] 向下拨动一次
    String minusOne(String s, int j) {
        char[] ch = s.toCharArray();
        // if (ch[j] == '0') ch[j] = '9';
        // else ch[j] -= 1;
        ch[j] = (char) ((ch[j] - '0' -1+10)%10 + '0');
        return new String(ch);
    }


    public static void main(String[] args) {
        System.out.println(-1%10);
    }

    //双向bfs优化
    public int openLock1(String[] deadends, String target) {

        // 用集合不用队列，可以快速判断元素是否存在
        Set<String> q1 = new HashSet<>();
        Set<String> q2 = new HashSet<>();
        Set<String> visited = new HashSet<>(Arrays.asList(deadends));
        if(visited.contains("0000")) return -1;

        int step = 0;
        q1.add("0000");
        q2.add(target);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.size() > q2.size()) {
                // 交换 q1 和 q2 , 每次遍历较小的集合
                Set<String> temp = q1;
                q1 = q2;
                q2 = temp;
            }
            // 哈希集合在遍历的过程中不能修改，用 temp 存储扩散结果
            Set<String> temp = new HashSet<>();

            /* 将 q1 中的所有节点向周围扩散 */
            for (String cur : q1) {
                /* 判断是否到达终点 */
                if (q2.contains(cur)) return step;
                visited.add(cur);

                /* 将一个节点的未遍历相邻节点加入集合 */
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) temp.add(up);
                    String down = minusOne(cur, j);
                    if (!visited.contains(down)) temp.add(down);
                }
            }
            /* 在这里增加步数 */
            step++;
            // 交换索引，即扩散另一端，此处扩散完q1相当于temp
            q1 = q2; q2 = temp;
        }
        return -1;
    }


}
