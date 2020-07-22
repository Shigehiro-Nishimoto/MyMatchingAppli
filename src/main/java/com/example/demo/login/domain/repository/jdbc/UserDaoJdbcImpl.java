package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao{

@Autowired
JdbcTemplate jdbc;

@Override
public List<User> selectBeforematching() throws DataAccessException {
	List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
	List<User> userList = new ArrayList<>();
	
	for(Map<String, Object> map:getList) {
		
		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));

        int a = (Integer)map.get("state");

		if(a < 3) {
		userList.add(user);
		}		
	}
	return userList;
	}

@Override
public List<User> selectAftermatching() throws DataAccessException {
	List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
	List<User> userList = new ArrayList<>();
	
	for(Map<String, Object> map:getList) {
		
		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));

        int a = (Integer)map.get("state");

		if(a == 3) {
		userList.add(user);
		}		
	}
	return userList;
	}

@Override
public User selectOne(String mailaddress)throws DataAccessException {
	
	Map<String, Object> map = jdbc.queryForMap("SELECT name FROM members WHERE mailaddress = ?", mailaddress);
	User user = new User();
	user.setName((String)map.get("name"));
	return user;
	
	}
}