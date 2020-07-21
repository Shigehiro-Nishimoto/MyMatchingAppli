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
public List<User> selectMany() throws DataAccessException {
	List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
	List<User> userList = new ArrayList<>();
	for(Map<String, Object> map:getList) {
		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));
		userList.add(user);
	}
	return userList;
	}
}