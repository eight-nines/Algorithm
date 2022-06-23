package com.csu.dataStructure.dynamicArray;

@SuppressWarnings("unchecked")
public class ArrayList<E> {
	/**
	 * 元素的数量
	 */
	private int size;
	/**
	 * 所有的元素
	 */
	private E[] elements;
	
	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;
	
public ArrayList(int capaticy) {
	capaticy = (capaticy < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capaticy;
	elements = (E[]) new Object[capaticy];
}
	
	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * 清除所有元素
	 */
public void clear() {
	for (int i = 0; i < size; i++) {
		elements[i] = null;
	}
	size = 0;
}

	/**
	 * 元素的数量
	 * @return
	 */
public int size() {
	return size;
}

	/**
	 * 是否为空
	 * @return
	 */
public boolean isEmpty() {
	 return size == 0;
}

	/**
	 * 是否包含某个元素
	 * @param element
	 * @return
	 */
public boolean contains(E element) {
	return indexOf(element) != ELEMENT_NOT_FOUND;
}

	/**
	 * 添加元素到尾部
	 * @param element
	 */
public void add(E element) {
	insert(size, element);
}

	/**
	 * 获取index位置的元素
	 * @param index
	 * @return
	 */
public E get(int index) {
	rangeCheck(index);
	return elements[index];
}

	/**
	 * 设置index位置的元素
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
public E set(int index, E element) {
	rangeCheck(index);

	E old = elements[index];
	elements[index] = element;
	return old;
}

	/**
	 * 在index位置插入一个元素
	 * @param index
	 * @param element
	 */
//在index位置插入一个元素
public void insert(int index, E element) {
	//边界判断
	rangeCheckForAdd(index);
	//扩容判断
	ensureCapacity(size + 1);

	//后续的每个元素都向后挪一位
	//精华：从最后一个新扩展的位置向前遍历
	for (int i = size; i > index; i--) {
		elements[i] = elements[i - 1];
	}
	elements[index] = element;
	size++;
}

	/**
	 * 删除index位置的元素
	 * @param index
	 * @return
	 */
public E remove(int index) {
	//1.边界判断
	rangeCheck(index);

	//2.用于返回的删除的值
	E old = elements[index];

	//3.整体前挪
	for (int i = index + 1; i < size; i++) {
		elements[i - 1] = elements[i];
	}
	//4.将末尾元素设为null
	elements[--size] = null;
	return old;
}

	/**
	 * 查看元素的索引
	 * @param element
	 * @return
	 */
//查看元素的索引
public int indexOf(E element) {
	//返回第一个值为null的索引
	if (element == null) {  // 1
		for (int i = 0; i < size; i++) {
			if (elements[i] == null) return i;
		}
	} else {
		//使用equal比较对象
		for (int i = 0; i < size; i++) {
			if (element.equals(elements[i])) return i; // n
		}
	}
	return ELEMENT_NOT_FOUND;
}
	
//	public int indexOf2(E element) {
//		for (int i = 0; i < size; i++) {
//			if (valEquals(element, elements[i])) return i; // 2n
//		}
//		return ELEMENT_NOT_FOUND;
//	}
//	
//	private boolean valEquals(Object v1, Object v2) {
//		return v1 == null ? v2 == null : v1.equals(v2);
//	}
	
	/**
	 * 保证要有capacity的容量
	 * @param capacity
	 */
//保证操作顺利进行需要有capacity的容量
private void ensureCapacity(int capacity) {
	//如果现有的数组长度（注意不是size）大于等于需要的容量，不需要扩容
	int oldCapacity = elements.length;
	if (oldCapacity >= capacity) return;

	// 新容量为旧容量的1.5倍，利用位运算
	//此处可以使用System.arraycopy()优化
	int newCapacity = oldCapacity + (oldCapacity >> 1);
	E[] newElements = (E[]) new Object[newCapacity];
	for (int i = 0; i < size; i++) {
		newElements[i] = elements[i];
	}

	//使数组引用指向新的数组
	elements = newElements;

	System.out.println(oldCapacity + "扩容为" + newCapacity);
}

//超出边界，抛出异常
private void outOfBounds(int index) {
	throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
}

//普通的边界判断，get,set,remove
private void rangeCheck(int index) {
	if (index < 0 || index >= size) {
		outOfBounds(index);
	}
}

//添加操作的边界判断,index可以等于size,即边界索引+1
private void rangeCheckForAdd(int index) {
	if (index < 0 || index > size) {
		outOfBounds(index);
	}
}
	
@Override
public String toString() {
	// size=3, [99, 88, 77]
	StringBuilder string = new StringBuilder();
	string.append("size=").append(size).append(", [");
	for (int i = 0; i < size; i++) {
		//只要不是第一个元素，在前面拼接一个", "
		if (i != 0) {
			string.append(", ");
		}

		string.append(elements[i]);
	}
	string.append("]");
	return string.toString();
}

}
