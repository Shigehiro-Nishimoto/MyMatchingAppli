package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	//ホーム画面のGETメソッド
	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectBeforematching();
		model.addAttribute("userList", userList);
		return "login/home";
	}
	
	@GetMapping("/hometomatching")
	public String Hometomatching() {
	//マッチング画面にリダイレクト
	return "redirect:/matching";
	}
	
	@GetMapping("/matchingtohome")
	public String Matchingtohome() {
	//ホーム画面にリダイレクト
	return "redirect:/home";
	}

}