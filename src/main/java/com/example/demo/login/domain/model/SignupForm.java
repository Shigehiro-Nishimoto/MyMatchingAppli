package com.example.demo.login.domain.model;

import java.util.Date;

import org. springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupForm {
	
	private String Name;
	private Boolean Sex;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date Birthday;
	private String EMail;
	private String Password;
}