package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	//ホーム画面のGETメソッド
	@GetMapping("/home")
	public String getHome(Model model) {
		return "login/home";
	}
}