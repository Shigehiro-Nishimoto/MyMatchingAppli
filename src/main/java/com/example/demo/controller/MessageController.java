package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.service.UserService;

@Controller
public class MessageController {

	@Autowired
	UserService userService;
	
	//メッセージ画面のGETメソッド
	@GetMapping("/message")
	public String getMessage(Model model) {
		return "login/message";
	}
}