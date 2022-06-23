package com.csu.algorithm.Tree;

import java.io.*;
import java.util.*;

public class Main {

    static boolean contains(Node n1, Node n2) {
        if(n2 == null) {
            return true;
        }

        if(n1 == null || n1.val != n2.val) {
            return false;
        }

        return  contains(n1.left, n2.left) && contains(n1.right, n2.right);
    }

    static boolean traversal(Node n1, Node n2) {
        if(n1 == null) {
            return false;
        }

        return contains(n1, n2) || traversal(n1.left, n2) || traversal(n1.right, n2);
    }

    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    private static StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    public static int nextInt() {
        try {
            st.nextToken();
            return (int) st.nval;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Node read() {
        int fal, lth, rth;
        fal = nextInt();
        lth = nextInt();
        rth = nextInt();
        Node node = new Node(fal);
        if (lth != 0) {
            node.left = read();
        }
        if (rth != 0) {
            node.right = read();
        }
        return node;
    }

    public static void main(String[] args) {
        int n = nextInt();
        int root = nextInt();
        Node node = read();

        nextInt();
        nextInt();

        Node node2 = read();

        System.out.println(traversal(node, node2));

    }
}