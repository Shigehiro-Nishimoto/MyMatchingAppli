package com.example.demo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.repository.UserDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDaoTest {

    @Autowired
    UserDao dao;

    @Test
    public void Test() {

        assertEquals(dao.calcAge("micheal@yahoo.co.jp"), 27);
    }
}