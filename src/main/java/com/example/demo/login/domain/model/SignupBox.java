package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation. constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupBox {

	
	@NotBlank(message="この欄は入力必須です！")
	private String name;

	@NotNull(message="男と女、どちらかを選択してください！")
	private boolean sex;

	@NotNull(message="この欄は入力必須です！")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday;

	@NotBlank(message="この欄は入力必須です！")
	@Email(message="EMail形式で入力してください！")
	private String mailaddress;

	@NotBlank(message="この欄は入力必須です！")
	@Length(min=4, max=100, message="４文字以上、１００文字以内で入力してください！")
	@Pattern(regexp = "^[a-zA-Z 0-9]+$", message="半角英数字で入力してください！")
	private String password;

}