package com.example.demo.login.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao dao;

    public boolean insert(User user) {
        int rowNumber = dao.insertOne(user);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

	public List<User> selectBeforematching(String mailaddress) {
		List<Map<String, Object>> getList = dao.getallfromMatching();
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map:getList) {
			User user = TheSelect(map, mailaddress);
	        int a = (Integer)map.get("state");
			if(a < 3) {
				if(user.gaitousuruka == true) {
					userList.add(user);
				}
			}
		}
		return userList;
	}

	public List<User> selectAftermatching(String mailaddress) {
		List<Map<String, Object>> getList = dao.getallfromMatching();
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map:getList) {
			User user = TheSelect(map, mailaddress);
	        int a = (Integer)map.get("state");
			if(a == 3) {
				if(user.gaitousuruka == true) {
					userList.add(user);
				}
			}
		}
		return userList;
	}

	public User Name(String mailaddress) {
		return dao.Name(mailaddress);
		}

	public int calcAge(String mailaddress) {
		return dao.calcAge(mailaddress);
		}

	public List<Message> takeMessage(int matchingid) {
		return dao.takeMessage(matchingid);
		}

	public User TheSelect(Map<String, Object> map, String mailaddress) throws DataAccessException {
		Map<String, Object> sexandid = dao.TheSelect1(mailaddress);
	    boolean c = (Boolean)sexandid.get("sex");
	    int d = (Integer)sexandid.get("id");
	    	int b = 0;
	    	if(c == true) {
		    	b = (Integer)map.get("femaleid");
	    	}else{
		    	b = (Integer)map.get("maleid");
	    	}

		Map<String, Object> who = dao.TheSelect2(b);

		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));
		user.setName((String)who.get("name"));
		user.setBirthday((Date)who.get("birthday"));
		int age = calcAge((String)who.get("mailaddress"));
		user.setAge(age);

		int thestate = (Integer)map.get("state");

		if(c == true) {
				if(thestate == 0){
					user.setShukantekistate(0);
				}else if(thestate == 1){
					user.setShukantekistate(1);
				}else if(thestate == 2){
					user.setShukantekistate(2);
				}else{
					user.setShukantekistate(3);
				}

			}else {
				if(thestate == 0){
					user.setShukantekistate(0);
				}else if(thestate == 1){
					user.setShukantekistate(2);
				}else if(thestate == 2){
					user.setShukantekistate(1);
				}else{
					user.setShukantekistate(3);
				}
			}

    	int e = (Integer)map.get("maleid");
    	int f = (Integer)map.get("femaleid");
    	boolean g = false;
    	if(c == true) {
	    	if(d == e) {
	    	g = true;
	    	}
    	}else{
	    	if(d == f) {
	    	g = true;
	    	}
    	}
		user.setGaitousuruka(g);
		return user;
	}

	public int MessageWritten(String written) {
	Map<String, Object> writtenall = new HashMap();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String mailaddressnow = auth.getName();
    int matchingidnow = dao.CheckMatchingid();
    int theid = dao.whosloggingin(mailaddressnow);
    int newnumber = 1 + dao.seebiggestnumber(matchingidnow);

	writtenall.put("matchingid", matchingidnow);
	writtenall.put("whospost", theid);
	writtenall.put("number", newnumber);
	writtenall.put("messagecontent", written);
	int b = dao.MessageWritten(writtenall);
	return b;
	}

	public int CheckMatchingid() {
	return dao.CheckMatchingid();
	}

	public int LeaveMessageGamen() {
	return dao.LeaveMessageGamen();
	}

	public int Iineshita(int matchingid, String mailaddress) {
		Map<String, Object> sexandid = dao.Roguinshanoidtoseibetsu(mailaddress);
		boolean c = (Boolean)sexandid.get("sex");
		return dao.Iineshita(matchingid, c);
	}
	
	public int Iineshitakaijo(int matchingid, String mailaddress) {
	return dao.Iineshitakaijo(matchingid);
	}

	public String Hisname(int matchingid, String mailaddressnow) {
	String hisname = dao.Hisname(matchingid, mailaddressnow);
	return hisname;
	}
	
	public int Sakujo(int matchingid, int number) {
	return dao.Sakujo(matchingid, number);
	}
	
	public int Roguinshanoid(String mailaddress) {
	Map<String, Object> sexandid = dao.Roguinshanoidtoseibetsu(mailaddress);
	return (Integer)sexandid.get("id");
	}
	
	public String gaitounomesse(int matchingid, int number) {
		return dao.gaitounomesse(matchingid, number);
	}
	
	public int shuusei(String written, int number) {
		return dao.shuusei(written, number);
	}
	
	public int matchingidshirabe() {
		return dao.matchingidshirabe();
	}
	
	public void Yokunai(int matchingid) {
	dao.yokunai(matchingid);
	}
}