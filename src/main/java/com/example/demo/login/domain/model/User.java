package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {

	private int id;
	public String name;
	private Boolean sex;
	public Date birthday;
	private String mailaddress;
	private String password;
	private int age;
	private int matchingid;
	private int state;
}