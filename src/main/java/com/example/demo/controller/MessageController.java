package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Message;
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

	@PostMapping
	public String MessageWirtten(@ModelAttribute("message") Message form, Model model) {
	return "redirect:/message";
	}
}