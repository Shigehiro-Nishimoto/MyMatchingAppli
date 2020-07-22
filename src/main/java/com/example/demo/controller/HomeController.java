package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
@RequestMapping
public class HomeController {

	@Autowired
	UserService userService;

	//ホーム画面のGETメソッド
	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectBeforematching();
		model.addAttribute("userList", userList);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Principalからログインユーザの情報を取得
        String mailaddress = auth.getName();
        model.addAttribute("mailaddress", mailaddress);
        User a = userService.selectOne(mailaddress);
        String thename = a.name;
        model.addAttribute("thename", thename);
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

	@GetMapping("/messagetomatching")
	public String Messagetomatching() {
	//マッチング画面にリダイレクト
	return "redirect:/matching";
	}
}