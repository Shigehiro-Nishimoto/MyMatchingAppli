package com.example.demo;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void insertNormal() {
    	   Date date = new Date();
    	    User user = new User();
    	    user.setName("あ");
    	    user.setSex(true);
    	    user.setBirthday(date);
    	    user.setMailaddress("yuyuyu@yahoo.co.jp");
    	    user.setPassword("aaaaa");
        assertEquals(userService.insert(user), true);
    }

    //異常系追加
    @Test
    public void insertIrregular1() {
    	   Date date = new Date();
    	    User user = new User();
    	    user.setName("");
    	    user.setSex(true);
    	    user.setBirthday(date);
    	    user.setMailaddress("yuyuyu@yahoo.co.jp");
    	    user.setPassword("aaaaa");
        assertEquals(userService.insert(user), true);
    }

    //異常系追加
    @Test
    public void insertIrregular2() {
    	   Date date = new Date();
    	    User user = new User();
    	    user.setName("あ");
    	    user.setSex(null);
    	    user.setBirthday(date);
    	    user.setMailaddress("yuyuyu@yahoo.co.jp");
    	    user.setPassword("aaaaa");
        assertEquals(userService.insert(user), true);
    }

    //異常系追加
    @Test
    public void insertIrregular3() {
    	    User user = new User();
    	    user.setName("あ");
    	    user.setSex(true);
    	    user.setBirthday(null);
    	    user.setMailaddress("yuyuyu@yahoo.co.jp");
    	    user.setPassword("aaaaa");
        assertEquals(userService.insert(user), true);
    }

    //異常系追加
    @Test
    public void insertIrregular4() {
    	   Date date = new Date();
    	    User user = new User();
    	    user.setName("あ");
    	    user.setSex(true);
    	    user.setBirthday(date);
    	    user.setMailaddress("null");
    	    user.setPassword("aaaaa");
        assertEquals(userService.insert(user), true);
    }

    //異常系追加
    @Test
    public void insertIrregular5() {
    	   Date date = new Date();
    	    User user = new User();
    	    user.setName("あ");
    	    user.setSex(true);
    	    user.setBirthday(date);
    	    user.setMailaddress("yuyuyu@yahoo.co.jp");
    	    user.setPassword("aaaaあ");
        assertEquals(userService.insert(user), true);
    }

    @Test
    public void selectBeforematching() {
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		User user2 = new User();

		Date birthdaykate = new Date();
		Date birthdaykim = new Date();

	       try {
	            String str1 = "2001/07/01";
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	            birthdaykate = sdFormat.parse(str1);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

	       try {
	            String str2 = "2004/07/01";
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	            birthdaykim = sdFormat.parse(str2);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

		user1.setName("ケイト");
		user1.setBirthday(birthdaykate);
		user1.setAge(19);
		user1.setMatchingid(2);
		user1.setGaitousuruka(true);

		user2.setName("キム");
		user2.setBirthday(birthdaykim);
		user2.setAge(16);
		user2.setMatchingid(3);
		user2.setGaitousuruka(true);

		userList.add(user1);
		userList.add(user2);

        assertEquals(userService.selectBeforematching("micheal@yahoo.co.jp"), userList);
    }

    @Test
    public void selectBeforematchingIrregular() {
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		User user2 = new User();

		Date birthdaykate = new Date();
		Date birthdaykim = new Date();

	       try {
	            String str1 = "2001/07/01";
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	            birthdaykate = sdFormat.parse(str1);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

	       try {
	            String str2 = "2004/07/01";
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	            birthdaykim = sdFormat.parse(str2);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

		user1.setName("ケイト");
		user1.setBirthday(birthdaykate);
		user1.setAge(19);
		user1.setMatchingid(2);
		user1.setGaitousuruka(true);

		user2.setName("キム");
		user2.setBirthday(birthdaykim);
		user2.setAge(16);
		user2.setMatchingid(3);
		user2.setGaitousuruka(true);

		userList.add(user1);
		userList.add(user2);

        assertEquals(userService.selectBeforematching("a"), userList);
    }
    
    @Test
    public void selectAftermatching() {
		List<User> userList = new ArrayList<>();
		User user1 = new User();

		Date birthdaymacy = new Date();

	       try {
	            String str1 = "1999/07/01";
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	            birthdaymacy = sdFormat.parse(str1);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

		user1.setName("メイシー");
		user1.setBirthday(birthdaymacy);
		user1.setAge(21);
		user1.setMatchingid(1);
		user1.setGaitousuruka(true);
		user1.setState(3);
		user1.setShukantekistate(3);

		userList.add(user1);

        assertEquals(userService.selectAftermatching("micheal@yahoo.co.jp"), userList);
    }

    @Test
    public void Name() {
    	User user = new User();
    	user.setName("マイケル");
        assertEquals(userService.Name("micheal@yahoo.co.jp"), user);
    }

    //異常系追加
    @Test
    public void NameNull() {
    	User user = new User();
    	user.setName("マイケル");
        assertEquals(userService.Name("null"), user);
    }

    @Test
    public void calcAge() {
        assertEquals(userService.calcAge("micheal@yahoo.co.jp"), 27);
    }

    @Test
    public void takeMessage() {
    	List<Message> list = new ArrayList<Message>();
    	Message message1 = new Message();
    	Message message2 = new Message();

        message1.setMatchingid(1);
        message1.setWhospost(1);
        message1.setNumber(1);
        message1.setMessagecontent("はじめまして！");
        message1.setSex(true);
        message2.setMatchingid(1);
        message2.setWhospost(4);
        message2.setNumber(2);
        message2.setMessagecontent("こちらこそ！");
        message2.setSex(false);

        list.add(message1);
        list.add(message2);
        assertEquals(userService.takeMessage(1), list);
    }

    @Test
    @Sql("/daotest.sql")
    @WithMockUser(username="micheal@yahoo.co.jp")
    public void MessageWritten() {
        assertEquals(userService.MessageWritten("こんにちは。"), 1);
    }

    @Test
    public void CheckMatchingid() {
        assertEquals(userService.CheckMatchingid(), 1);
    }

    @Test
    public void LeaveMessageGamen() {
        assertEquals(userService.LeaveMessageGamen(), 1);
    }

    @Test
    public void Iineshita() {
        assertEquals(userService.Iineshita(1, "micheal@yahoo.co.jp"), 1);
    }

    //異常系追加
    @Test
    public void IineshitaIrregular() {
        assertEquals(userService.Iineshita(8, "micheal@yahoo.co.jp"), 1);
    }

    //異常系追加
    @Test
    public void IineshitaIrregular2() {
        assertEquals(userService.Iineshita(1, null), 1);
    }

    @Test
    public void HisName() {
        assertEquals(userService.Hisname(1, "micheal@yahoo.co.jp"), "メイシー");
    }
}
