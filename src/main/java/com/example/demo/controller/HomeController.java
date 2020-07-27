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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailaddressnow = auth.getName();

        //ユーザーリストのモデルへの登録。
		model.addAttribute("contents", "login/home :: userList_contents");
		List<User> userList = userService.selectBeforematching(mailaddressnow);
		model.addAttribute("userList", userList);

        //モデルへのログイン者の名前の登録。
        //ここで初めて、他のクラスからのメソッドの呼び出しを理解した。
        //というのも、まずサービスクラスをAutowiredし、selectOne()というメソッドを呼び出しているからである。
        //実は、サービスクラスはDaoをAutowiredしており、Daoは、Implのinterface。
        //つまり、実装の記述はImplでされており、実質的にはたらいまわしにされているようなイメージである。
        //では実装部分で何をしているかというと、jdbc.queryで直接、ＤＢからデータをとってきているのである。
        //なので、実質的なコードの部分を見たければ、ImplのselectOne()を見るべきである。
        //また、この日初めてListとmapと配列の違いを理解した。
        User a = userService.selectOne(mailaddressnow);
        String thename = a.name;
        model.addAttribute("thename", thename);
        
        int theage = userService.calcAgeAruAru(mailaddressnow);
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

	@GetMapping("/messagetomatching")
	public String Messagetomatching() {
	//マッチング画面にリダイレクト
	return "redirect:/matching";
	}
}