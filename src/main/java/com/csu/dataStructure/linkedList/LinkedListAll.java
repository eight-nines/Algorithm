package com.csu.dataStructure.linkedList;

import com.csu.algorithm.UnionFind.ClosedIsland;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedListAll {


    public static class ListNode {

        Integer val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(Integer val) {
            this.val = val;
        }

        public ListNode(Integer val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode removeElements(ListNode head, int val) {
        //设置虚拟头节点，让遍历操作前后（原头和非原头）保持一致
        ListNode root = new ListNode(); //虚拟头结点
        root.next = head; //连接虚拟头结点和原头节点
        ListNode cur = root; //用于遍历的节点

        while (cur.next != null) {
            //虚拟头结点不会等于目标值
            if (cur.next.val == val) cur.next = cur.next.next;
            else cur = cur.next;//向后遍历
        }
        return root.next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {

        //虚拟头节点+快慢指针：f先走n+1步，当f为空，s在删除节点的前节点
        ListNode root = new ListNode();
        root.next = head;
        ListNode fast = root, slow = root; //快慢指针

        //快指针 先走 n+1 步
        for (int i = 0; i <= n; i++) fast = fast.next;

        //一起走到 fast==null ,此时slow.next为目标节点
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        //删除目标节点
        slow.next = slow.next.next;

        return root.next;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //双指针：因为交点后节点全部重合，长链表指针 与 短链表长度 对齐
        ListNode curA = headA, curB = headB; //双指针

        //初始化链表长度
        int lenA = 0, lenB = 0;
        while (curA != null) { //初始化a长度
            lenA++;
            curA = curA.next;
        }
        while (curB != null) { //初始化b长度
            lenB++;
            curB = curB.next;
        }

        //重置指针，对齐链表
        curA = headA;
        curB = headB;
        if (lenA > lenB) { //a是长链表
            for (int i = 0; i < lenA - lenB; i++)
                curA = curA.next;
        } else if (lenB > lenA) {
            for (int i = 0; i < lenB - lenA; i++)
                curB = curB.next;
        }

        //开始遍历
        while (curA != null) {
            if (curA == curB) return curA;
            curA = curA.next;
            curB = curB.next;
        }
        return null;
    }

    public boolean hasCycle(ListNode head) {
        //快慢指针：快指针+2，慢指针+1，若有环一定相遇
        ListNode fast = head, slow = head;

        //保证快指针有的走
        while (fast != null && fast.next != null) {
            //先走再判断
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }

    public ListNode detectCycle(ListNode head) {
        //快慢指针：快指针+2，慢指针+1
        //若相遇，快指针走1步，慢指针回头结点走1步，再相遇就是入环点
        ListNode fast = head, slow = head;

        //保证快指针有的走
        while (fast != null && fast.next != null) {
            //先走再判断
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) { //确定有环
                slow = head; //慢指针回头结点
                while (fast != slow) { //再次相遇
                    fast = fast.next;
                    slow = slow.next;
                } //返回相遇点
                return fast;
            }
        }
        return null;
    }

    public static boolean isPalindrome1(ListNode head) {
        //快慢指针+左右指针：找到上中点，下半段翻转，左右指针依次对比
        ListNode fast = head, slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next; //fast->end
            slow = slow.next; //slow->mid
        }

        //此时slow指向上中点，翻转下半段链表
        fast = slow.next; //以fast为指针开始翻转
        slow.next = null; //截断上半段链表,同时作为pre指针
        ListNode temp; //用于记录下一个节点
        //翻转后半段 pre cur temp -> slow fast temp
        while (fast != null) {
            temp = fast.next;
            fast.next = slow;
            //更新指针
            slow = fast; //注意顺序，先更新前指针 再更新中间指针
            fast = temp;
        }
        //翻转完成，此时slow指向原链表的末尾(fast=null) 对比即可
        fast = head; //head还有用，重复利用fast指针
        temp = slow; //记录slow的位置，用于后续恢复
        boolean res = true; //不能直接返回，需要恢复链表
        while (fast != null && slow != null) {
            if (fast.val != slow.val) {
                res = false;
                break;
            }
            fast = fast.next;
            slow = slow.next;
        }
        //恢复链表，再翻转
        slow = temp.next; //temp为右1，指向右2，翻转
        temp.next = null; //右1 恢复指向null
        while (slow != null) { //pre cur temp -> temp slow fast
            fast = slow.next;
            slow.next = temp;
            //更新指针
            temp = slow;
            slow = fast; //左移
        }
        return res;
    }


    public boolean isPalindrome(ListNode head) {

        //快慢指针+左右指针：找上中点，逆序下半部分，左右指针比较
        ListNode fast = head, slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next; //fast->end
            slow = slow.next; //slow->mid
        }

        // slow fast temp
        ListNode temp;
        fast = slow.next;
        slow.next = null;

        while (fast != null) {
            temp = fast.next;
            fast.next = slow;

            slow = fast;
            fast = temp;
        }

        //slow指向最右侧
        while (head != null && slow != null) {
            if (head.val != slow.val) return false;
            head = head.next;
            slow = slow.next;
        }
        return true;
    }


    public ListNode swapPairs(ListNode head) {

        //虚拟头节点：每次交换后两个节点 cur next1 next2 next3
        //cur指向next2, next2指向1, 1指向3, cur 2 1 3
        ListNode root = new ListNode();
        root.next = head;
        ListNode cur = root;//用于遍历
        ListNode next1, next2, next3;//当前节点的后面节点的序号，用于记录

        //每次交换后面两个节点，所以都不能为空
        while (cur.next != null && cur.next.next != null) {
            next1 = cur.next;
            next2 = next1.next;
            next3 = next2.next;

            cur.next = next2; //cur指向next2
            next2.next = next1; //next2指向1
            next1.next = next3; //1指向3

            cur = next1;
        }
        return root.next;
    }

    // 中点或上中点
    public ListNode searchMid(ListNode head) {
        //上中点：快2，慢1 慢指针就是中点 或 上中点
        ListNode fast = head, slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 中、上中的前一个
    public ListNode searchMid1(ListNode head) {
        //上中点的前一个：快指针先跳一次，快2，慢1
        ListNode fast = head.next.next, slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 中点或下中点
    public ListNode searchMid2(ListNode head) {
        //下中点：从第二个节点起跳 快2，慢1
        ListNode fast = head.next, slow = head.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 中点或下中点的前一个
    public ListNode searchMid3(ListNode head) {
        //下中点：快指针从第二个节点起跳 快2，慢1
        ListNode fast = head.next, slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static ListNode partition(ListNode head, int x) {
        // 6指针，3链表：小 等 大 最后合并
        ListNode sH, sT, eH, eT, bH, bT, next;
        sH = sT = eH = eT = bH = bT = null;

        while (head != null) {
            next = head.next;//记录下个节点
            head.next = null;//取出当前节点

            if (head.val < x) { //小
                if (sH == null) sH = sT = head;
                else { //连上当前节点，更新链表尾节点
                    sT.next = head;
                    sT = head;
                }
            } else if (head.val == x) { //相等
                if (eH == null) eH = eT = head;
                else {
                    eT.next = head;
                    eT = head;
                }
            } else { //大于
                if (bH == null) bH = bT = head;
                else {
                    bT.next = head;
                    bT = head;
                }
            }
            //更新当前节点
            head = next;
        }
        //链表相连,注意：此时三个链表的
        //先连后两个,中间链表存在，大头连中尾，不存在，中头等于大头
        if (eT != null) eT.next = bH;
        else eH = bH;
        //连前两个,小链表存在，中头连小尾，不存在，小头等于中头
        if (sT != null) sT.next = eH;
        else sH = eH;

        return sH;
    }

    public ListNode partition1(ListNode head, int x) {
        // 4指针，2链表：小 等大 最后合并
        ListNode sH, sT, eH, eT, next;
        sH = sT = eH = eT = null;

        while (head != null) {
            next = head.next;//记录下个节点
            head.next = null;//取出当前节点

            if (head.val < x) { //小
                if (sH == null) sH = sT = head;
                else { //连上当前节点，更新链表尾节点
                    sT.next = head;
                    sT = head;
                }
            } else { //相等或大于
                if (eH == null) eH = eT = head;
                else {
                    eT.next = head;
                    eT = head;
                }
            }
            //更新当前节点
            head = next;
        }
        //链表相连,注意：此时三个链表的
        //小链表存在，中头连小尾，不存在，小头等于中头
        if (sT != null) sT.next = eH;
        else sH = eH;

        return sH;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }


    public Node copyRandomList1(Node head) {

        //map：所有节点存入map,再遍历一遍原链表将新链表连起来
        HashMap<Node, Node> map = new HashMap<>();

        Node cur = head;
        //依次将节点存入map中，只初始化 值
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        //将新创建的节点按原链表顺序连接
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }


    public Node copyRandomList(Node head) {
        //非map:将新节点依次插入到原节点的后一个位置
        //新节点rand 是 原节点rand 的next ;再遍历一遍将新旧链表分开
        if (head == null) return null;
        Node cur = head, next;
        //依次插入新节点
        while (cur != null) { //cur next -> cur new next
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }

        //新节点的rand 指向 原节点rand的下一个节点（新rand）
        cur = head;
        Node curNew;//新节点
        while (cur != null) {
            curNew = cur.next;
            next = cur.next.next;//原节点的下一个原节点
            //如果原节点的rand指向null,新的也指向null
            curNew.random = cur.random == null ? null : cur.random.next;
            cur = next;
        }

        //next指针：分割新旧链表
        cur = head;
        Node res = head.next;
        while (cur != null) {
            next = cur.next.next;
            curNew = cur.next;
            cur.next = next;
            //防止空指针异常
            curNew.next = next == null ? null : next.next;
            cur = next;
        }

        return res;
    }

    public ListNode getIntersectNode(ListNode head1, ListNode head2) {

        //两链表相交，要么都无环，要么都有环，无环对齐后比较；有环遍历环比较入环点
        ListNode loop1 = getLoopNode(head1), loop2 = getLoopNode(head2);

        //无环对齐比较
        if (loop1 == null && loop2 == null) return noLoop(head1, head2);

        //有环遍历环比较入环点
        if (loop1 != null && loop2 != null) return haveLoop(head1, head2, loop1, loop2);

        return null;
    }

    //获取入环点
    public static ListNode getLoopNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {//有环
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }

    //无环对齐比较
    public static ListNode noLoop(ListNode head1, ListNode head2) {
        int len1 = 0, len2 = 0;
        ListNode cur1 = head1, cur2 = head2;
        while (cur1 != null) {
            len1++;
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            len2++;
            cur2 = cur2.next;
        }
        cur1 = head1;
        cur2 = head2;
        if (len1 > len2) {
            while (len1 > len2) {
                cur1 = cur1.next;
                len1--;
            }
        } else {
            while (len1 < len2) {
                cur2 = cur2.next;
                len2--;
            }
        }
        while (cur1 != null) {
            if (cur1 == cur2) return cur1;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return null;
    }

    //有环遍历环比较入环点
    public static ListNode haveLoop(ListNode head1, ListNode head2,
                                    ListNode loop1, ListNode loop2) {

        //如果相交：1、入环点不同，环相同；2、入环点相同，入环点之前相交
        ListNode cur1 = head1, cur2 = head2;

        //第二种情况相当于之前的 无环求交点
        if (loop1 == loop2) {
            int len1 = 0, len2 = 0;
            while (cur1 != loop1) {
                len1++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                len2++;
                cur2 = cur2.next;
            }
            cur1 = head1;
            cur2 = head2;
            if (len1 > len2) {
                while (len1 > len2) {
                    cur1 = cur1.next;
                    len1--;
                }
            } else {
                while (len1 < len2) {
                    cur2 = cur2.next;
                    len2--;
                }
            }
            while (cur1 != null) {
                if (cur1 == cur2) return cur1;
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return null;
        }

        //情况1：loop1遍历一遍环,y遇到loop2相交，否则不相交
        cur1 = loop1.next;
        while (cur1 != loop1) {
            if (cur1 == cur2) return cur1;
            cur1 = cur1.next;
        }
        return null;
    }

    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        //遍历两遍链表:第一遍遍历自己，到头返回遍历对方（第二遍）
        //两者在交点或各自的终点前的总步长是相同的，所以一定可以在交点/null值相遇
        ListNode p1 = headA, p2 = headB;
        while (p1 != p2) { //3,4 -> 7,7 两遍遍历两者走的总步数相同
            //走到自己的终点，就返回遍历对方
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }
        return p1;//要么是交点，要么是null
    }

    class Solution {
        public ListNode sortList(ListNode head) {
            //归并排序：快慢指针找中点，合并时使用虚拟头结点
            return sort(head);
        }

        public ListNode sort(ListNode head) {
            //返回排完序的头结点，边界保证有至少两个节点
            if (head == null || head.next == null) return head;

            ListNode slow = head, fast = head;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;//中点
                fast = fast.next.next;
            }
            ListNode right = slow.next;//以中点分割链表,左闭右闭
            slow.next = null;
            ListNode left = sort(head);
            right = sort(right);

            return merge(left, right);//合并两段链表
        }

        private ListNode merge(ListNode left, ListNode right) {
            //返回两段链表合并后的头节点
            ListNode dummyHead = new ListNode();
            ListNode cur = dummyHead;
            while (left != null && right != null) {
                if (left.val <= right.val) {//左边小
                    cur.next = left;
                    left = left.next;
                } else {
                    cur.next = right;
                    right = right.next;
                }
                cur = cur.next;
            }
            if (left != null) cur.next = left;
            if (right != null) cur.next = right;
            return dummyHead.next;
        }
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        //虚拟头节点: 创建新链表统计节点值
        ListNode dummyHead = new ListNode();

        ListNode cur = dummyHead;
        int more = 0;//当前进位 节点值
        while (l1 != null || l2 != null || more != 0) {
            if (l1 != null) {
                more += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                more += l2.val;
                l2 = l2.next;
            }
            cur.next = new ListNode(more % 10);
            more /= 10;
            cur = cur.next;
        }
        return dummyHead.next;
    }

    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        //非递归+虚拟头结点:比较并赋值
        ListNode dummyHead = new ListNode();
        ListNode cur = dummyHead, temp;

        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                temp = list2.next;
                cur.next = list2;
                list2 = temp;
            } else {
                temp = list1.next;
                cur.next = list1;
                list1 = temp;
            }
            cur = cur.next;
        }
        //有一个链表不为空，直接连上即可
        if (list1 != null) cur.next = list1;
        else cur.next = list2;

        return dummyHead.next;
    }






    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //递归连接：1.获取当前节点 2.连接当前节点的下一个节点
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode root = list1.val > list2.val ? list2 : list1;
        root.next = mergeTwoLists(root.next,
                list1.val > list2.val ? list1 : list2);

        return root;
    }


    class Solution1 {
        public ListNode mergeKLists(ListNode[] lists) {
            //分治+归并：把列表不断二分，每次合并两个链表
            if (lists.length == 0) return null;
            return merge(lists, 0, lists.length - 1);
        }

        //分治，并返回当前范围链表数组合并后的头结点
        private ListNode merge(ListNode[] lists, int l, int r) {
            if (l == r) return lists[l];//返回当前链表头结点

            int mid = l + (r - l) / 2;//找到中点，拆分并分别合并两端链表
            ListNode left = merge(lists, l, mid);//左侧合并后的头结点
            ListNode right = merge(lists, mid + 1, r);//右侧合并后的头结点

            return mergeSort(left, right);
        }

        //合并两个链表，并返回合并后的头结点
        private ListNode mergeSort(ListNode l, ListNode r) {
            if (l == null) return r;
            if (r == null) return l;

            if (l.val < r.val) {
                l.next = mergeSort(l.next, r);
                return l;
            } else {
                r.next = mergeSort(l, r.next);
                return r;
            }
        }
    }



    public ListNode rotateRight(ListNode head, int k) {
        //找到倒数第k个节点，就是新头，原头节点连到原尾上 新头-原尾-原头
        if (head == null || head.next == null) return head;
        ListNode cur = head, fast = head, slow = head;
        int len = 0;//统计链表长度
        while (cur != null) {
            cur = cur.next;
            len++;
        }
        k %= len;//k大于len时多次循环，k:倒数第几个（旋转的次数）
        if (k == 0) return head;//相当于不用旋转，直接返回即可

        while (k-- != 0) fast = fast.next;//fast先跳k步

        while (fast.next != null) {//f为null,slow指向倒数第k个节点
            fast = fast.next; //倒数第一个节点
            slow = slow.next; //倒数第k+1个节点
        }
        ListNode newHead = slow.next; //新头
        slow.next = null; //截断前半段

        fast.next = head;//连接：原尾+原头
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);

        partition(head, 3);
    }

    public ListNode reverseList(ListNode head) {

        //双指针：pre指向null,cur指向head,cur指向pre
        ListNode pre = null, cur = head, tem;

        //终止条件，遍历完所有节点
        while (cur != null) {
            tem = cur.next; //记录下一个节点
            cur.next = pre;
            pre = cur;
            cur = tem;
        }
        return pre;
    }

    class LRUCache extends LinkedHashMap<Integer, Integer> {
        //继承LinkedHashMap 重写 removeEldestEntry方法
        int capacity;

        public LRUCache(int capacity) {
            //初始化 map,且设置按访问排序
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get(int key) {//不存在时返回-1
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return this.size() > capacity;//元素大于容量时，删除最后一个节点
        }
    }


}
