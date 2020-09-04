package com.example.demo;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org. springframework. test. context. jdbc. Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DaoTest {

    @Autowired
    UserDao dao;

    @Test
    public void insertOne() {
   Date date = new Date();
    User user = new User();
    user.setName("あ");
    user.setSex(true);
    user.setBirthday(date);
    user.setMailaddress("yuyuyu@yahoo.co.jp");
    user.setPassword("aaaaa");
        assertEquals(dao.insertOne(user), 1);
    }

    @Test
    public void getallfrommatching() {

    	Map<String, Object> map1 = new HashMap<>();
    	map1.put("FEMALEID", 4);
    	map1.put("MALEID", 1);
    	map1.put("STATE", 3);
    	map1.put("MATCHINGID", 1);

      	Map<String, Object> map2 = new HashMap<>();
    	map2.put("FEMALEID", 5);
    	map2.put("MALEID", 1);
    	map2.put("STATE", 0);
    	map2.put("MATCHINGID", 2);

    	Map<String, Object> map3 = new HashMap<>();
    	map3.put("FEMALEID", 6);
    	map3.put("MALEID", 1);
    	map3.put("STATE", 0);
    	map3.put("MATCHINGID", 3);

    	Map<String, Object> map4 = new HashMap<>();
    	map4.put("FEMALEID", 4);
    	map4.put("MALEID", 2);
    	map4.put("STATE", 0);
    	map4.put("MATCHINGID", 4);

    	Map<String, Object> map5 = new HashMap<>();
    	map5.put("FEMALEID", 5);
    	map5.put("MALEID", 2);
    	map5.put("STATE", 0);
    	map5.put("MATCHINGID", 5);

    	Map<String, Object> map6 = new HashMap<>();
    	map6.put("FEMALEID", 6);
    	map6.put("MALEID", 2);
    	map6.put("STATE", 0);
    	map6.put("MATCHINGID", 6);

    	Map<String, Object> map7 = new HashMap<>();
    	map7.put("FEMALEID", 4);
    	map7.put("MALEID", 3);
    	map7.put("STATE", 0);
    	map7.put("MATCHINGID", 7);

    	Map<String, Object> map8 = new HashMap<>();
    	map8.put("FEMALEID", 5);
    	map8.put("MALEID", 3);
    	map8.put("STATE", 0);
    	map8.put("MATCHINGID", 8);

    	Map<String, Object> map9 = new HashMap<>();
    	map9.put("FEMALEID", 6);
    	map9.put("MALEID", 3);
    	map9.put("STATE", 0);
    	map9.put("MATCHINGID", 9);

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        list.add(map8);
        list.add(map9);

    assertEquals(dao.getallfromMatching(), list);
    }

    @Test
    public void TheSelect1() {
    	Map<String, Object> sexandid = new HashMap<String, Object>();
    	sexandid.put("SEX", true);
    	sexandid.put("ID", 1);
        assertEquals(dao.TheSelect1("micheal@yahoo.co.jp"), sexandid);
    }

    @Test
    public void TheSelect2() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
        date = df.parse("1993/05/01");
        } catch (ParseException e) {
        e.printStackTrace();
        }

    	Map<String, Object> who = new HashMap<String, Object>();
    	who.put("ID", 1);
    	who.put("NAME", "マイケル");
    	who.put("SEX", true);
    	who.put("BIRTHDAY", date);
    	who.put("MAILADDRESS", "micheal@yahoo.co.jp");
    	who.put("PASSWORD", "$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa");
    	who.put("ROLE", "ROLE_GENERAL");
        assertEquals(dao.TheSelect2(1), who);
    }

    @Test
    public void Name() {
    	User user2 = new User();
		user2.setName("マイケル");
    	assertEquals(dao.Name("micheal@yahoo.co.jp"), user2);
    }

    @Test
    public void calAge() {
        assertEquals(dao.calcAge("micheal@yahoo.co.jp"), 27);
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

        assertEquals(dao.takeMessage(1), list);
    }

    @Test
    public void MessageWritten() {
    	Map<String, Object> writtenall = new HashMap();
    	writtenall.put("matchingid", 1);
    	writtenall.put("whospost", 1);
    	writtenall.put("number", 3);
    	writtenall.put("messagecontent", "趣味は何ですか？");
        assertEquals(dao.MessageWritten(writtenall), 1);
    }

    @Test
    @Sql("/daotest.sql")
    public void CheckMatchingid() {
        assertEquals(dao.CheckMatchingid(), 1);
    }

    @Test
    public void whosloggingin() {
        assertEquals(dao.whosloggingin("micheal@yahoo.co.jp"), 1);
    }

    @Test
    public void seebiggestnumber() {
        assertEquals(dao.seebiggestnumber(1), 2);
        assertEquals(dao.seebiggestnumber(2), 0);
    }

    @Test
    public void LeaveMessageGamen1() {
        assertEquals(dao.LeaveMessageGamen(), 0);
    }

    @Test
    @Sql("/daotest.sql")
    public void LeaveMessageGamen2() {
        assertEquals(dao.LeaveMessageGamen(), 1);
    }

    @Test
    @Sql("/daotest.sql")
    public void Roguinshanoidtoseibetsu() {
    	Map<String, Object> sexandid = new HashMap();
    	sexandid.put("ID", 1);
    	sexandid.put("SEX", true);
        assertEquals(dao.Roguinshanoidtoseibetsu("micheal@yahoo.co.jp"), sexandid);
    }

    @Test
    public void Iineshita() {
        assertEquals(dao.Iineshita(2, true), 1);
    }

    @Test
    public void Hisname() {
        assertEquals(dao.Hisname(1, "micheal@yahoo.co.jp"), "メイシー");
    }

    //異常系追加
    @Test
    @Sql("/daotest2.sql")
    public void calAge2() {
        assertEquals(dao.calcAge("1@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("2@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("3@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("4@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("5@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("6@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("7@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("8@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("9@yahoo.co.jp"), 27);
        assertEquals(dao.calcAge("10@yahoo.co.jp"), 26);
        assertEquals(dao.calcAge("11@yahoo.co.jp"), 26);
        assertEquals(dao.calcAge("12@yahoo.co.jp"), 26);
    }
}