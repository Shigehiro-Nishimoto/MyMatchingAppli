package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.service.UserService;

	@Controller
	public class LoginController{
		
	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String getLogin(Model model){
		return "login/login";
	}

	@PostMapping("/login")
	public String postLogin(Model model){
		return "redirect:/home";
   }
}