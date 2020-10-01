package com.example.demo.controller;

import java.util.ArrayList;
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
        int a = -1;
        int b = 200;
        boolean shibotteru = false;
	    model.addAttribute("min", a);
	    model.addAttribute("max", b);
	    model.addAttribute("shibotteru", shibotteru);
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

	@GetMapping("/iineshita/{id}/{id1}/{id2}/{id3}")
	public String Iineshita(@ModelAttribute ShiborichiBox form1, @ModelAttribute User form2,
			@PathVariable("id") int matchingid, @PathVariable("id1") int min, @PathVariable("id2") int max, 
			@PathVariable("id3") boolean shibotteru, Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String mailaddressnow = auth.getName();
	    userService.Iineshita(matchingid, mailaddressnow);
	    if(shibotteru == false) {
		return "redirect:/home";
	    }else {
			int a =  min;
			int b =  max;
			System.out.println("minは、" + a + "で、maxは" + b);
			model.addAttribute("min", a);
			model.addAttribute("max", b);
			userService.LeaveMessageGamen();
			model.addAttribute("contents", "login/home :: userList_contents");
			List<User> userList = userService.selectBeforematching(mailaddressnow);
		
			//年齢の絞り込みから外れたuserを外す記述
			//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			List<User> remover = new ArrayList<User>();
			for(int i = 0 ; i < userList.size() ; i ++) {
					if(userList.get(i).age >= a && userList.get(i).age <= b) {
				}else {
					remover.add(userList.get(i));
				}
			}
			System.out.println(remover);
			userList.removeAll(remover);
			model.addAttribute("userList", userList);
			//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
		    String thename = userService.Name(mailaddressnow).name;
		    model.addAttribute("thename", thename);
		    int theage = userService.calcAge(mailaddressnow);
		    model.addAttribute("theage", theage);
		    model.addAttribute("min", a);
		    model.addAttribute("max", b);
		    model.addAttribute("shibotteru", true);
		    return "login/home";
	    }
	}

	@GetMapping("/iineshitakaijo/{id}/{id1}/{id2}/{id3}")
		public String Iineshitakaijo(@ModelAttribute ShiborichiBox form1, @ModelAttribute User form2,
				@PathVariable("id") int matchingid, @PathVariable("id1") int min, @PathVariable("id2") int max, 
				@PathVariable("id3") boolean shibotteru, Model model) {
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String mailaddressnow = auth.getName();
		    userService.Iineshitakaijo(matchingid, mailaddressnow);
		    if(shibotteru == false) {
				return "redirect:/home";
			    }else {
					int a =  min;
					int b =  max;
					System.out.println("minは、" + a + "で、maxは" + b);
					model.addAttribute("min", a);
					model.addAttribute("max", b);
					userService.LeaveMessageGamen();
					model.addAttribute("contents", "login/home :: userList_contents");
					List<User> userList = userService.selectBeforematching(mailaddressnow);
				
					//年齢の絞り込みから外れたuserを外す記述
					//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					List<User> remover = new ArrayList<User>();
					for(int i = 0 ; i < userList.size() ; i ++) {
							if(userList.get(i).age >= a && userList.get(i).age <= b) {
						}else {
							remover.add(userList.get(i));
						}
					}
					System.out.println(remover);
					userList.removeAll(remover);
					model.addAttribute("userList", userList);
					//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				    String thename = userService.Name(mailaddressnow).name;
				    model.addAttribute("thename", thename);
				    int theage = userService.calcAge(mailaddressnow);
				    model.addAttribute("theage", theage);
				    model.addAttribute("min", a);
				    model.addAttribute("max", b);
				    model.addAttribute("shibotteru", true);
				    return "login/home";
			    }
	}

	@GetMapping("/hometoupload")
	public String Hometoupload() {
		return "redirect:/upload";
	}

	@PostMapping("/shiborichi")
	public String Shiborichi(@ModelAttribute ShiborichiBox form, Model model){
		int a =  form.getMin();
		int b =  form.getMax();
		System.out.println("minは、" + a + "で、maxは" + b);
		model.addAttribute("min", a);
		model.addAttribute("max", b);
		userService.LeaveMessageGamen();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String mailaddressnow = auth.getName();
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectBeforematching(mailaddressnow);
	
		//年齢の絞り込みから外れたuserを外す記述
		//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
		List<User> remover = new ArrayList<User>();
		for(int i = 0 ; i < userList.size() ; i ++) {
				if(userList.get(i).age >= a && userList.get(i).age <= b) {
			}else {
				remover.add(userList.get(i));
			}
		}
		System.out.println(remover);
		userList.removeAll(remover);
		model.addAttribute("userList", userList);
		//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
		
	    String thename = userService.Name(mailaddressnow).name;
	    model.addAttribute("thename", thename);
	    int theage = userService.calcAge(mailaddressnow);
	    model.addAttribute("theage", theage);
	    model.addAttribute("min", a);
	    model.addAttribute("max", b);
	    model.addAttribute("shibotteru", true);
	    return "login/home";
	}

	@GetMapping("/yokunai/{id}/{id1}/{id2}/{id3}")
	public String Yokunai(@ModelAttribute ShiborichiBox form1, @ModelAttribute User form2,
			@PathVariable("id") int matchingid, @PathVariable("id1") int min, @PathVariable("id2") int max, 
			@PathVariable("id3") boolean shibotteru, Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String mailaddressnow = auth.getName();
	    userService.Yokunai(matchingid);
	    if(shibotteru == false) {
			return "redirect:/home";
		    }else {
				int a =  min;
				int b =  max;
				System.out.println("minは、" + a + "で、maxは" + b);
				model.addAttribute("min", a);
				model.addAttribute("max", b);
				userService.LeaveMessageGamen();
				model.addAttribute("contents", "login/home :: userList_contents");
				List<User> userList = userService.selectBeforematching(mailaddressnow);
			
				//年齢の絞り込みから外れたuserを外す記述
				//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				List<User> remover = new ArrayList<User>();
				for(int i = 0 ; i < userList.size() ; i ++) {
						if(userList.get(i).age >= a && userList.get(i).age <= b) {
					}else {
						remover.add(userList.get(i));
					}
				}
				System.out.println(remover);
				userList.removeAll(remover);
				model.addAttribute("userList", userList);
				//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			    String thename = userService.Name(mailaddressnow).name;
			    model.addAttribute("thename", thename);
			    int theage = userService.calcAge(mailaddressnow);
			    model.addAttribute("theage", theage);
			    model.addAttribute("min", a);
			    model.addAttribute("max", b);
			    model.addAttribute("shibotteru", true);
			    return "login/home";
		    }
	}
}