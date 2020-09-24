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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.domain.model.ShiborichiBox;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
@RequestMapping
public class HomeController {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	UserService userService;

	@GetMapping("/home")
	public String getHome(@ModelAttribute ShiborichiBox form, Model model) {
		userService.LeaveMessageGamen();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailaddressnow = auth.getName();
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectBeforematching(mailaddressnow);
		model.addAttribute("userList", userList);
        String thename = userService.Name(mailaddressnow).name;
        model.addAttribute("thename", thename);
        int theage = userService.calcAge(mailaddressnow);
        model.addAttribute("theage", theage);
        return "login/home";
	}

	@GetMapping("/hometomatching")
	public String Hometomatching() {
	return "redirect:/matching";
	}

	@GetMapping("/matchingtohome")
	public String Matchingtohome() {
	return "redirect:/home";
	}

	@GetMapping("/iineshita/{id}")
	public String Iineshita(@ModelAttribute User form, Model model, @PathVariable("id") int matchingid) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String mailaddressnow = auth.getName();
    userService.Iineshita(matchingid, mailaddressnow);
	return "redirect:/home";
	}

	@GetMapping("/iineshitakaijo/{id}")
	public String Iineshitakaijo(@ModelAttribute User form, Model model, @PathVariable("id") int matchingid) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String mailaddressnow = auth.getName();

    userService.Iineshitakaijo(matchingid, mailaddressnow);
	return "redirect:/home";
	}

	@GetMapping("/hometoupload")
	public String Hometoupload() {
	return "redirect:/upload";
	}

	@PostMapping("/shiborichi")
	public String Shiborichi(@ModelAttribute ShiborichiBox form, Model model){
	int a =  form.getMin();
	int b =  form.getMax();
	System.out.println("minは、" + a);
	System.out.println("maxは、" + b);
	userService.mintomaxwokaku(a, b);
	return "redirect:/home";
	}
}