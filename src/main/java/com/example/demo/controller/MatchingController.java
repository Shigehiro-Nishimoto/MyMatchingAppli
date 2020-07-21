package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class MatchingController {

	@Autowired
	UserService userService;

	//マッチング画面のGETメソッド
	@GetMapping("/matching")
	public String getHome(Model model) {
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectAftermatching();
		model.addAttribute("userList", userList);
		return "login/matching";
	}
}