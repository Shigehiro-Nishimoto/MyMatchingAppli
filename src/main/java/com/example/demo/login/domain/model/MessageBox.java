package com.example.demo.login.domain.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class MessageBox {

@NotBlank(message="入力してください！")
@Length(max=100, message="１００文字以内で入力してください！")
private String nowwritten;

}
