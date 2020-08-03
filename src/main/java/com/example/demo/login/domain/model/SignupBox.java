package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation. constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org. springframework. format. annotation. DateTimeFormat;

import lombok.Data;

@Data
public class SignupBox {

	
	@NotBlank
	private String name;

	@NotNull
	private boolean sex;

	@NotNull
	@DateTimeFormat(pattern ="yyyy/MM/dd")
	private Date birthday;

	@NotBlank
	@Email
	private String mailaddress;

	@NotBlank
	@Length(min=4, max=100)
	@Pattern(regexp = "^[a-zA-Z 0-9]+$")
	private String password;

}