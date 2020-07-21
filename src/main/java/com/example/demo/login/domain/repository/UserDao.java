package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository
public interface UserDao {

public List<User> selectMany() throws DataAccessException;

}