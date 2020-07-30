package com.example.demo.login.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
            // insert成功
            result = true;
        }
        return result;
    }
	
	public List<User> selectBeforematching(String mailaddress) {
		List<Map<String, Object>> getList = dao.getallfromMatching();
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map:getList) {
			User user = dao.TheSelect(map, mailaddress);
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
			User user = dao.TheSelect(map, mailaddress);
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
}