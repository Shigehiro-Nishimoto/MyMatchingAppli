
package com.example.demo.controller;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@WebServlet("/message")
@Controller
public class MessageController extends HttpServlet {

	@Autowired
	UserService userService;
	
	@Autowired
	JdbcTemplate jdbc;

	//メッセージ画面のGETメソッド
	@GetMapping("/message")
	public String getMessage(Model model) {
		return "login/message";
	}

	@PostMapping("/messagewrite")
	public String MessageWritten(Model model) {
	String written = "a";
	int matchingid = userService.CheckMatchingid();
	int a = userService.MessageWritten(written, matchingid);
	model.addAttribute("contents", "login/message :: messagetoShow_contents");
	List<Message> messagetoShow = userService.takeMessage(matchingid);
	model.addAttribute("messagetoShow", messagetoShow);
	return "login/message";
	}

	@GetMapping("/messagetomatching")
	public String Messagetomatching(Model model) {
	//マッチング画面にリダイレクト
	int matchingaitekakikomi = jdbc.update("INSERT INTO matchingaite(matchingid) VALUES(?)", 0);
	model.addAttribute("contents", "login/home :: userList_contents");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String mailaddressnow = auth.getName();
	List<User> userList = userService.selectAftermatching(mailaddressnow);
	model.addAttribute("userList", userList);
	return "login/matching";
	}
}