package com.csu.dataStructure.Trie;

import java.util.LinkedList;
import java.util.List;

public class MyTrieMap<V> implements ITrieMap<V> {
    // ASCII 码个数
    private static final int R = 256;
    // 当前存在 Map 中的键值对个数
    private int size = 0;

    private static class TrieNode<V> {
        V val = null;
        TrieNode<V>[] children = new TrieNode[R];
    }

    // Trie 树的根节点
    private TrieNode<V> root = null;

    @Override
    public void put(String key, V val) {
        if (!containsKey(key)) size++;
        // 需要一个额外的辅助函数，递归修改原数据结构
        root = put(root, key, val, 0);
    }

    // 定义：向以 node 为根的 Trie 树中插入 key[i..]，返回插入完成后的根节点
    private TrieNode<V> put(TrieNode<V> node, String key, V val, int i) {
        if (node == null) node = new TrieNode<>();

        if (i == key.length()) {// 尾节点存值
            node.val = val;
            return node;
        }
        char c = key.charAt(i);
        // 递归插入子节点，并接收返回值
        node.children[c] = put(node.children[c], key, val, i + 1);
        return node;
    }

    @Override
    public void remove(String key) {
        if (!containsKey(key)) return;
        size--;
        // 递归修改数据结构要接收函数的返回值
        root = remove(root, key, 0);
    }

    // 定义：在以 node 为根的 Trie 树中删除 key[i..]，返回删除后的根节点
    private TrieNode<V> remove(TrieNode<V> node, String key, int i) {
        if (node == null) return null;

        if (i == key.length()) node.val = null;//尾节点删值
        else {
            char c = key.charAt(i); // 递归去子树进行删除
            node.children[c] = remove(node.children[c], key, i + 1);
        }
        // 后序位置，递归路径上的节点可能需要被清理
        if (node.val != null) return node;

        // 节点为空，检查该 TrieNode 是否还有后缀
        for (int c = 0; c < R; c++) {
            if (node.children[c] != null) {
                // 只要存在一个子节点（后缀树枝），就不需要被清理
                return node;
            }
        }
        // 既没有存储 val，也没有后缀树枝，则该节点需要被清理
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    // 工具函数
    // 从节点 node 开始搜索 key，如果key完整存在返回尾节点，否则返回 null
    private TrieNode<V> getNode(TrieNode<V> node, String key) {
        TrieNode<V> p = node;//起始节点

        for (int i = 0; i < key.length(); i++) {
            if (p == null) return null;// 节点不存在
            p = p.children[key.charAt(i)];// 向下搜索
        }
        return p;//对应的是key的最后一个字符节点
    }


    @Override
    public V get(String key) {
        TrieNode<V> node = getNode(root, key);
        return node == null ? null : node.val;
    }

    @Override
    public boolean containsKey(String key) {
        //key的尾节点不为空 且 尾节点有值
        return get(key) != null;//与hasKeyWithPrefix存在区别
    }

    @Override
    public boolean hasKeyWithPrefix(String prefix) {
        return getNode(root, prefix) != null;
    }

    @Override
    public String shortestPrefixOf(String query) {
        TrieNode<V> p = root; //从根节点出发
        for (int i = 0; i < query.length(); i++) {
            if (p == null) return "";//还没找到就没了，说明不存在
            //返回第一个遇到的有值节点，就是最短子串
            if (p.val != null) return query.substring(0, i);
            // 向下搜索
            p = p.children[query.charAt(i)];
        }
        //最后判断下尾节点，相当于query本身是一个 key
        if (p != null && p.val != null) return query;

        return "";
    }

    @Override
    public String longestPrefixOf(String query) {
        //额外维护一个 记录沿途有值节点下标的 指针max
        TrieNode<V> p = root;
        int max = 0;

        for (int i = 0; i < query.length(); i++) {
            if (p == null) break;//不能直接 return
            if (p.val != null) max = i;//更新max指针
            // 向下搜索
            p = p.children[query.charAt(i)];
        }

        //最后判断下尾节点，相当于query本身是一个 key
        if (p != null && p.val != null) return query;

        return query.substring(0, max);
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new LinkedList<>();
        // 找到匹配 prefix 在 Trie 树中的那个节点
        TrieNode<V> x = getNode(root, prefix);
        if (x == null) return res;//不存在此key,直接返回

        // 回溯 遍历以 x 为根的这棵 Trie 树
        traverse(x, new StringBuilder(prefix), res);
        return res;
    }

    // 遍历以 node 节点为根的 Trie 树，找到所有键
    private void traverse(TrieNode<V> node, StringBuilder path, List<String> res) {
        if (node == null) return;

        // 记录沿途的结果
        if (node.val != null) res.add(path.toString());

        for (char c = 0; c < R; c++) {
            path.append(c);// 处理当前节点
            traverse(node.children[c], path, res);// 递归
            path.deleteCharAt(path.length() - 1);// 回溯
        }
    }


    @Override
    public List<String> keysWithPattern(String pattern) {
        List<String> res = new LinkedList<>();
        traverse1(root, new StringBuilder(), pattern, 0, res);
        return res;
    }

    // 遍历函数，尝试在「以 node 为根的 Trie 树中」匹配 pattern[i..]
    private void traverse1(TrieNode<V> node, StringBuilder path, String pattern, int i, List<String> res) {
        if (node == null) return;

        if (i == pattern.length()) {// pattern 匹配完成
            if (node.val != null) res.add(path.toString());
            else return;
        }

        char c = pattern.charAt(i);
        if (c == '.') { // 是通配符，可以变化成任意字符
            for (char j = 0; j < R; j++) { //遍历所有可能
                path.append(j); //处理当前节点
                traverse1(node.children[j], path, pattern, i + 1, res);
                path.deleteCharAt(path.length() - 1);//回溯
            }
        } else { //普通字符 c
            path.append(c); //处理当前节点
            traverse1(node.children[c], path, pattern, i + 1, res);
            path.deleteCharAt(path.length() - 1); //回溯
        }
    }

    @Override
    public boolean hasKeyWithPattern(String pattern) {
        return traverse2(root, pattern, 0);
    }

    // 函数定义：从 node 节点开始匹配 pattern[i..]，返回是否成功匹配
    private boolean traverse2(TrieNode<V> node, String pattern, int i) {
        if (node == null) return false;

        if (i == pattern.length()) return node.val != null;

        char c = pattern.charAt(i);

        if (c != '.') {// 不是通配符
            return traverse2(node.children[c], pattern, i + 1);
        }

        for (int j = 0; j < R; j++) {// 是通配符
            if (traverse2(node.children[j], pattern, i + 1)) {
                return true;
            }
        }
        // 都没有匹配
        return false;
    }

}
