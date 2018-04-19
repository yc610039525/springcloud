package com.github.caoyeung.model;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer age;
	private String email;
	private Integer phone;
	public User(){}
	public User(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPhone() {
		return phone;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", phone=" + phone + "]";
	}
	
	
}
