package com.csu.dataStructure.SegmentTree;

//左神体系班32
public class Code01_SegmentTree {

    public static class SegmentTree {
        private int MAXN; //新数组长度=旧数组长度+1，为了下标从1开始
        private int[] arr; //存放值的新数组，下标从1开始
        private int[] sum; //某范围的 累加和
        private int[] lazy; //某范围拦截的向下的 懒累加值
        private int[] change; //某范围拦截的向下的 懒更新值
        private boolean[] update; //消除change的歧义(0)，标记是否需要更新

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN]; // arr[0] 不用 从1开始使用
            for (int i = 1; i < MAXN; i++) arr[i] = origin[i - 1];

            sum = new int[MAXN << 2]; //长度*4
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
        }

        private void pushUp(int rt) {
            //向上更新值，父节点值(区间和)=左+右
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }


        private void pushDown(int rt, int ln, int rn) {//向下更新值
            //先判断是否需要更新(直接变)，再判断是否需要累加(累加“懒更新值”)
            int left = rt << 1, right = rt << 1 | 1;
            if (update[rt]) {//当前节点“懒更新”任务，向下分发
                update[left] = update[right] = true;//左右子节点标记“需更新”
                change[left] = change[right] = change[rt];//父任务向下分发
                lazy[left] = lazy[right] = 0;//不需要再累加，直接变
                sum[left] = change[left] * ln;//更新节点范围更新后的值
                sum[right] = change[right] * rn;
                update[rt] = false;//当前节点任务分发完成，不需要更新
            }
            if (lazy[rt] != 0) {//当前节点“懒累加”任务，向下分发
                lazy[left] += lazy[rt];//左待更新值+上层分发的值
                lazy[right] += lazy[rt];//右待更新值+上层分发的值
                sum[left] += lazy[rt] * ln;//更新左子节点sum值
                sum[right] += lazy[rt] * rn;//更新右子节点sum值
                lazy[rt] = 0;//分发完成
            }
        }


        public void build(int l, int r, int rt) {
            // arr范围l-r 左闭右闭,构建线段树，rt为sum中表示该范围的下标

            if (l == r) { //到达叶子节点，赋值
                sum[rt] = arr[l];
                return;
            }
            //非叶子节点 向下递归构建
            int mid = (l + r) >> 1;//找到范围中点，向左右递归构建
            build(l, mid, 2*rt);//左子树，范围下标为 2*rt
            build(mid + 1, r, 2*rt+ 1);//右子树

            pushUp(rt);//后序遍历，向上给根节点赋值，左和+右和
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            //任务范围L~R, C为更新值；节点范围l-r，rt为sum中范围节点下标
            if (L <= l && r <= R) {//当前节点范围是任务范围的子集，懒更新
                update[rt] = true; //标记需要更新
                change[rt] = C; //需要更新的值
                sum[rt] = C * (r - l + 1); //更新当前节点sum值
                lazy[rt] = 0;//当前范围都变成C,不用向下更新之前的“懒更新值”了
                return;
            }
            int mid = (l + r) >> 1;//把当前层任务向下分发一层
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) update(L, R, C, l, mid, rt << 1);//左半边更新
            if (R > mid) update(L, R, C, mid + 1, r, rt << 1 | 1);
            pushUp(rt); //更新当前节点值
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            //任务范围L~R, C为累加值；节点范围l-r，rt为sum中范围节点下标
            if (L <= l && r <= R) {//当前节点范围是任务范围的子集，懒更新
                sum[rt] += C * (r - l + 1);//被拦到当前节点，先更新当前和
                lazy[rt] += C; //更新当前节点懒累加
                return;
            }
            // 范围更小，向下分发
            int mid = (l + r) >> 1;//找中点
            pushDown(rt, mid - l + 1, r - mid);//向下更新任务
            // 向左右子树递归累加任务
            if (L <= mid) add(L, R, C, l, mid, rt << 1);
            if (R > mid) add(L, R, C, mid + 1, r, rt << 1 | 1);

            pushUp(rt);//向下分发完，更新当前节点sum值
        }


        public long query(int L, int R, int l, int r, int rt) {
            // 查询范围L-R 节点范围l-r
            if (L <= l && r <= R) { //从上到下查，先返回的就是最大子集结果
                return sum[rt];
            }
            int mid = (l + r) >> 1;//查询范围更小，向下分发
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0;
            if (L <= mid) {//查下一层左
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {//查下一层右
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = {2, 1, 1, 2, 3, 4, 5};
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

}
