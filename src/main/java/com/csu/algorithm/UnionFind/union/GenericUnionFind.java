package com.csu.algorithm.UnionFind.union;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 泛型，V是传入节点的类型，如Student/Node/Integer
public class GenericUnionFind<V> {
	// key存放节点对象名，value存放节点对象
	private Map<V, Node<V>> nodes = new HashMap<V, Node<V>>();

	// 节点内部类，把传入的对象包装成节点
	// 其中value是自己的对象名；parent是父节点；rank为深度
	private static class Node<V> {
		// value即V类对象名
		V value;
		// 父节点初始指向自己
		Node<V> parent = this;
		// 初始深度为1
		int rank = 1;

		Node(V value) {
			this.value = value;
		}
	}

	// 把对象包装成节点，初始化节点属性
	public void makeSet(V v) {
		if (nodes.containsKey(v)) return;
		nodes.put(v, new Node<>(v));
	}
	
	// 传入对象，找到根节点
	// hashMap找到对象的节点，再通过节点属性找到父节点
	private Node<V> findNode(V v) {
		Node<V> node = nodes.get(v);
		if (node == null) return null;
		while (!Objects.equals(node.value, node.parent.value)) {
			// Path Halving
			node.parent = node.parent.parent;
			node = node.parent;
		}
		return node;
	}

	// find操作，函数名不变，调用findNode函数
	public V find(V v) {
		Node<V> node = findNode(v);
		return node == null ? null : node.value;
	}

	// 首先找到两个对象的根节点，比较两者的rank(基于rank的优化)
	public void union(V v1, V v2) {
		Node<V> p1 = findNode(v1);
		Node<V> p2 = findNode(v2);
		if (p1 == null || p2 == null) return;
		if (Objects.equals(p1.value, p2.value)) return;
		
		if (p1.rank < p2.rank) {
			p1.parent = p2;
		} else if (p1.rank > p2.rank) {
			p2.parent = p1;
		} else {
			p1.parent = p2;
			p2.rank += 1;
		}
	}
	
	public boolean isSame(V v1, V v2) {
		return Objects.equals(find(v1), find(v2));
	}

}
