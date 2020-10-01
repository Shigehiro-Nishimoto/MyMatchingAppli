
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
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/message")
	public String getMessage(@ModelAttribute MessageBox form, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailaddressnow = auth.getName();
    	int matchingid = userService.CheckMatchingid();
    	model.addAttribute("contents", "login/message :: messagetoShow_contents");
    	List<Message> messagetoShow = userService.takeMessage(matchingid);
    	model.addAttribute("messagetoShow", messagetoShow);
    	String hisname = userService.Hisname(matchingid, mailaddressnow);
    	model.addAttribute("hisname", hisname);
        model.addAttribute("shuuseichuunanokana", false);
        MessageBox messe = new MessageBox();
        messe.setNowwritten("");
        model.addAttribute("messageBox", messe);
    	return "login/message";
	}

	@GetMapping("/tomessage")
	public String URLChokuuchiTaisaku1(@ModelAttribute MessageBox form, Model model) {
		model.addAttribute("contents", "login/message :: messagetoShow_contents");
		int matchingid = userService.CheckMatchingid();
		List<Message> messagetoShow = userService.takeMessage(matchingid);
		model.addAttribute("messagetoShow", messagetoShow);
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String mailaddressnow = auth.getName();
		String hisname = userService.Hisname(matchingid, mailaddressnow);
		model.addAttribute("hisname", hisname);
        MessageBox messe = new MessageBox();
        messe.setNowwritten("");
        model.addAttribute("messageBox", messe);
		return "login/message";
	}

	@GetMapping("/newmessage")
	public String URLChokuuchiTaisaku2(@ModelAttribute MessageBox form, Model model) {
		model.addAttribute("contents", "login/message :: messagetoShow_contents");
		int matchingid = userService.CheckMatchingid();
		List<Message> messagetoShow = userService.takeMessage(matchingid);
		model.addAttribute("messagetoShow", messagetoShow);
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String mailaddressnow = auth.getName();
		String hisname = userService.Hisname(matchingid, mailaddressnow);
		model.addAttribute("hisname", hisname);
        MessageBox messe = new MessageBox();
        messe.setNowwritten("");
        model.addAttribute("messageBox", messe);
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

	//■■書き込みがなされた時のルート■■
	 @PostMapping("/newmessage")
	 public String NewMessage(@ModelAttribute  @Validated MessageBox form, BindingResult bindingResult, Model model) {
	        if (bindingResult.hasErrors()) {
	        System.out.println("入力チェックにひっかかりました。");
	        return getMessage(form, model);
	        }
	        String written = form.getNowwritten();
	        int result = 0;
	        
	        	result = userService.MessageWritten(written);
	        
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
	        MessageBox messe = new MessageBox();
	        messe.setNowwritten("");
	        model.addAttribute("messageBox", messe);
	        model.addAttribute("shuuseichuunanokana", false);
	    	return "login/message";
	    }

	    @ExceptionHandler(DataAccessException.class)
	    public String dataAccessExceptionHandler(DataAccessException e, Model model) {
	        model.addAttribute("error", "内部サーバーエラー（DB）：ExceptionHandler");
	        model.addAttribute("message", "DataAccessExceptionが発生しました");
	        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
	        return "error";
	    }

	    @ExceptionHandler(Exception.class)
	    public String exceptionHandler(Exception e, Model model) {
	        model.addAttribute("error", "内部サーバーエラー：ExceptionHandler");
	        model.addAttribute("message", "Exceptionが発生しました");
	        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
	        return "error";
	    }

		@GetMapping("/sakujo/{id}")
		public String Sakujoshita(@PathVariable("id") int number,Model model) {
			int matchingid = userService.CheckMatchingid();
			userService.Sakujo(matchingid, number);
			return  "redirect:/message";
		}
		
		@GetMapping("/shuusei/{id}")
		public String Shuuseishita(@PathVariable("id") int number, @ModelAttribute MessageBox form, Model model) {
	    	model.addAttribute("shuuseichuunanokana", true);
	    	model.addAttribute("number", number);
	    	
	    	int matchingid = userService.matchingidshirabe();
	        String gaitounomesse = userService.gaitounomesse(matchingid, number);
	        MessageBox messe = new MessageBox();
	        messe.setNowwritten(gaitounomesse);
	        System.out.println(messe);
	        model.addAttribute("messageBox", messe);

	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String mailaddressnow = auth.getName();
	    	model.addAttribute("contents", "login/message :: messagetoShow_contents");
	    	List<Message> messagetoShow = userService.takeMessage(matchingid);
	    	model.addAttribute("messagetoShow", messagetoShow);
	    	String hisname = userService.Hisname(matchingid, mailaddressnow);
	    	model.addAttribute("hisname", hisname);
	    	
	    	return "login/message";
		}
		
		//■■修正がなされた時のルート■■
		 @PostMapping("/shuuseisuru/{id}")
		    public String Shuusei(@PathVariable("id") int number, @ModelAttribute  @Validated MessageBox form, BindingResult bindingResult, Model model) {
		        if (bindingResult.hasErrors()) {
		        System.out.println("入力チェックにひっかかりました。");
		        return getMessage(form, model);
		        }
		        System.out.println(number);
		        String written = form.getNowwritten();
				int matchingid = userService.CheckMatchingid();
		        userService.shuusei(written, number);
		        
		    	model.addAttribute("contents", "login/message :: messagetoShow_contents");
		    	List<Message> messagetoShow = userService.takeMessage(matchingid);
		    	model.addAttribute("messagetoShow", messagetoShow);
		        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		        String mailaddressnow = auth.getName();
		    	String hisname = userService.Hisname(matchingid, mailaddressnow);
		    	model.addAttribute("hisname", hisname);
		        model.addAttribute("shuuseichuunanokana", false);
		        MessageBox messe = new MessageBox();
		        messe.setNowwritten("");
		        model.addAttribute("messageBox", messe);
		    	return "login/message";
		    }
}