
package com.example.demo.controller;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.MessageBox;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailaddressnow = auth.getName();
    	int matchingid = userService.CheckMatchingid();
    	model.addAttribute("contents", "login/message :: messagetoShow_contents");
    	List<Message> messagetoShow = userService.takeMessage(matchingid);
    	model.addAttribute("messagetoShow", messagetoShow);
    	String hisname = userService.Hisname(matchingid, mailaddressnow);
    	model.addAttribute("hisname", hisname);
    	return "login/message";
	}

	@GetMapping("/messagetomatching")
	public String Messagetomatching(Model model) {
	//マッチング画面にリダイレクト
	model.addAttribute("contents", "login/home :: userList_contents");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String mailaddressnow = auth.getName();
	List<User> userList = userService.selectAftermatching(mailaddressnow);
	model.addAttribute("userList", userList);
	userService.LeaveMessageGamen();
	return "login/matching";
	}

	 @PostMapping("/newmessage")
	    public String postSignUp(@ModelAttribute  @Validated MessageBox form, BindingResult bindingResult, Model model) {
	        //入力チェックに引っかかった場合、ユーザー登録画面に戻る
	        if (bindingResult.hasErrors()) {
	        System.out.println("入力チェックにひっかかりました。");
	        return getMessage(model);
	        }

	        String written = form.getNowwritten();

	        int result = userService.MessageWritten(written);

	        if (result > 0) {
	            System.out.println("書き込み成功");
	        } else {
	            System.out.println("書き込み失敗");
	        }
	    	int matchingid = userService.CheckMatchingid();
	    	model.addAttribute("contents", "login/message :: messagetoShow_contents");
	    	List<Message> messagetoShow = userService.takeMessage(matchingid);
	    	model.addAttribute("messagetoShow", messagetoShow);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String mailaddressnow = auth.getName();
	    	String hisname = userService.Hisname(matchingid, mailaddressnow);
	    	model.addAttribute("hisname", hisname);
	    	return "login/message";
	    }

		//DataAccessException発生時の処理メソッド
	    @ExceptionHandler(DataAccessException.class)
	    public String dataAccessExceptionHandler(DataAccessException e, Model model) {
	        model.addAttribute("error", "内部サーバーエラー（DB）：ExceptionHandler");
	        model.addAttribute("message", "DataAccessExceptionが発生しました");
	        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
	        return "error";
	    }

	    //Exception発生時の処理メソッド
	    @ExceptionHandler(Exception.class)
	    public String exceptionHandler(Exception e, Model model) {
	        model.addAttribute("error", "内部サーバーエラー：ExceptionHandler");
	        model.addAttribute("message", "Exceptionが発生しました");
	        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
	        return "error";
	    }
}