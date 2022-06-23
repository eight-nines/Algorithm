package com.csu.algorithm.QueueStack;


import com.sun.jmx.remote.internal.ArrayQueue;

public class Array_To_Stack_Queue {

	public static class ArrayStack {
		// 声明栈基于的数组
		private Integer[] arr;
		// 栈的长度、此coding也用来当做指针
		private Integer size;

		// 初始化栈信息
		public ArrayStack(int initSize) {
			if (initSize < 0) {
				throw new IllegalArgumentException("The init size is less than 0");
			}
			arr = new Integer[initSize];
			// 长度为0，指针也指向0
			size = 0;
		}

		// 实现查看栈顶元素的方法
		public Integer peek() {
			if (size == 0) {
				return null;
			}
			return arr[size - 1];
		}

		// 栈顶增加数据
		public void push(int obj) {
			if (size == arr.length) {
				throw new ArrayIndexOutOfBoundsException("The queue is full");
			}
			arr[size++] = obj;
		}

		// 移除栈顶数据
		public Integer pop() {
			if (size == 0) {
				throw new ArrayIndexOutOfBoundsException("The queue is empty");
			}
			return arr[--size];
		}
	}




	public static class ArrayQueue {
		// 声明队列基于的数组
		private Integer[] arr;
		// 队列的长度
		private Integer size;
		// 两个标记队列起点和终点的指针
		private Integer start;
		private Integer end;

		// 初始化队列信息
		public ArrayQueue(int initSize) {
			if (initSize < 0) {
				throw new IllegalArgumentException("The init size is less than 0");
			}
			// 初始化数组
			arr = new Integer[initSize];
			// 初始化队列信息
			size = 0;
			start = 0;
			end = 0;
		}

		// 查：返回先进值即start指针位置值
		public Integer peek() {
			if (size == 0) {
				return null;
			}
			return arr[start];
		}

		// 增：向end后一位添加值
		public void push(int obj) {
			if (size == arr.length) {
				throw new ArrayIndexOutOfBoundsException("The queue is full");
			}
			size++;
			arr[end] = obj;
			// 如果end位置在数组最后一位，则回头数组开头继续存数据
			end = end == arr.length - 1 ? 0 : end + 1;
		}

		// 删：删除start指针位置数,并返回数据
		public Integer poll() {
			if (size == 0) {
				throw new ArrayIndexOutOfBoundsException("The queue is empty");
			}
			size--;
			int tmp = start;
			// 如果start位置在数组最后一位，则回头数组开头继续存数据
			start = start == arr.length - 1 ? 0 : start + 1;
			return arr[tmp];
		}
	}




	public static void main(String[] args) {
		ArrayQueue aq  = new ArrayQueue(5);
		aq.push(5);
		System.out.println(aq.peek());
	}

}
