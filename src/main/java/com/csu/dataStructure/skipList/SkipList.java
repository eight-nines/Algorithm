package com.csu.dataStructure.skipList;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class SkipList<K, V> {
	//最大层数
	private static final int MAX_LEVEL = 32;
	//当前跳表的有效层数
	private int level;
	//新节点增加层数的固定概率
	private static final double P = 0.25;
	//节点个数
	private int size;
	//用于排序的比较器
	private Comparator<K> comparator;
	//头结点：不存放任何K_V的虚拟节点
	private Node<K, V> first;
	
	public SkipList(Comparator<K> comparator) {
		this.comparator = comparator;
		first = new Node<>(null, null, MAX_LEVEL);
	}
	
	public SkipList() {
		this(null);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public V get(K key) {
		keyCheck(key);

		//首节点
		Node<K, V> node = first;
		//从最上层向下搜索，层级索引从0开始，（0,level-1）
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			//“遍历到了最后一个节点” or “节点值大于当前值”
			while (node.nexts[i] != null
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				//当前节点的下一个节点值小于目标值，前进
				node = node.nexts[i];
			}
			// 刚好是要找的节点，直接返回；否则从当前节点开始继续遍历下一层
			if (cmp == 0) return node.nexts[i].value;
		}
		//遍历所有层还没找到（如果找到会提前返回），则不存在目标值
		return null;
	}
	
	public V put(K key, V value) {
		keyCheck(key);
		
		Node<K, V> node = first;
		//存放当前节点在所有层中的前继节点
		Node<K, V>[] prevs = new Node[level];
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null 
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			if (cmp == 0) { // 节点是存在的，替换值并返回
				V oldV = node.nexts[i].value;
				node.nexts[i].value = value;
				return oldV;
			}
			//当前层的前继
			prevs[i] = node;
		}
		
		// 新节点的层数
		int newLevel = randomLevel();
		// 创建新节点
		Node<K, V> newNode = new Node<>(key, value, newLevel);
		// 设置前驱和后继
		for (int i = 0; i < newLevel; i++) {
			//如果新节点层数大于已有层数，前驱是虚拟节点，后继默认是null
			if (i >= level) {
				first.nexts[i] = newNode;
			} else {
				newNode.nexts[i] = prevs[i].nexts[i];
				prevs[i].nexts[i] = newNode;
			}
		}
		
		// 节点数量增加
		size++;
		// 计算跳表的最终层数
		level = Math.max(level, newLevel);

		return null;
	}
	
	public V remove(K key) {
		keyCheck(key);
		
		Node<K, V> node = first;
		Node<K, V>[] prevs = new Node[level];
		//判断当前节点是否存在
		boolean exist = false;
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null 
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			prevs[i] = node;
			//只要找到一次，就证明存在
			if (cmp == 0) exist = true;
		}
		//不存在直接返回
		if (!exist) return null;
		
		// 需要被删除的节点，node是目标节点的前一个节点
		Node<K, V> removedNode = node.nexts[0];
		
		// 数量减少
		size--;
		
		// 设置前继节点的后继
		for (int i = 0; i < removedNode.nexts.length; i++) {
			prevs[i].nexts[i] = removedNode.nexts[i];
		}
		
		// 更新跳表的层数
		int newLevel = level;
		while (--newLevel >= 0 && first.nexts[newLevel] == null) {
			level = newLevel;
		}
		
		return removedNode.value;
	}
	
	private int randomLevel() {
		int level = 1;
		//P代表增加层数的概率
		while (Math.random() < P && level < MAX_LEVEL) {
			level++;
		}
		return level;
	}
	
	private void keyCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null.");
		}
	}
	
	private int compare(K k1, K k2) {
		return comparator != null 
				? comparator.compare(k1, k2)
				: ((Comparable<K>)k1).compareTo(k2);
	}
	
	private static class Node<K, V> {
		K key;
		V value;
		//存放每一层（如果有）的后继指针
		Node<K, V>[] nexts;
		//level指当前节点的层高
		public Node(K key, V value, int level) {
			this.key = key;
			this.value = value;
			nexts = new Node[level];
		}
		@Override
		public String toString() {
			return key + ":" + value + "_" + nexts.length;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("一共" + level + "层").append("\n");
		for (int i = level - 1; i >= 0; i--) {
			Node<K, V> node = first;
			while (node.nexts[i] != null) {
				sb.append(node.nexts[i]);
				sb.append(" ");
				node = node.nexts[i];
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
