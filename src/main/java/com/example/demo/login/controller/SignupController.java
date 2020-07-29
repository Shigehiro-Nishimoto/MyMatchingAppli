package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

	@Autowired
	UserService userService;
	
	//ラジオボタンの生成
	private Map <String, String> radioSex;
	private Map <String, String> initRadioSex() {
		Map < String, String > radio = new LinkedHashMap <>();
		radio.put("男", "true");
		radio.put("女", "false");

		return radio;
	}

	//登録画面のGETメソッド
	@GetMapping("/signup")
	public String getSignup(SignupForm form, Model model) {
		
		radioSex = initRadioSex();
		model.addAttribute("radioSex", radioSex);
		
		return "login/signup";
	}
	
	 @PostMapping("/signup")
	public String postSignup(@ModelAttribute SignupForm form, BindingResult bindingResult, Model model) {

		if ( bindingResult. hasErrors() ) {
			return getSignup(form, model);
		}
		User user = new User();
		user.setName(form.getName());
		user.setPassword(form.getPassword());
		user.setName(form.getMailaddress());
		user.setSex(form.getSex());
		user.setBirthday(form.getBirthday());
		user.setPassword(form.getPassword());
		
		boolean result = userService.insert(user);

		if(result == true) {
		System.out.println("Insert成功");
		}else {
		System.out.println("Insert失敗");
		}

		return "redirect:/login";
	 }

}