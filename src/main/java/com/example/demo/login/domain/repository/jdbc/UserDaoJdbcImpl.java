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


	//●●状態が２以下のお相手を表示するメソッド●●
	@Override
	public List<Map<String, Object>> selectBeforematching(String mailaddress) throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
		return getList;
		}

	//●●状態が３のお相手を表示するメソッド●●
	@Override
	public List<Map<String, Object>> selectAftermatching(String mailaddress) throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
		return getList;
		}

	//●●渡されたマッチング表のレコードから、User型のデータを完成させるメソッド●●
	public User TheSelect(Map<String, Object> map, String mailaddress) throws DataAccessException {
    	Map<String, Object> sex = jdbc.queryForMap("SELECT sex FROM members WHERE mailaddress = ?", mailaddress);
    	Map<String, Object> id = jdbc.queryForMap("SELECT id FROM members WHERE mailaddress = ?", mailaddress);
    	boolean c = (Boolean)sex.get("sex");
    	int d = (Integer)id.get("id");
    	int b = 0;
    	if(c == true) {
	    	b = (Integer)map.get("femaleid");
    	}else{
	    	b = (Integer)map.get("maleid");
    	}
		Map<String, Object> who = jdbc.queryForMap("SELECT * FROM members WHERE id = ?", b);

		User user = new User();
		user.setMatchingid((Integer)map.get("matchingid"));
		user.setState((Integer)map.get("state"));
		user.setName((String)who.get("name"));
		user.setBirthday((Date)who.get("birthday"));
		int age = calcAgeAruAru((String)who.get("mailaddress"));
		user.setAge(age);
    	int e = (Integer)map.get("maleid");
    	int f = (Integer)map.get("femaleid");
    	boolean g = false;
    	if(c == true) {
	    	if(d == e) {
	    	g = true;
	    	}
    	}else{
	    	if(d == f) {
	    	g = true;
	    	}
    	}
		user.setGaitousuruka(g);
		return user;
	}

	//●●新規会員登録するメソッド●●
	@Override
	public int insertOne(User user)throws DataAccessException {

		Map<String, Object> a = jdbc.queryForMap("SELECT MAX(id) + FROM members");
		int newid = (Integer)a.get("id") + 1;
		String password = passwordEncoder.encode(user.getPassword());
		int rowNumber = jdbc.update("INSERT INTO members(id, name, sex, birthday, mailaddress, password, role) VALUES(?, ?, ?, ?, ?, ?, ?)",
		newid, user.getName(), user.getSex(), user.getBirthday(), user.getMailaddress(), password, "ROLE_GENERAL");
		return rowNumber;
	}

	//●●ログイン者の名前をUser型で返すメソッド●●
	@Override
	public User selectOne(String mailaddress)throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT name FROM members WHERE mailaddress = ?", mailaddress);
		User user = new User();
		user.setName((String)map.get("name"));
		return user;
	}

	//●●メールアドレスを引数に、年齢を返すメソッド●●
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