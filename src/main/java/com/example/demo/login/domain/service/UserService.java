package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {

@Autowired
UserDao dao;

public List<User> selectBeforematching(String mailaddress) {
return dao.selectBeforematching(mailaddress);
	}

public List<User> selectAftermatching(String mailaddress) {
return dao.selectAftermatching(mailaddress);
	}

public User selectOne(String mailaddress) {
	return dao.selectOne(mailaddress);
	}

public int calcAgeAruAru(String mailaddress) {
	return dao.calcAgeAruAru(mailaddress);
	}

public List<Message> takeMessage(int matchingid) {
	return dao.takeMessage(matchingid);
	}
}