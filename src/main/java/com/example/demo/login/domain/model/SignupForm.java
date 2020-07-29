package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org. springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupForm {

	@NotBlank(message="{require_check}")
	private String Name;
	
	private Boolean Sex;

	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date Birthday;

	@Email(message="{email_check}")
	@NotBlank(message="{require_check}")
	private String Mailaddress;

	@NotBlank(message="{require_check}")
	@Length(min=4, max=100, message="{length_check}")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="{pattern_check}")
	private String Password;
}