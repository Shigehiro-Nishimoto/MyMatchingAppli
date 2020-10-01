package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {

	public int id;
	public String name;
	private Boolean sex;
	public Date birthday;
	private String mailaddress;
	private String password;
	public int age;
	private int matchingid;
	private int state;
	private int shukantekistate;
	public boolean gaitousuruka;
	public String role;
}