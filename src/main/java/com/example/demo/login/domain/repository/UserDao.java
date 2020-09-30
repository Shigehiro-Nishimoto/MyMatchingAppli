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

public Map<String, Object> TheSelect1(String mailaddress);

public Map<String, Object> TheSelect2(int b);

public int MessageWritten(Map<String, Object> q);

public int CheckMatchingid();

public  int whosloggingin(String mailaddress);

public  int seebiggestnumber(int matchingid);

public int LeaveMessageGamen();

public int Iineshita(int matchingid, boolean sex);

public Map<String, Object> Roguinshanoidtoseibetsu(String mailaddress);

public String Hisname(int matchingid, String mailaddressnow);

public int Sakujo(int matchingid, int number);

public int Iineshitakaijo(int matchingid);

public String gaitounomesse(int matchingid, int number);

public int shuusei(String written, int number);

public int mintomaxwokaku(int min, int max);

public int matchingidshirabe();

public void yokunai(int matchingid);
}