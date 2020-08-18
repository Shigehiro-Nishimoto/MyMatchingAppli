package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
@RequestMapping
public class HomeController {

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	UserService userService;

	//ホーム画面のGETメソッド
	@GetMapping("/home")
	public String getHome(Model model) {
		userService.LeaveMessageGamen();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailaddressnow = auth.getName();
        //ユーザーリストのモデルへの登録。
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectBeforematching(mailaddressnow);
		model.addAttribute("userList", userList);
        //モデルへのログイン者の名前の登録。
        User a = userService.Name(mailaddressnow);
        String thename = a.name;
        model.addAttribute("thename", thename);
        int theage = userService.calcAge(mailaddressnow);
        model.addAttribute("theage", theage);
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

	
	//【いいねに関する処理】
	
	@GetMapping("/iineshita/{id}")
	public String Iineshita(@ModelAttribute User form, Model model, @PathVariable("id") int matchingid) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String mailaddressnow = auth.getName();
    int kakikaeta = userService.Iineshita(matchingid, mailaddressnow);
	return "redirect:/home";
	}
}