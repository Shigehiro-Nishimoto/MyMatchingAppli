package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;

@Repository
public interface UserDao {

public List<User> selectBeforematching(String mailaddress) throws DataAccessException;

public List<User> selectAftermatching(String mailaddress) throws DataAccessException;

public User selectOne(String mailaddress) throws DataAccessException;

public int calcAgeAruAru(String mailaddress)  throws DataAccessException;

public List<Message> takeMessage(int matchingid) throws DataAccessException;
}