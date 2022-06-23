package com.csu.algorithm.QueueStack;

import java.util.LinkedList;
import java.util.Queue;

public class DogCatQueue {

	public static class Pet {
		private String type;

		public Pet(String type) {
			this.type = type;
		}

		public String getPetType() {
			return this.type;
		}
	}

	public static class Dog extends Pet {
		public Dog() {
			super("dog");
		}
	}

	public static class Cat extends Pet {
		public Cat() {
			super("cat");
		}
	}

	// 扩展信息类
	public static class PetEnterQueue {
		private Pet pet;
		private long count;

		public PetEnterQueue(Pet pet, long count) {
			this.pet = pet;
			this.count = count;
		}

		public Pet getPet() {
			return this.pet;
		}

		public long getCount() {
			return this.count;
		}

		public String getEnterPetType() {
			return this.pet.getPetType();
		}
	}

	// 真正的容器
	public static class realDogCatQueue {
		// 两种动物分别创建一个队列，元素为扩展信息类
		// 包含对象本身，和进入队列的序号
		private Queue<PetEnterQueue> dogQ;
		private Queue<PetEnterQueue> catQ;
		// 标志整个容器中，添加的对象的序号
		// 如初始为0，添加狗时，使用this.count++
		// 则，此狗序号为0，并+1，下次添加其他对象，序号为1
		private long count;

		// 构造函数，初始化容器
		// 为每个动物类型创建一个空的扩展信息类队列
		// count 初始为0
		public realDogCatQueue() {
			this.dogQ = new LinkedList<PetEnterQueue>();
			this.catQ = new LinkedList<PetEnterQueue>();
			this.count = 0;
		}

		// 容器添加方法
		public void add(Pet pet) {
			// 添加狗、猫、其他
			if (pet.getPetType().equals("dog")) {
				// 每次添加信息类对象，其中this.count++表示当前添加的对象是第几个
				this.dogQ.add(new PetEnterQueue(pet, this.count++));
			} else if (pet.getPetType().equals("cat")) {
				this.catQ.add(new PetEnterQueue(pet, this.count++));
			} else {
				throw new RuntimeException("err, not dog or cat");
			}
		}

		public Pet pollAll() {
			if (!this.dogQ.isEmpty() && !this.catQ.isEmpty()) {
				// 使用队列中扩展类中的getCount获得对象添加时的序号
				// 序号小的为先进的对象，先弹出
				if (this.dogQ.peek().getCount() < this.catQ.peek().getCount()) {
					return this.dogQ.poll().getPet();
				} else {
					return this.catQ.poll().getPet();
				}
			} else if (!this.dogQ.isEmpty()) {
				return this.dogQ.poll().getPet();
			} else if (!this.catQ.isEmpty()) {
				return this.catQ.poll().getPet();
			} else {
				throw new RuntimeException("err, queue is empty!");
			}
		}

		public Dog pollDog() {
			if (!this.isDogQueueEmpty()) {
				return (Dog) this.dogQ.poll().getPet();
			} else {
				throw new RuntimeException("Dog queue is empty!");
			}
		}

		public Cat pollCat() {
			if (!this.isCatQueueEmpty()) {
				return (Cat) this.catQ.poll().getPet();
			} else
				throw new RuntimeException("Cat queue is empty!");
		}

		public boolean isEmpty() {
			return this.dogQ.isEmpty() && this.catQ.isEmpty();
		}

		public boolean isDogQueueEmpty() {
			return this.dogQ.isEmpty();
		}

		public boolean isCatQueueEmpty() {
			return this.catQ.isEmpty();
		}

	}

//	public static void main(String[] args) {
//		DogCatQueue test = new DogCatQueue();
//
//		Pet dog1 = new Dog();
//		Pet cat1 = new Cat();
//		Pet dog2 = new Dog();
//		Pet cat2 = new Cat();
//		Pet dog3 = new Dog();
//		Pet cat3 = new Cat();
//
//		test.add(dog1);
//		test.add(cat1);
//		test.add(dog2);
//		test.add(cat2);
//		test.add(dog3);
//		test.add(cat3);
//
//		test.add(dog1);
//		test.add(cat1);
//		test.add(dog2);
//		test.add(cat2);
//		test.add(dog3);
//		test.add(cat3);
//
//		test.add(dog1);
//		test.add(cat1);
//		test.add(dog2);
//		test.add(cat2);
//		test.add(dog3);
//		test.add(cat3);
//		while (!test.isDogQueueEmpty()) {
//			System.out.println(test.pollDog().getPetType());
//		}
//		while (!test.isEmpty()) {
//			System.out.println(test.pollAll().getPetType());
//		}
//	}

}
