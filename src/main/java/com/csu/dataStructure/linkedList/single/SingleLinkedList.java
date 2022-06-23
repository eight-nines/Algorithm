package com.csu.dataStructure.linkedList.single;

import com.csu.dataStructure.linkedList.AbstractList;



//单向链表
public class SingleLinkedList<E> extends AbstractList<E> {
	//首节点
	private Node<E> first;

	//节点内部类
	private static class Node<E> {
		E element;
		Node<E> next;
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

	}
	/**
	 * 获取index位置对应的节点对象
	 * @param index
	 * @return
	 */
	//获取index位置处的节点
	private Node<E> node(int index) {
		rangeCheck(index);

		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

@Override
public void clear() {
	//只需要断开首节点的引用即可
	size = 0;
	first = null;
}

@Override
public E get(int index) {
	/*
	 * 最好：O(1)
	 * 最坏：O(n)
	 * 平均：O(n)
	 */
	return node(index).element;
}

	@Override
public E set(int index, E element) {
	/*
	 * 最好：O(1)
	 * 最坏：O(n)
	 * 平均：O(n)
	 */
	Node<E> node = node(index);
	E old = node.element;
	node.element = element;
	return old;
}

	@Override
public void add(int index, E element) {
	/*
	 * 最好：O(1)
	 * 最坏：O(n)
	 * 平均：O(n)
	 */
	rangeCheckForAdd(index);
	//注意边界情况：0，size，size-1
	if (index == 0) {
		first = new Node<>(element, first);
	} else {
		//获得前一个节点
		Node<E> prev = node(index - 1);
		prev.next = new Node<>(element, prev.next);
	}
	size++;
}

	@Override
public E remove(int index) {
	/*
	 * 最好：O(1)
	 * 最坏：O(n)
	 * 平均：O(n)
	 */
	rangeCheck(index);

	//默认删除的节点是首节点，如果不是后面再覆盖
	Node<E> node = first;
	//如果删除首节点，将首节点引用指向下个节点
	if (index == 0) {
		first = first.next;
	} else { //不是首节点，找到前一个节点，让前一个节点指向后一个节点
		Node<E> prev = node(index - 1);
		node = prev.next;
		prev.next = node.next;
	}
	size--;
	return node.element;
}

	@Override
public int indexOf(E element) {
	if (element == null) {
		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (node.element == null) return i;

			node = node.next;
		}
	} else {
		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (element.equals(node.element)) return i;

			node = node.next;
		}
	}
	return ELEMENT_NOT_FOUND;
}

	@Override
public String toString() {
	StringBuilder string = new StringBuilder();
	string.append("size=").append(size).append(", [");
	Node<E> node = first;
	for (int i = 0; i < size; i++) {
		if (i != 0) {
			string.append(", ");
		}

		string.append(node.element);

		node = node.next;
	}
	string.append("]");

	return string.toString();
}
}
