package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupBox;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

	@Autowired
	UserService userService;

	private Map <String, String> radioSex;
	private Map <String, String> initRadioSex() {
		Map < String, String > radio = new LinkedHashMap <>();
		radio.put("男", "true");
		radio.put("女", "false");
		return radio;
	}

	//登録画面のメソッド
	@GetMapping("/signup")
    public String getSignUp(@ModelAttribute SignupBox form, Model model) {
        radioSex = initRadioSex();
        model.addAttribute("radioSex", radioSex);
        return "login/signup";
    }

	 @PostMapping("/signup")
	    public String postSignUp(@ModelAttribute  @Validated SignupBox form, BindingResult bindingResult, Model model) {
	        //入力チェックに引っかかった場合、ユーザー登録画面に戻る
	        if (bindingResult.hasErrors()) {
	        //GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
	            return getSignUp(form, model);
	        }
	        User user = new User();	        
	        user.setName(form.getName());
	        user.setSex(form.isSex());
	        user.setBirthday(form.getBirthday());
	        user.setMailaddress(form.getMailaddress());
	        user.setPassword(form.getPassword());
	        user.setRole("ROLE_GENERAL");
	        user.setAge(1);
	        user.setMatchingid(1);
	        user.setState(1);
	        user.setGaitousuruka(true);
	        user.setId(1);
	        boolean result = userService.insert(user);

	        // ユーザー登録結果の判定
	        if (result == true) {
	            System.out.println("insert成功");
	        } else {
	            System.out.println("insert失敗");
	        }
	        // login.htmlにリダイレクト
	        return "redirect:/login";
	    }
	 
		//DataAccessException発生時の処理メソッド
	    @ExceptionHandler(DataAccessException.class)
	    public String dataAccessExceptionHandler(DataAccessException e, Model model) {

	        // 例外クラスのメッセージをModelに登録
	        model.addAttribute("error", "内部サーバーエラー（DB）：ExceptionHandler");

	        // 例外クラスのメッセージをModelに登録
	        model.addAttribute("message", "DataAccessExceptionが発生しました");

	        // HTTPのエラーコード（500）をModelに登録
	        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

	        return "error";
	    }
	    //Exception発生時の処理メソッド
	    @ExceptionHandler(Exception.class)
	    public String exceptionHandler(Exception e, Model model) {

	        // 例外クラスのメッセージをModelに登録
	        model.addAttribute("error", "内部サーバーエラー：ExceptionHandler");

	        // 例外クラスのメッセージをModelに登録
	        model.addAttribute("message", "Exceptionが発生しました");

	        // HTTPのエラーコード（500）をModelに登録
	        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

	        return "error";
	    }
}