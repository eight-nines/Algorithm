package com.csu.dataStructure.dynamicArray;

public class Person {
	private int age;
	private String name;
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + "]";
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		System.out.println("Person - finalize");
	}
	
@Override
public boolean equals(Object obj) {
	if (obj == null) return false;
	//只有对方也是同类型才可以向下转型比较
	//否则会强制转型报错
	if (obj instanceof Person) {
		Person person  = (Person) obj;
		return this.age == person.age;
	}
	return false;
}
}
