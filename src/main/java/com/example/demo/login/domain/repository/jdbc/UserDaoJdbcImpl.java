package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	   	List<Map<String, Object>> maleidall = jdbc.queryForList("SELECT id FROM members WHERE sex = true");
	   	List<Map<String, Object>> femaleidall = jdbc.queryForList("SELECT id FROM members WHERE sex = false");
		int newmatchingid = 1 + (Integer)biggestmatchingid.get("MAX(matchingid)");
		boolean sexofthisperson = user.getSex();

		if(sexofthisperson == true){
			int w = newmatchingid;

			for(Map<String, Object> y: femaleidall) {
				int yy = (Integer)y.get("id");
			    int rowNumber2 = jdbc.update("INSERT INTO matchings(matchingid, maleid, femaleid, state) VALUES(?, ?, ?, ?)", w, newid, yy, 0);
			    w = w + 1;
				}
		}else {
			int w = newmatchingid;
			for(Map<String, Object> y: maleidall) {
				int yy = (Integer)y.get("id");
			    int rowNumber2 = jdbc.update("INSERT INTO matchings(matchingid, maleid, femaleid, state) VALUES(?, ?, ?, ?)", w, yy, newid, 0);
			    w = w + 1;
				}
		}
	    return rowNumber;
	}

	//●●マッチング表のデータを全て取得するメソッド●●
	@Override
	public List<Map<String, Object>> getallfromMatching() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM matchings");
		return getList;
		}

	//●●渡されたマッチング表のレコードから、User型のデータを完成させるメソッド１●●
	public Map<String, Object> TheSelect1(String mailaddress) throws DataAccessException {
    	Map<String, Object> sexandid = jdbc.queryForMap("SELECT sex, id FROM members WHERE mailaddress = ?", mailaddress);
    	return sexandid;
	}

	//●●渡されたマッチング表のレコードから、User型のデータを完成させるメソッド２●●
	public Map<String, Object> TheSelect2(int b) throws DataAccessException {
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
			
			String str = (String)map.get("messagecontent");
			str = str.replaceAll("(\r\n|\n)", "<br>");
			
			onemessage.setMessagecontent(str);
			
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String mailaddressnow = auth.getName();
			int myid = whosloggingin(mailaddressnow);
			boolean meornot = true;
			if(myid == (Integer)map.get("whospost")) {
				meornot =  true;
			}else {
				meornot =  false;
			}
			onemessage.setMeornot(meornot);
			int whospost = (Integer)map.get("whospost");
			Map<String, Object> sex = jdbc.queryForMap("SELECT sex FROM members WHERE id = ?", whospost);
			onemessage.setSex((Boolean)sex.get("sex"));
			Message.add(onemessage);
			}

		int a1 = jdbc.queryForObject("SELECT COUNT(*) FROM matchingaite", Integer.class);
		if(a1 == 0) {
		int matchingaitekakikomi = jdbc.update("INSERT INTO matchingaite(matchingid) VALUES(?)", matchingid);
		}else {
		}
		System.out.println(Message);
		return Message;
		}

	public int MessageWritten(Map<String, Object> written) {
	    int rowNumber2 = jdbc.update("INSERT INTO message(matchingid, whospost, number, messagecontent) VALUES(?, ?, ?, ?)", (Integer)written.get("matchingid"), (Integer)written.get("whospost"), (Integer)written.get("number"), (String)written.get("messagecontent"));
		return rowNumber2;
	}

	public int CheckMatchingid() {
	Map<String, Object> map = jdbc.queryForMap("SELECT matchingid FROM matchingaite");
	return (Integer)map.get("matchingid");
	}

	public int whosloggingin(String mailaddress) {
	Map<String, Object> map = jdbc.queryForMap("SELECT id FROM members WHERE mailaddress = ?", mailaddress);
	return (Integer)map.get("id");
	}

	public int seebiggestnumber(int matchingid) {
	Map<String, Object> map = jdbc.queryForMap("SELECT Max(number) FROM message WHERE matchingid = ?", matchingid);
	System.out.println(map);
	if((Integer)map.get("Max(number)") == null) {
		return 0;
	}else {
		return  (Integer)map.get("Max(number)");
		}
	}

	public int LeaveMessageGamen() {
		int a = jdbc.queryForObject("SELECT COUNT(*) FROM matchingaite", Integer.class);
		int b = 0;
		int c = 0;
		if(a == 0) {
		}else {
		Map<String, Object> map2 = jdbc.queryForMap("SELECT matchingid FROM matchingaite");
		b = (Integer)map2.get("matchingid");
		c = jdbc.update("DELETE FROM matchingaite WHERE matchingid = ?", b);
		}
		return c;
	}

	public Map<String, Object> Roguinshanoidtoseibetsu(String mailaddress){
    	Map<String, Object> sexandid = jdbc.queryForMap("SELECT sex, id FROM members WHERE mailaddress = ?", mailaddress);
    	return sexandid;
	}

	public  int Iineshita (int matchingid, boolean sex) {

		Map<String, Object> map = jdbc.queryForMap("SELECT state FROM matchings WHERE matchingid = ?", matchingid);
		int imanostate = (Integer)map.get("state");

		int kakikaeta = 0;

		if(imanostate == 0) {
			if(sex == true) {
				kakikaeta = jdbc.update("UPDATE matchings SET state = 1 WHERE matchingid = ?", matchingid);
			}else {
				kakikaeta = jdbc.update("UPDATE matchings SET state = 2 WHERE matchingid = ?", matchingid);
			}
		}else {
			kakikaeta = jdbc.update("UPDATE matchings SET state = 3 WHERE matchingid = ?", matchingid);
		}
		return kakikaeta;
	}

	public String Hisname(int matchingid, String mailaddressnow){
    	Map<String, Object> sex1 = jdbc.queryForMap("SELECT sex FROM members WHERE mailaddress = ?", mailaddressnow);
    	boolean sex = (boolean)sex1.get("sex");
    	int id = 0;
    	if(sex == true) {
        	Map<String, Object> theid = jdbc.queryForMap("SELECT femaleid FROM matchings WHERE matchingid = ?", matchingid);
        	id = (Integer)theid.get("femaleid");
    	}else {
        	Map<String, Object> theid = jdbc.queryForMap("SELECT maleid FROM matchings WHERE matchingid = ?", matchingid);
        	id = (Integer)theid.get("maleid");
    	}
    	Map<String, Object> name = jdbc.queryForMap("SELECT name FROM members WHERE id = ?", id);
    	String hisname = (String)name.get("name");
    	return hisname;
	}

	public int Sakujo(int matchingid, int number) {
	return jdbc.update("DELETE message WHERE matchingid = ? AND number = ?", matchingid, number);
	}
	
	public int Iineshitakaijo(int matchingid) {

		Map<String, Object> map = jdbc.queryForMap("SELECT state FROM matchings WHERE matchingid = ?", matchingid);
		int kakikaeta = 0;
		kakikaeta = jdbc.update("UPDATE matchings SET state = 0 WHERE matchingid = ?", matchingid);
		return kakikaeta;
	}
}