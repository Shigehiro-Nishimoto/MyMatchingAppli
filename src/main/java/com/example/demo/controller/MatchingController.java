package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.MessageBox;
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

	@PostMapping("/tomessage")
	public String Tomessage(HttpServletRequest request, @ModelAttribute MessageBox form, Model model) {
		model.addAttribute("contents", "login/message :: messagetoShow_contents");
		int matchingid = Integer.parseInt(request.getParameter("matchingid"));
		List<Message> messagetoShow = userService.takeMessage(matchingid);
		model.addAttribute("messagetoShow", messagetoShow);
		
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String mailaddressnow = auth.getName();
		String hisname = userService.Hisname(matchingid, mailaddressnow);
		model.addAttribute("hisname", hisname);
		model.addAttribute("shuuseichuunanokana", false);
		return "login/message";
	}
}