package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public String getMatching(Model model) {
		model.addAttribute("contents", "login/home :: userList_contents");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailaddressnow = auth.getName();
		List<User> userList = userService.selectAftermatching(mailaddressnow);
		model.addAttribute("userList", userList);
		return "login/matching";
	}
}