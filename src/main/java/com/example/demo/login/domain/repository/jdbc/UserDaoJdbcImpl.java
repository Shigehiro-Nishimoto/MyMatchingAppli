package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao{

@Autowired
JdbcTemplate jdbc;

@Override
public List<User> selectBeforematching(String mailaddress) throws DataAccessException {
	List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
	List<User> userList = new ArrayList<>();

	for(Map<String, Object> map:getList) {

		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));

    	Map<String, Object> sex = jdbc.queryForMap("SELECT sex FROM members WHERE mailaddress = ?", mailaddress);
    	boolean c = (Boolean)sex.get("sex");
    	//今ログインしている人が男性なら、cは、trueである。

    	int b = 0;

    	if(c == true) {
    	b = (Integer)map.get("femaleid");
    	}else{
    	b = (Integer)map.get("maleid");
    	}

		Map<String, Object> who = jdbc.queryForMap("SELECT * FROM members WHERE id = ?", b);

		user.setName((String)who.get("name"));
		user.setBirthday((Date)who.get("birthday"));

		//ここから
		int age = 0;
		Date birthdaynow = (Date)who.get("birthday");

		Calendar birthdaycalendar = Calendar.getInstance();
		birthdaycalendar.setTime(birthdaynow);
		int bornyear = birthdaycalendar.get(Calendar.YEAR);

	    Date nowTime = new Date();
	    Calendar nowtime = Calendar.getInstance();
	    nowtime.setTime(nowTime);
		int thisyear = nowtime.get(Calendar.YEAR);

		age = thisyear - bornyear;
		user.setAge(age);
		//ここまで

        int a = (Integer)map.get("state");

    	Map<String, Object> id = jdbc.queryForMap("SELECT id FROM members WHERE mailaddress = ?", mailaddress);
    	int d = (Integer)id.get("id");
    	//dは、今ログインしている人のid。
    	int e = (Integer)map.get("maleid");
    	int f = (Integer)map.get("femaleid");
    	//eとfは、今みているマッチング表のレコードの、それぞれmaleidと、femaleidをとってきた。
    	boolean g = false;
    	//boolean gとは、今見ているレコードが、ログイン者に対して該当するかどうか見る。
    	//以下で、gに、該当レコードならtrueという記述をする。
    	if(c == true) {
	    	if(d == e) {
	    	g = true;
	    	}
    	}else{
	    	if(d == f) {
	    	g = true;
	    	}
    	}


    	//状態が０～２かつログイン者と関係あるレコードなら、userListに追加。
		if(a < 3) {
			if(g == true) {
				userList.add(user);
			}
		}
	}
	return userList;
	}

@Override
public List<User> selectAftermatching(String mailaddress) throws DataAccessException {
	List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
	List<User> userList = new ArrayList<>();

	for(Map<String, Object> map:getList) {

		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));

    	Map<String, Object> sex = jdbc.queryForMap("SELECT sex FROM members WHERE mailaddress = ?", mailaddress);
    	boolean c = (Boolean)sex.get("sex");
    	//今ログインしている人が男性なら、cは、trueである。

    	int b = 0;

    	if(c == true) {
    	b = (Integer)map.get("femaleid");
    	}else{
    	b = (Integer)map.get("maleid");
    	}

		Map<String, Object> who = jdbc.queryForMap("SELECT * FROM members WHERE id = ?", b);

		user.setName((String)who.get("name"));
		user.setBirthday((Date)who.get("birthday"));

		//ここから
		int age = 0;
		Date birthdaynow = (Date)who.get("birthday");

		Calendar birthdaycalendar = Calendar.getInstance();
		birthdaycalendar.setTime(birthdaynow);
		int bornyear = birthdaycalendar.get(Calendar.YEAR);

	    Date nowTime = new Date();
	    Calendar nowtime = Calendar.getInstance();
	    nowtime.setTime(nowTime);
		int thisyear = nowtime.get(Calendar.YEAR);

		age = thisyear - bornyear;
		user.setAge(age);
		//ここまで

        int a = (Integer)map.get("state");

    	Map<String, Object> id = jdbc.queryForMap("SELECT id FROM members WHERE mailaddress = ?", mailaddress);
    	int d = (Integer)id.get("id");
    	//dは、今ログインしている人のid。
    	int e = (Integer)map.get("maleid");
    	int f = (Integer)map.get("femaleid");
    	//eとfは、今みているマッチング表のレコードの、それぞれmaleidと、femaleidをとってきた。
    	boolean g = false;
    	//boolean gとは、今見ているレコードが、ログイン者に対して該当するかどうか見る。
    	//以下で、gに、該当レコードならtrueという記述をする。
    	if(c == true) {
	    	if(d == e) {
	    	g = true;
	    	}
    	}else{
	    	if(d == f) {
	    	g = true;
	    	}
    	}


    	//状態が３かつログイン者と関係あるレコードなら、userListに追加。
		if(a == 3) {
			if(g == true) {
				userList.add(user);
			}
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

@Override
public int calcAgeAruAru(String mailaddress) throws DataAccessException {
	int age = 0;
	Map<String, Object> map = jdbc.queryForMap("SELECT birthday FROM members WHERE mailaddress = ?", mailaddress);
	Date birthdaynow = (Date)map.get("birthday");

	Calendar birthdaycalendar = Calendar.getInstance();
	birthdaycalendar.setTime(birthdaynow);
	int bornyear = birthdaycalendar.get(Calendar.YEAR);

    Date nowTime = new Date();
    Calendar nowtime = Calendar.getInstance();
    nowtime.setTime(nowTime);
	int thisyear = nowtime.get(Calendar.YEAR);

	age = thisyear - bornyear;

	return age;
	}


@Override
public List<Message> takeMessage(int matchingid) throws DataAccessException {
	List<Map<String, Object>> getMessage = jdbc.queryForList("SELECT * FROM message WHERE matchingid = ?");
	List<Message> Message = new ArrayList<>();

	for(Map<String, Object> map:getMessage) {

		Message onemessage = new Message();
		onemessage.setMatchingid((Integer)map.get("matchingid"));
		onemessage.setWhospost((Integer)map.get("whospost"));
		onemessage.setNumber((Integer)map.get("number"));
		onemessage.setMessage((String)map.get("message"));

		Message.add(onemessage);
	}

	return Message;
		}
}