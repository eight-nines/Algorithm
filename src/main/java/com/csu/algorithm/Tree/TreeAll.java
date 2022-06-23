package com.csu.algorithm.Tree;

import sun.reflect.generics.tree.Tree;

import java.util.*;

public class TreeAll {


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


    public TreeNode invertTree(TreeNode root) {

        //前序遍历：交换每个节点的左右子树
        if (root == null) return null;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }


    class Solution21 {
        int res = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            //后序遍历：每个节点处返回自己为终点的最大路径和，左右大于0才加
            dfs(root);
            return res;
        }

        int dfs(TreeNode root) {
            if (root == null) return 0;

            //左右两侧的路径和，只有大于0才加
            int left = Math.max(dfs(root.left), 0);
            int right = Math.max(dfs(root.right), 0);

            res = Math.max(res, root.val + left + right);

            //当前节点代表的分支的路径和（左右分支选最大的）
            return Math.max(left, right) + root.val;
        }
    }


    class Solution22 {
        int res = 0;

        public int longestUnivaluePath(TreeNode root) {
            //后序遍历：两条路径选最长的一条返回，只有子节点与自己相同才相加，不然为0
            dfs(root);
            return res;
        }

        //以root为终点,与root同值的路径长,注意是边数,不是节点数
        int dfs(TreeNode root) {
            if (root == null) return 0;

            int left = dfs(root.left);
            int right = dfs(root.right);
            int lenL = 0, lenR = 0;//最终真实的路径长，考虑子节点与root不同

            if (root.left != null && root.left.val == root.val) lenL = left + 1;
            if (root.right != null && root.right.val == root.val) lenR = right + 1;

            res = Math.max(res, lenL + lenR);
            return Math.max(lenL, lenR);
        }
    }


    class Solution {
        //<前缀和，出现次数> 201,01前缀和都是1
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;

        public int pathSum(TreeNode root, int targetSum) {
            //前缀和+前序：每次查询当前差值为k的元素个数，保存sum进map
            map.put(0, 1);//root.val+sum-tar 单独一个节点(-tar)也算一个路径
            dfs(root, targetSum, 0);
            return res;
        }

        void dfs(TreeNode root, int k, int sum) {
            if (root == null) return;

            sum += root.val;
            res += map.getOrDefault(sum - k, 0);
            map.merge(sum, 1, Integer::sum);

            dfs(root.left, k, sum);
            dfs(root.right, k, sum);

            map.merge(sum, -1, Integer::sum);
        }
    }

    public int maxDepth(TreeNode root) {
        //后序遍历：收集当前节点和子节点信息，求根节点的最大高度
        if (root == null) return 0;//空节点高度为0

        int left = maxDepth(root.left);//左
        int right = maxDepth(root.right);//右

        return Math.max(left, right) + 1;//当前节点
    }

    public int maxArea(int[] height) {
        //双指针：哪边低移动哪边，底盘缩小的同时，寻求更高的水位
        int left = 0, right = height.length - 1, res = 0;

        while (left < right) {
            if (height[left] > height[right]) {//左高右低，按右边算
                res = Math.max(res, (right - left) * height[right]);
                //向右移动时，相当于缩小底盘，高度必须大于已有高度，不然没意义
                int temp = height[right];//边界水高
                while (height[right - 1] < temp) right--;
                right--;//移动到大于边界水位的位置
            } else {
                res = Math.max(res, (right - left) * height[left]);
                int temp = height[left];//边界水高
                while (height[left + 1] < temp) left++;
                left++;//移动到大于边界水位的位置
            }
        }
        return res;
    }


    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }


    class Solution988 {
        String res = null;

        public String smallestFromLeaf(TreeNode root) {
            //不能后序,只能前序：尾部固定的前提下，头越小越好
            if (root == null) return "";
            recur(root, "");
            return res;
        }

        void recur(TreeNode root, String cur) {
            cur = (char) (root.val + 'a') + cur;//子在前

            if (root.left == null && root.right == null) {
                if (res == null || cur.compareTo(res) < 0) res = cur;
            }//使用String，不需要回溯
            if (root.left != null) recur(root.left, cur);
            if (root.right != null) recur(root.right, cur);
        }
    }


    class Solution257 {
        ArrayList<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        public List<String> binaryTreePaths(TreeNode root) {
            //前序+回溯:回溯使用sb要注意val值的长度不一定是1
            backTrack(root);
            return res;
        }

        void backTrack(TreeNode root) {
            sb.append(root.val).append("->");
            //到达叶子节点，收集路径
            if (root.left == null && root.right == null) {
                res.add(sb.toString().substring(0, sb.length() - 2));
            }

            if (root.left != null) {//不为空才有必要递归+回溯
                backTrack(root.left);//val的长度不一定是1
                int len = String.valueOf(root.left.val).length();
                sb.delete(sb.length() - 2 - len, sb.length());
            }
            if (root.right != null) {
                backTrack(root.right);
                int len = String.valueOf(root.right.val).length();
                sb.delete(sb.length() - 2 - len, sb.length());
            }
        }
    }

    class Solution559 {
        public int maxDepth(Node root) {
            //后序：收集子节点信息
            if (root == null) return 0;
            int res = 0;

            for (Node child : root.children) {
                res = Math.max(res, maxDepth(child));
            }
            return res + 1;
        }
    }

    class Solution111 {
        int res = Integer.MAX_VALUE;

        public int minDepth(TreeNode root) {
            //前序：从上到下，遇到叶子节点更新结果
            if (root == null) return 0;
            pre(root, 1);
            return res;
        }

        void pre(TreeNode root, int depth) {
            if (root.left == null && root.right == null) {
                res = Math.min(res, depth);
                return;
            }

            if (root.left != null) pre(root.left, depth + 1);
            if (root.right != null) pre(root.right, depth + 1);
        }
    }


    public int countNodes(TreeNode root) {
        //后序遍历：统计节点个数
        if (root == null) return 0;//个数为0

        int left = countNodes(root.left);//左
        int right = countNodes(root.right);//右

        return 1 + left + right;//中
    }


    class Solution110 {
        boolean res = true;

        public boolean isBalanced(TreeNode root) {
            //后序遍历：依次对比两个子树的高度
            recur(root);
            return res;
        }

        int recur(TreeNode root) {//剪枝
            if (root == null || !res) return 0;

            int left = recur(root.left);
            int right = recur(root.right);
            if (Math.abs(left - right) > 1) res = false;

            return Math.max(left, right) + 1;
        }
    }

    class Solution513 {
        int maxD = 0, res;

        public int findBottomLeftValue(TreeNode root) {
            //前序：遇到叶子节点更新最大深度，如果遇到更深，更新结果
            pre(root, 1);
            return res;
        }

        void pre(TreeNode root, int depth) {
            if (root.left == null && root.right == null) {
                if (depth > maxD) {
                    maxD = depth;
                    res = root.val;
                }
                return;
            }

            if (root.left != null) pre(root.left, depth + 1);
            if (root.right != null) pre(root.right, depth + 1);
        }
    }

    class Solution114 {
        TreeNode pre = null;//记录链表的上一个节点，从后向前

        public void flatten(TreeNode root) {
            //后序:右左中，找到最右节点，开始向上串联
            if (root == null) return;
            //找到最右的节点
            flatten(root.right);
            flatten(root.left);

            root.left = null;
            root.right = pre;//连接链表
            pre = root; //更新链表的上一个节点
        }
    }

    class Solution654 {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            //前序+左闭右闭
            return build(nums, 0, nums.length - 1);
        }

        TreeNode build(int[] nums, int l, int r) {//当前数组的根节点
            if (l > r) return null;
            int index = l, max = nums[l];//当前数组最大值的下标、值
            for (int i = l; i <= r; i++) {
                if (nums[i] <= max) continue;
                max = nums[i];
                index = i;//更新最大值
            }
            //以index为根构建二叉树
            TreeNode root = new TreeNode(max);
            root.left = build(nums, l, index - 1);
            root.right = build(nums, index + 1, r);
            return root;
        }
    }

    class Solution543 {
        int max = 0;

        public int diameterOfBinaryTree(TreeNode root) {
            //后序：自下而上，主要是按边数算长度，长度=左节点数+右节点数
            find(root);
            return max;
        }

        int find(TreeNode root) {//当前节点为根的节点个数
            if (root == null) return 0;//空节点节点个数0

            int left = find(root.left);//左节点个数
            int right = find(root.right);//右节点个数

            max = Math.max(left + right, max);//更新最大值
            return Math.max(left, right) + 1;//返回当前根最大边长
        }
    }

    class Solution1 {

        List<String> res = new ArrayList<>();
        List<TreeNode> list = new ArrayList<>();

        public List<String> binaryTreePaths(TreeNode root) {
            //dfs:列表记录节点，空节点返回，叶子节点记录结果，不然就递归+回溯
            dfs(root);
            return res;
        }

        void dfs(TreeNode root) {

            list.add(root);//记录当前节点

            if (root.left == null && root.right == null) {
                StringBuilder sb = new StringBuilder();
                for (TreeNode node : list) {
                    sb.append(node.val).append("->");//添加节点路径
                }//删掉最后多余的 ->
                sb.delete(sb.length() - 3, sb.length() - 1);
                res.add(sb.toString()); //添加路径到结果中
            }
            if (root.left != null) {//只有不为空，才会添加节点，才有必要回溯
                dfs(root.left);//递归
                list.remove(list.size() - 1);//回溯
            }
            if (root.right != null) {
                dfs(root.right);//递归
                list.remove(list.size() - 1);//回溯
            }
        }
    }

    class Solution23 {
        int res = 0;

        public int pathSum(TreeNode root, int sum) {
            //前序遍历+dfs：前序遍历所有节点，并dfs前序向下搜
            //统计以每个节点为根的情况下的sum个数
            if (root == null) return 0;

            dfs(root, sum);//每个节点都向下dfs
            pathSum(root.left, sum);
            pathSum(root.right, sum);
            return res;
        }

        void dfs(TreeNode root, int sum) {
            if (root == null) return;

            sum -= root.val;//减去当前节点值
            if (sum == 0) res++;
            dfs(root.left, sum);
            dfs(root.right, sum);
        }
    }

    class Solution24 {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;

        public int pathSum(TreeNode root, int sum) {
            //前缀和+前序遍历+回溯
            map.put(0, 1);
            dfs(root, 0, sum);
            return res;
        }

        void dfs(TreeNode root, int total, int k) {
            if (root == null) return;

            //处理当前节点
            total += root.val;
            res += map.getOrDefault(total - k, 0);
            map.merge(total, 1, Integer::sum);

            dfs(root.left, total, k);//左dfs
            dfs(root.right, total, k);//右dfs
            //左右都搜完，把当前节点回溯掉
            map.merge(total, -1, Integer::sum);
        }
    }

    class Solution25 {
        String res;

        public String smallestFromLeaf(TreeNode root) {
            //dfs+前序+回溯:自顶向下，拼接节点为字符串，到叶子节点则更新结果
            if (root == null) return "";
            dfs(root, new StringBuilder());
            return res;
        }

        void dfs(TreeNode root, StringBuilder s) {
            s.append((char) (root.val + 'a'));//拼接字符串

            if (root.left == null && root.right == null) {
                String cur = s.reverse().toString();
                if (res == null || res.compareTo(cur) > 0) res = cur;
                s.reverse();//还原sb
            }
            if (root.left != null) {
                dfs(root.left, s);//只有不为空，才会添加，才有必要回溯
                s.deleteCharAt(s.length() - 1);
            }
            if (root.right != null) {
                dfs(root.right, s);
                s.deleteCharAt(s.length() - 1);
            }
        }
    }

    class Solution113 {
        ArrayList<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();

        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            //回溯+前序dfs：叶子节点记录结果，需要回溯，子节点不为空才递归+回溯
            if (root == null) return res;//find函数不需要判空了
            find(root, targetSum);
            return res;
        }

        void find(TreeNode root, int tar) {
            tar -= root.val;
            list.add(root.val);

            if (root.left == null && root.right == null) {
                if (tar == 0) res.add(new ArrayList<>(list));
            }
            if (root.left != null) {
                find(root.left, tar);
                list.remove(list.size() - 1);//回溯
            }
            if (root.right != null) {
                find(root.right, tar);
                list.remove(list.size() - 1);
            }
        }
    }


    class Solution236 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            //后序:从下到上，依次返回祖节点，当左右都有祖节点，返回
            //空节点返回null，pq其中一个节点，返回自己
            if (root == null || root == p || root == q) return root;

            TreeNode left = lowestCommonAncestor(root.left, p, q);//左子
            TreeNode right = lowestCommonAncestor(root.right, p, q);//右子

            if (left != null && right != null) return root;//左右都找到了祖节点
            if (left == null) return right;//向上传递祖节点
            else return left;
        }
    }


    class Solution2 {
        int res = 0;

        public int sumOfLeftLeaves(TreeNode root) {
            //后序遍历：遇到左叶子节点结果+1
            if (root == null) return 0;

            int left = sumOfLeftLeaves(root.left);//左
            int right = sumOfLeftLeaves(root.right);//右

            int mid = 0;//中
            if (root.left != null && root.left.left == null
                    && root.left.right == null) {
                mid = root.left.val;
            }

            return mid + left + right;
        }
    }

    class Solution4 {
        int maxDepth = 0;
        int res;

        public int findBottomLeftValue(TreeNode root) {
            //前序遍历：找到深度最大的节点，前序遍历的结果肯定是最左侧
            dfs(root, 1);
            return res;
        }

        void dfs(TreeNode root, int depth) {

            if (root == null) return;
            depth++; //深度+1

            if (root.left == null && root.right == null) {//叶子节点
                if (maxDepth < depth) {
                    maxDepth = depth;
                    res = root.val;
                }
                return;//这一句可有可无，没有的话会进行无意义的向下递归
            }

            dfs(root.left, depth);
            dfs(root.right, depth);
        }
    }


    class Solution26 {
        boolean res;

        public boolean hasPathSum(TreeNode root, int targetSum) {
            //dfs+前序：自顶向下，叶子节点判断，传递int变量，不需要回溯
            dfs(root, targetSum);
            return res;
        }

        void dfs(TreeNode root, int tar) {
            //如果已经有成功匹配的路径 直接返回
            if (root == null || res) return;

            tar -= root.val;
            if (root.left == null && root.right == null) {
                if (tar == 0) res = true;
            }
            dfs(root.left, tar);
            dfs(root.right, tar);
        }
    }




    class Solution7 {

        public TreeNode buildTree(int[] inorder, int[] postorder) {

            //后序数组的末尾值是根节点，根据根节点切分中序数组，找到左右子数组，递归
            //循环不变量：数组保持左闭右开
            int len = inorder.length; //两个数组长度一样
            if (len == 0) return null;//空数组，空树

            //1.找根节点
            int rootVal = postorder[len - 1];
            TreeNode root = new TreeNode(rootVal);

            //2.切分中序数组 左闭右开
            int rootIndex = 0;
            for (int i = 0; i < len; i++) {
                if (inorder[i] == rootVal) {
                    rootIndex = i;
                    break;
                }
            }
            int[] inL = Arrays.copyOfRange(inorder, 0, rootIndex);
            //注意此处边界，需要过掉中间的根节点
            int[] inR = Arrays.copyOfRange(inorder, rootIndex + 1, len);

            //3.切分后序数组 左闭右开 中序的左和后序的左 下标是一样的
            int[] postL = Arrays.copyOfRange(postorder, 0, rootIndex);
            int[] postR = Arrays.copyOfRange(postorder, rootIndex, len - 1);

            //4.递归
            root.left = buildTree(inL, postL);
            root.right = buildTree(inR, postR);

            return root;
        }
    }


    public TreeNode buildTree(int[] preorder, int[] inorder) {
        //前序数组的首值是根节点，根据根节点切分中序数组，找到左右子数组，递归
        //循环不变量：数组保持左闭右开
        int len = inorder.length; //两个数组长度一样
        if (len == 0) return null;//空数组，空树

        //1.找根节点
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);

        //2.切分中序数组 左闭右开
        int rootIndex = 0;
        for (int i = 0; i < len; i++) {
            if (inorder[i] == rootVal) {
                rootIndex = i;
                break;
            }
        }
        int[] inL = Arrays.copyOfRange(inorder, 0, rootIndex);
        //注意此处边界，需要过掉中间的根节点
        int[] inR = Arrays.copyOfRange(inorder, rootIndex + 1, len);

        //3.切分前序数组 左闭右开 中序的左+1和前序的左 下标是一样的
        int[] preL = Arrays.copyOfRange(preorder, 1, rootIndex + 1);
        int[] preR = Arrays.copyOfRange(preorder, rootIndex + 1, len);

        //4.递归
        root.left = buildTree(preL, inL);
        root.right = buildTree(preR, inR);

        return root;
    }

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        //前序数组 根左右 后序数组 左右根 找到左右划分下标划分左右数组 递归
        //循环不变量：数组保持左闭右开
        int len = postorder.length; //两个数组长度一样
        if (len == 0) return null;//空数组，空树

        //1.找根节点
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);

        if (len == 1) return root;//叶子节点提前返回，下方避免数组越界

        //2.切分后序数组 左闭右开 后序左子树数组起始 等于 前序左子树数组末尾
        int rootIndex = 0; //pre = [1,2,4,5,3,6,7], post = [4,5,2,6,7,3,1]
        for (int i = 0; i < len; i++) {
            if (postorder[i] == preorder[1]) {
                rootIndex = i;
                break;
            }
        }
        int[] postL = Arrays.copyOfRange(postorder, 0, rootIndex + 1);
        //注意此处边界，需要过掉中间的根节点
        int[] postR = Arrays.copyOfRange(postorder, rootIndex + 1, len - 1);

        //3.切分前序数组 左闭右开 中序的左+1和前序的左 下标是一样的
        int[] preL = Arrays.copyOfRange(preorder, 1, rootIndex + 2);
        int[] preR = Arrays.copyOfRange(preorder, rootIndex + 2, len);

        //4.递归
        root.left = constructFromPrePost(preL, postL);
        root.right = constructFromPrePost(preR, postR);

        return root;
    }


    class Solution8 {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            //每次找当前数组的最大值，创建节点，分割数组，递归
            //循环不变量：左闭右开！
            return dfs(nums, 0, nums.length);
        }

        TreeNode dfs(int[] nums, int l, int r) {
            if (l == r) return null;//左闭右开

            //找当前数组中的最大值
            int idx = l, max = nums[l];
            for (int i = l; i < r; i++) {
                if (nums[i] > max) {
                    idx = i;
                    max = nums[i];
                }
            }
            //创建节点，分割数组，递归
            TreeNode root = new TreeNode(nums[idx]);
            root.left = dfs(nums, l, idx);
            root.right = dfs(nums, idx + 1, r);
            return root;
        }
    }

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {

        //前序遍历：1空返回2,2空返回1（包括了都空的情况），都不空就叠加
        //聚焦问题：当前节点是什么？
        if (root1 == null) return root2;//1空，当前是2
        if (root2 == null) return root1;//2空，当前是1

        //都不空，节点叠加，当前节点是新节点
        TreeNode cur = new TreeNode(root1.val + root2.val);

        //递归子树并连接
        cur.left = mergeTrees(root1.left, root2.left); //左节点是？
        cur.right = mergeTrees(root1.right, root2.right); //右？

        return cur;//返回当前节点
    }

    class Solution9 {
        TreeNode pre;//记录前一个 避免顺序错误 [5,4,6,null,null,3,7]

        public boolean isValidBST(TreeNode root) {
            //中序：左小右大，且当前节点值要小于前一个节点值
            if (root == null) return true; //边界

            if (!isValidBST(root.left)) return false; //左

            if (pre != null && root.val <= pre.val) return false;//中
            pre = root;//更新前节点指针

            return isValidBST(root.right);//右
        }
    }

    class Solution10 {
        int res = Integer.MAX_VALUE;//记录最小结果
        TreeNode pre;//记录上一个节点

        public int getMinimumDifference(TreeNode root) {
            //中序：pre记录上一个节点
            if (root == null) return 0;

            getMinimumDifference(root.left);//左,全局变量，不需要接收返回值

            if (pre != null) res = Math.min(root.val - pre.val, res);//中
            pre = root;

            getMinimumDifference(root.right);//右

            return res;//根节点返回全局变量
        }
    }

    class Solution11 {
        TreeNode pre;//记录上一个节点
        int maxCount = 0, count;//最大频率，当前数频率
        List<Integer> list = new ArrayList<Integer>();

        public int[] findMode(TreeNode root) {
            //中序：pre=cur,持续计数，否则归1，更新众数
            dfs(root);
            return list.stream().mapToInt(Integer::intValue).toArray();
        }

        void dfs(TreeNode root) {
            if (root == null) return;

            findMode(root.left);//左，全局变量，不需要返回值

            //起始节点或者新数值，重新计数 否则持续计数
            if (pre == null || root.val != pre.val) count = 1;
            else count++;
            //更新结果集
            if (count == maxCount) list.add(root.val);
            else if (count > maxCount) {
                maxCount = count;
                list.clear();
                list.add(root.val);
            }
            //更新前节点
            pre = root;

            findMode(root.right);//右
        }
    }


    class Solution116 {
        public Node1 connect(Node1 root) {
            //前序:从上到下，每次连接下一层的节点，注意跨父节点的连接
            if (root == null) return null;//叶子节点，停止

            if (root.left != null) {//还不到叶子节点，连接下一层节点
                root.left.next = root.right;
                if (root.next != null) root.right.next = root.next.left;
            }

            connect(root.left);
            connect(root.right);
            return root;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //前序：从上到下，bst有序，祖先在[p,q]区间内，故寻找区间
        if (root == null) return null;
        //此处的前其实体现在最后的返回结果上了，或者说条件的判断上了
        //if(在区间内) return root; 此处放到了最下方

        //不知道p,q谁大，故区间整体判断，此时区间在root左侧,递归左子树
        if (root.val > p.val && root.val > q.val) { //左
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            if (left != null) return left;//返回的是祖先节点
        }
        if (root.val < p.val && root.val < q.val) { //右
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            if (right != null) return right;//返回的是祖先节点
        }

        //在区间内了，返回当前节点
        return root;
    }


    public TreeNode insertIntoBST(TreeNode root, int val) {
        //前序：利用BST有序，找到第一个合理的空节点插入即可
        if (root == null) return new TreeNode(val);//找到的目标空节点
        //此处是前序后置了，左右都是在调整当前节点root本身

        if (root.val > val) //左
            //判断得知，目标应该在左侧，左侧是null，返回的是目标新节点
            root.left = insertIntoBST(root.left, val);
        if (root.val < val) //右
            root.right = insertIntoBST(root.right, val);

        return root;//返回修正过的值
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        //bst前序:找到目标节点，左空返右，右空返左，都不空返左大或右小
        if (root == null) return null;
        //此处前序后置，前包括else语句，最后的return语句

        if (root.val > key) //左
            //返回的是目标新节点
            root.left = deleteNode(root.left, key);
        else if (root.val < key) //右
            root.right = deleteNode(root.right, key);
        else { //目标节点，左空，右空，左右都不空
            if (root.left == null) return root.right;//左空返右
            if (root.right == null) return root.left;//右空返左
            //左右都不空，返左大(左子树的最右节点)或右小(右子树的最左节点)
            TreeNode max = root.left; //左大，叶子节点
            while (max.right != null) max = max.right;
            //前序从上到下,key=max:继续向下删max,root=max,置换节点
            key = root.val = max.val;
            root.left = deleteNode(root.left, key);//继续删左大
        }

        return root;//返回当前节点
    }


    public TreeNode trimBST(TreeNode root, int low, int high) {
        //前序从上到下：超过边界就找到第一个在边界的值取代自己
        //逻辑是：找到可以替换当前节点的目标节点
        if (root == null) return null;

        if (root.val < low)//超过下边界，向右找取代自己的节点
            return trimBST(root.right, low, high);
        if (root.val > high)//超过上边界，向左找取代自己的节点
            return trimBST(root.left, low, high);

        //再向下就是 当前节点属于区间内
        root.left = trimBST(root.left, low, high);//找取代左节点的
        root.right = trimBST(root.right, low, high);//找取代右节点的
        return root;
    }


    public TreeNode sortedArrayToBST(int[] nums) {
        //前序：找到上中点，分割数组，构建BST（一定是平衡的），左小右大
        //循环不变量：左闭右开；方法目标：返回当前数组的根节点
        int len = nums.length;
        if (len == 0) return null;//空数组，空树
        //找到上中点，构建根节点
        int mid = len >> 1;
        TreeNode root = new TreeNode(nums[mid]);
        //递归连接左右子节点
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, mid));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums, mid + 1, len));
        return root;
    }

    class Solution12 {
        int sum = 0;//记录当前累加值

        public TreeNode convertBST(TreeNode root) {
            //逆序BST：右中左，依次修改当前节点值
            if (root == null) return null;

            convertBST(root.right);//右

            root.val += sum;//中，修改当前节点值
            sum = root.val;//记录当前累加值

            convertBST(root.left);//左

            return root;
        }
    }

    static class Node1 {
        public int val;
        public Node1 left;
        public Node1 right;
        public Node1 next;

        public Node1() {
        }

        public Node1(int _val) {
            val = _val;
        }

        public Node1(int _val, Node1 _left, Node1 _right, Node1 _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }


    class Solution15 {
        int res = -1, rank;
        //此题需要用rank把k变成全局变量，否则回溯会出问题

        public int kthSmallest(TreeNode root, int k) {
            //BST中序：计数（k--==0），返回节点值
            rank = k;//左遍历 k=0 但回溯又会变成原来的值
            dfs(root);
            return res;
        }

        void dfs(TreeNode root) {
            if (root == null) return;

            dfs(root.left);//左

            if (--rank == 0) res = root.val;//中
            dfs(root.right);//右
        }
    }

    public int numTrees(int n) {
        //动态规划：种数=左子树种数*右子树种数（重叠子问题）
        //状态：dp[i] n=i 时的种数
        int[] dp = new int[n + 1];

        //初始状态：dp[0] = dp[1] =1,其他的都是0
        dp[0] = dp[1] = 1;

        //状态转移：种数=左子树种数dp[j-1]*右子树种数dp[i-j]
        for (int i = 2; i <= n; i++) {//j是根节点的下标
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    class Solution17 {
        List<Integer> res = new ArrayList<Integer>();

        public List<Integer> inorderTraversal(TreeNode root) {
            if (root == null) return res;

            inorderTraversal(root.left);
            res.add(root.val);
            inorderTraversal(root.right);
            return res;
        }
    }

    class Solution18 {
        public List<Integer> inorderTraversal(TreeNode root) {
            //基于栈迭代：当前节点非空，压栈，并向左子树移动；为空，弹栈记录，并向右子树移动
            List<Integer> res = new ArrayList<Integer>();
            Deque<TreeNode> stack = new LinkedList<>();

            while (!stack.isEmpty() || root != null) {
                if (root != null) { //节点非空，压栈，并向左子树移动
                    stack.push(root);
                    root = root.left; //左
                } else {//节点为空，弹栈记录，并向右子树移动
                    root = stack.pop();
                    res.add(root.val); //中
                    root = root.right; //右
                }
            }
            return res;
        }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        //基于栈：中左右，弹栈记录，压右，压左
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stack = new LinkedList<>();
        if (root == null) return res;
        stack.push(root); //先压根节点

        while (!stack.isEmpty()) {
            root = stack.pop();
            res.add(root.val);
            if (root.right != null) stack.push(root.right);
            if (root.left != null) stack.push(root.left);
        }
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        //基于栈：左右中，弹栈到辅助栈，压左，压右；弹辅助栈（左右中）
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stack = new LinkedList<>();
        Deque<TreeNode> stack1 = new LinkedList<>();
        if (root == null) return res;
        stack.push(root); //先压根节点

        while (!stack.isEmpty()) {
            root = stack.pop();
            stack1.push(root);
            if (root.left != null) stack.push(root.left);
            if (root.right != null) stack.push(root.right);
        }
        while (!stack1.isEmpty()) res.add(stack1.pop().val);
        return res;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        //基于队列：队列非空，记录层节点数，依次出队列记录，左右进队列
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();//记录当前层的节点个数
            List<Integer> list = new ArrayList<>();//存放当前层节点
            for (int i = 0; i < size; i++) {//遍历当前层节点
                root = queue.poll();
                list.add(root.val);
                if (root.left != null) queue.offer(root.left);
                if (root.right != null) queue.offer(root.right);
            }
            res.add(list);
        }
        return res;
    }


    class Solution16 {
        public List<TreeNode> generateTrees(int n) {
            //变换根节点下标，递归穷举：不变量：左闭右闭
            return dfs(1, n);
        }

        List<TreeNode> dfs(int left, int right) {
            //当前范围能生成的BST列表
            ArrayList<TreeNode> list = new ArrayList<>();

            //根节点选在left上，返回null节点列表
            if (left > right) {
                list.add(null);
                return list;
            }

            //变换根节点下标，递归穷举各种根节点下标下的可能BST根节点
            for (int i = left; i <= right; i++) {
                //当前根节点下标下的 根节点列表
                List<TreeNode> arrL = dfs(left, i - 1);
                List<TreeNode> arrR = dfs(i + 1, right);
                //穷举所有可能的左右子树组合
                for (TreeNode lN : arrL) {
                    for (TreeNode rN : arrR) {
                        //构建当前根节点，连接左右子节点
                        TreeNode root = new TreeNode(i);
                        root.left = lN;
                        root.right = rN;
                        list.add(root);
                    }
                }
            }
            return list;
        }
    }


    public TreeNode searchBST(TreeNode root, int val) {
        //BST:前序遍历，左小右大
        if (root == null || root.val == val) return root;

        //左小右大，等于的情况已经在边界中了
        if (root.val > val) return searchBST(root.left, val);
        else return searchBST(root.right, val);
    }


    public int minDepth(TreeNode root) {
        //后序遍历：求根节点到叶子节点的最小高度
        //不同于最大高度，最小高度要注意非叶子节点的干扰
        if (root == null) return 0;


        int left = minDepth(root.left);//左
        int right = minDepth(root.right);//右

        //如果左右有一个不为空，就不是叶子节点，返回不为空的高度
        if (root.left == null) return 1 + right;
        if (root.right == null) return 1 + left;

        return 1 + Math.min(left, right);
    }

    class Solution100 {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            //前序：根是否相同？不为空的情况下，根左？根右？
            if (p == null && q == null) return true;//都为空
            if (p == null || q == null) return false;//有一个不为空
            if (p.val != q.val) return false;//值不同

            return isSameTree(p.left, q.left)
                    && isSameTree(p.right, q.right);
        }
    }


    class Solution572 {
        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            //双前序：1树前序，依次对比每个节点和2树的根(对比也使用前序)
            if (root == null) return false;

            return compare(root, subRoot) || isSubtree(root.left, subRoot)
                    || isSubtree(root.right, subRoot);
        }

        boolean compare(TreeNode root, TreeNode sub) {
            if (root == null && sub == null) return true;
            if (root == null || sub == null) return false;
            if (root.val != sub.val) return false;

            return compare(root.left, sub.left)
                    && compare(root.right, sub.right);
        }
    }


    class Solution101 {
        public boolean isSymmetric(TreeNode root) {
            //前序，从上到下：左右子节点是否相同？左右外？左右内？
            if (root == null) return true;

            return compare(root.left, root.right);
        }

        boolean compare(TreeNode left, TreeNode right) {
            if (left == null && right == null) return true;//都为空
            if (left == null || right == null) return false;//保证节点不为空
            if (left.val != right.val) return false;

            return compare(left.left, right.right) && compare(left.right, right.left);
        }
    }

}
