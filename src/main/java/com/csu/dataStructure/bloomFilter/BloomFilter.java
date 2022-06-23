package com.csu.dataStructure.bloomFilter;

public class BloomFilter<T> {

	//二进制向量的长度(一共有多少个二进制位)
	private int bitSize;
	//哈希函数的个数
	private int hashSize;
	//二进制向量:long数组，每个long占64位
	private long[] bits;

	//初始化bf
	//n 数据规模；p 误判率, 取值范围(0, 1)
	public BloomFilter(int n, double p) {
		if (n <= 0 || p <= 0 || p >= 1) {
			throw new IllegalArgumentException("wrong n or p");
		}
		
		double ln2 = Math.log(2);
		// 求出二进制向量的长度
		bitSize = (int) (- (n * Math.log(p)) / (ln2 * ln2));
		// 求出哈希函数的个数
		hashSize = (int) (bitSize * ln2 / n);
		// bits数组的长度（向上取整）
		bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
	}

	//添加元素
	public boolean put(T value) {
		//非法检查，value不能为null
		nullCheck(value);

		// 利用value生成2个hash值
		int hash1 = value.hashCode();
		int hash2 = hash1 >>> 16;

		//返回值标记二进位是否发生了改变
		//只要有一位发生改变，就设为true
		boolean result = false;
		//相当于执行hashSize次不同的hash
		for (int i = 1; i <= hashSize; i++) {
			int combinedHash = hash1 + (i * hash2);
			if (combinedHash < 0) {
				//设为正数
				combinedHash = ~combinedHash;
			} 
			// 生成一个二进位的索引
			int index = combinedHash % bitSize;
			// 设置index位置的二进位为1
			if (set(index)) result = true;
		}
		return result;
	}

	//设置index位置的二进位为1
	private boolean set(int index) {
		//向下取整找到数组中所属的long
		long value = bits[index / Long.SIZE];
		//找到在该long中的位，或运算设为1
		int bitValue = 1 << (index % Long.SIZE);
		bits[index / Long.SIZE] = value | bitValue;
		//标记二进位是否发生了改变，如果之前就是1，就不会改变
		//1&1 =1 返回值为false，即没有发生改变
		return (value & bitValue) == 0;
	}

	//判断一个元素是否存在
	public boolean contains(T value) {
		nullCheck(value);
		// 利用value生成2个整数
		int hash1 = value.hashCode();
		int hash2 = hash1 >>> 16;
	
		for (int i = 1; i <= hashSize; i++) {
			int combinedHash = hash1 + (i * hash2);
			if (combinedHash < 0) {
				combinedHash = ~combinedHash;
			} 
			// 生成一个二进位的索引
			int index = combinedHash % bitSize;
			// 查询index位置的二进位是否为0
			if (!get(index)) return false;
		}
		return true;
	}

	//查看index位置的二进位的值，true代表1, false代表0
	private boolean get(int index) {
		//向下取整找到数组中所属的long
		long value = bits[index / Long.SIZE];
		//1 << (index % Long.SIZE)将制定索引为设为1
		//与运算为0，索引位为0，返回false；与运算为1，索引位为1，true
		return (value & (1 << (index % Long.SIZE))) != 0;
	}

	//提取出的参数可行性检测方法
	private void nullCheck(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value must not be null.");
		}
	}
}
