package com.csu.dataStructure.Trie;

import sun.reflect.generics.tree.Tree;

import java.util.List;

public class TrieAll {


    class Solution {


        class Trie {

            class Node {
                String val;
                Node[] childes = new Node[26];
            }

            Node root = null;

            public Trie() {
                root = new Node();
            }

            public void insert(String word) {
                root = insert(root, word, 0);
            }

            public Node insert(Node node, String word, int i) {
                if (node == null) node = new Node();

                if (i == word.length()) {
                    node.val = word;
                    return node;
                }

                int c = word.charAt(i) - 'a';
                node.childes[c] = insert(node.childes[c], word, i + 1);
                return node;
            }

            //找到最小前缀词，找不到就返回自身
            public String minPrefix(String word) {
                Node cur = root;

                for (int i = 0; i < word.length(); i++) {
                    if (cur == null) return word;
                    if (cur.val != null) return cur.val;
                    cur = cur.childes[word.charAt(i) - 'a'];
                }
                return word;
            }
        }

        public String replaceWords(List<String> dictionary, String sentence) {
            //前缀树：找到最小前缀词，如果不存在，返回自身
            //初始化字典树
            Trie trie = new Trie();
            for (String str : dictionary) trie.insert(str);

            //遍历句子中每个单词，找到最小前缀(或本身)，拼接到结果中
            String[] strs = sentence.split(" ");
            StringBuilder res = new StringBuilder();

            for (int i = 0; i < strs.length; i++) {
                res.append(trie.minPrefix(strs[i]));
                if (i != strs.length - 1) res.append(" ");
            }

            return res.toString();
        }
    }


    class WordDictionary {

        class Node {
            Object val;
            Node[] childes = new Node[26];
        }

        Node root = null;

        public WordDictionary() {
            root = new Node();
        }

        public void addWord(String word) {
            root = addWord(root, word, 0);
        }

        public Node addWord(Node node, String word, int i) {
            if (node == null) node = new Node();

            if (i == word.length()) {
                node.val = new Object();
                return node;
            }

            int c = word.charAt(i) - 'a';
            node.childes[c] = addWord(node.childes[c], word, i + 1);

            return node;
        }

        public boolean search(String word) {
            return search(root, word, 0);
        }

        public boolean search(Node node, String word, int i) {
            if (node == null) return false;

            if (i == word.length()) return node.val != null;

            char c = word.charAt(i);
            if (c != '.') {
                return search(node.childes[c - 'a'], word, i + 1);
            }
            for (int j = 0; j < 26; j++) {
                //尝试所有分支，有一个能匹配就返回true,所有都不匹配，返回false
                if (search(node.childes[j], word, i + 1)) return true;
            }
            return false;
        }
    }


    class MapSum {
        class Node {
            Integer val;
            Node[] childes = new Node[26];
        }

        Node root = null;

        public MapSum() {
            root = new Node();
        }

        public Node getNode(Node node, String word) {
            Node cur = node;
            //从node开始搜索word,存在返回尾节点
            for (int i = 0; i < word.length(); i++) {
                if (cur == null) return null;
                cur = cur.childes[word.charAt(i) - 'a'];
            }
            return cur;
        }

        public void insert(String key, int val) {
            root = insert(root, key, val, 0);
        }

        public Node insert(Node node, String word, int val, int i) {
            if (node == null) node = new Node();

            if (i == word.length()) {
                node.val = val;
                return node;
            }

            int c = word.charAt(i) - 'a';
            node.childes[c] = insert(node.childes[c], word, val, i + 1);
            return node;
        }

        public int sum(String prefix) {
            Node cur = getNode(root, prefix);
            return sum(cur);
        }

        public int sum(Node node) {
            if (node == null) return 0;
            int res = 0;

            if (node.val != null) res += node.val;

            for (int i = 0; i < 26; i++) {
                res += sum(node.childes[i]);
            }
            return res;
        }
    }

    class Trie {

        class Node {
            Object val;
            Node[] childes = new Node[26];
        }

        Node root = null;

        public Trie() {
            root = new Node();
        }

        public Node getNode(Node node, String word) {
            Node cur = node;
            //从node开始搜索word,存在返回尾节点
            for (int i = 0; i < word.length(); i++) {
                if (cur == null) return null;
                cur = cur.childes[word.charAt(i) - 'a'];
            }
            return cur;
        }

        public void insert(String word) {
            if (search(word)) return;
            root = insert(root, word, 0);
        }

        public Node insert(Node node, String word, int i) {
            if (node == null) node = new Node();

            if (i == word.length()) {
                node.val = new Object();
                return node;
            }

            int c = word.charAt(i) - 'a';
            node.childes[c] = insert(node.childes[c], word, i + 1);
            return node;
        }

        public boolean search(String word) {
            Node node = getNode(root, word);
            return node != null && node.val != null;
        }

        public boolean startsWith(String prefix) {
            return getNode(root, prefix) != null;
        }
    }


}
