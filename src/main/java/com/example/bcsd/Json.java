package com.example.bcsd;

public class Json {
	private String name;
	private int age;

	public Information(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
