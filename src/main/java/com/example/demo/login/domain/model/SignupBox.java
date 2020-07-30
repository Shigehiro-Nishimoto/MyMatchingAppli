package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;


@Data
public class SignupBox {

	private String name;
	private boolean sex;
	private Date birthday;
	private String mailaddress;
	private String password;

}
