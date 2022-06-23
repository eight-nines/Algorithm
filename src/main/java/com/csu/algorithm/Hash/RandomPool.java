package com.csu.algorithm.Hash;

import java.util.HashMap;

public class RandomPool {

	public static class Pool<K> {
		private HashMap<K, Integer> map1;
		private HashMap<Integer, K> map2;
		private int size;

		public Pool() {
			this.map1 = new HashMap<K, Integer>();
			this.map2 = new HashMap<Integer, K>();
			// 当前元素个数
			this.size = 0;
		}

		public void insert(K key) {
			if (!this.map1.containsKey(key)) {
				// 序号从0开始插入，每次插完size+1
				this.map1.put(key, this.size);
				this.map2.put(this.size++, key);
			}
		}

		public void delete(K key) {
			// 将key在map1,map2中删除；把最后一个序号和对应的key换到删除的位置上
			if (this.map1.containsKey(key)) {
				// 在map1中找到要删除的key对应的序号
				int deleteIndex = this.map1.get(key);
				// 找到size-1的元素,并把size-1,为后面删除元素做准备
				int lastIndex = --this.size;
				K lastKey = this.map2.get(lastIndex);

				// 对于map1,将最后一个元素对应的序号替换掉即可
				this.map1.put(lastKey, deleteIndex);
				this.map1.remove(key);

				// 对于map2,将要删除的序号对应的key替换成最后一个
				this.map2.put(deleteIndex, lastKey);
				this.map2.remove(lastIndex);
			}
		}

		public K getRandom() {
			if (this.size == 0) {
				return null;
			}
			int randomIndex = (int) (Math.random() * this.size); // 0 ~ size -1
			return this.map2.get(randomIndex);
		}

	}

	public static void main(String[] args) {
		Pool<String> pool = new Pool<String>();
		pool.insert("zuo");
		pool.insert("cheng");
		pool.insert("yun");
		System.out.println(pool.map1);
		System.out.println(pool.map2);
		pool.delete("zuo");
		System.out.println(pool.map1);
		System.out.println(pool.map2);
//		System.out.println(pool.getRandom());
//		System.out.println(pool.getRandom());
//		System.out.println(pool.getRandom());
//		System.out.println(pool.getRandom());
//		System.out.println(pool.getRandom());
//		System.out.println(pool.getRandom());

	}

}
