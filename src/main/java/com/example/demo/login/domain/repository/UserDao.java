package com.example.demo.login.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;

@Repository
public interface UserDao {

public int insertOne(User user) throws DataAccessException;

public List<Map<String, Object>> getallfromMatching() throws DataAccessException;

public User Name(String mailaddress) throws DataAccessException;

public int calcAge(String mailaddress)  throws DataAccessException;

public List<Message> takeMessage(int matchingid) throws DataAccessException;

public Map<String, Object> TheSelect1(Map<String, Object> map, String mailaddress);

public Map<String, Object> TheSelect2(Map<String, Object> map, int b);
}