package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

	//登録画面のGETメソッド
	@GetMapping("/signup")
	public String getSignup(Model model) {
		return "login/signup";

	}
}