package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao{

@Autowired
JdbcTemplate jdbc;

@Autowired
PasswordEncoder passwordEncoder;

	//●●●●●●●●ここから制作中●●●●●●●●
	//●●●●●●●●ここから制作中●●●●●●●●
	@Override
	public int insertOne(User user) throws DataAccessException {
	    String password = passwordEncoder.encode(user.getPassword());
	   	Map<String, Object> biggestid = jdbc.queryForMap("SELECT MAX(id) FROM members");
		int biggestid2 = (Integer)biggestid.get("MAX(id)");
		System.out.println(biggestid2);
		int newid = 1 + biggestid2;
	    int rowNumber = jdbc.update("INSERT INTO members(id, name, sex, birthday, mailaddress, password, role) VALUES(?, ?, ?, ?, ?, ?, ?)", newid, user.getName(), user.getSex(), user.getBirthday(), user.getMailaddress(), password, user.getRole());
	    //マッチング表も必要な数だけレコードを増やす
	   	Map<String, Object> biggestmatchingid = jdbc.queryForMap("SELECT MAX(matchingid) FROM matchings");
		int biggestmatchingid2 = (Integer)biggestmatchingid.get("MAX(matchingid)");
		int newmatchingid = biggestmatchingid2 + 1;
	    return rowNumber;
	}
	//●●●●●●●●ここまで制作中●●●●●●●●
	//●●●●●●●●ここまで制作中●●●●●●●●



	//●●マッチング表のデータを全て取得するメソッド●●
	@Override
	public List<Map<String, Object>> getallfromMatching() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
		return getList;
		}

	//●●渡されたマッチング表のレコードから、User型のデータを完成させるメソッド１●●
	public Map<String, Object> TheSelect1(Map<String, Object> map, String mailaddress) throws DataAccessException {
    	Map<String, Object> sexandid = jdbc.queryForMap("SELECT sex, id FROM members WHERE mailaddress = ?", mailaddress);
    	return sexandid;
	}
	
	//●●渡されたマッチング表のレコードから、User型のデータを完成させるメソッド２●●
	public Map<String, Object> TheSelect2(Map<String, Object> map, int b) throws DataAccessException {
	Map<String, Object> who = jdbc.queryForMap("SELECT * FROM members WHERE id = ?", b);
	return who;
	}

	//●●ログイン者の名前をUser型で返すメソッド●●
	@Override
	public User Name(String mailaddress)throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT name FROM members WHERE mailaddress = ?", mailaddress);
		User user = new User();
		user.setName((String)map.get("name"));
		return user;
	}

	//●●メールアドレスを引数に、年齢を返すメソッド●●
	@Override
	public int calcAge(String mailaddress) throws DataAccessException {
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

	//●●マッチングＩＤを引数に、該当するメッセージの記録をMessage型で返すメソッド●●
	@Override
	public List<Message> takeMessage(int matchingid) throws DataAccessException {
		List<Map<String, Object>> getMessage = jdbc.queryForList("SELECT * FROM message WHERE matchingid = ?", matchingid);
		List<Message> Message = new ArrayList<>();
		for(Map<String, Object> map:getMessage) {
			Message onemessage = new Message();
			onemessage.setMatchingid((Integer)map.get("matchingid"));
			onemessage.setWhospost((Integer)map.get("whospost"));
			onemessage.setNumber((Integer)map.get("number"));
			onemessage.setMessagecontent((String)map.get("messagecontent"));
			Message.add(onemessage);
			}
		return Message;
		}
}