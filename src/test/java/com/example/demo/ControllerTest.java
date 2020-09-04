package com.example.demo;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org. springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.MessageBox;
import com.example.demo.login.domain.model.SignupBox;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void ログイン画面表示() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(content().string(containsString("マッチングアプリ")))
                .andExpect(content().string(containsString("メールアドレス")))
        		.andExpect(content().string(containsString("パスワード")))
        		.andExpect(content().string(containsString("ログインする")))
        		.andExpect(content().string(containsString("新規登録はこちら")));
    }

    @Test
    public void 登録画面表示() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ユーザー登録画面")))
                .andExpect(content().string(containsString("名前")))
        		.andExpect(content().string(containsString("性別")))
                .andExpect(content().string(containsString("男")))
                .andExpect(content().string(containsString("女")))
        		.andExpect(content().string(containsString("誕生日")))
		        .andExpect(content().string(containsString("メールアドレス")))
				.andExpect(content().string(containsString("パスワード")))
        		.andExpect(content().string(containsString("ユーザー登録")));
    }

    @Test
    @WithMockUser
    public void ホーム画面表示() throws Exception {

    	User a = new User();
    	a.setName("グレイ");
    	List<User> userList = new ArrayList<User>();
    	when(userService.Name(anyString())).thenReturn(a);
    	when(userService.calcAge(anyString())).thenReturn(17);
    	when(userService.selectBeforematching(anyString())).thenReturn(userList);

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("グレイ")))
                .andExpect(content().string(containsString("さん（")))
                .andExpect(content().string(containsString("17")))
                .andExpect(content().string(containsString("歳）こんにちは。")))
                .andExpect(content().string(containsString("気になる方に「いいね」してみましょう。")))
        		.andExpect(model().attribute("thename", "グレイ"));
    }

    @Test
    @WithMockUser
    public void マッチング画面表示() throws Exception {

    	List<User> userList = new ArrayList<User>();

    	when(userService.selectAftermatching(anyString())).thenReturn(userList);

        mockMvc.perform(get("/matching"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("お互いにいいねした方の一覧です。")))
                .andExpect(content().string(containsString("メッセージ画面を開き、お話ししましょう！")))
        		.andExpect(model().attribute("userList", userList));
    }

    @Test
    @WithMockUser
    public void メッセージ画面表示１() throws Exception {

		List<Message> Message = new ArrayList<>();

    	when(userService.CheckMatchingid()).thenReturn(1);
    	when(userService.takeMessage(anyInt())).thenReturn(Message);
    	when(userService.Hisname(anyInt(), anyString())).thenReturn("ボブ");

        mockMvc.perform(get("/tomessage"))
       .andExpect(status().isOk())
       .andExpect(content().string(containsString("ボブ")));
    }

    @Test
    @WithMockUser
    public void メッセージ画面表示２() throws Exception {

		List<Message> Message = new ArrayList<>();

    	Message message1 = new Message();
    	Message message2 = new Message();

        message1.setMatchingid(1);
        message1.setWhospost(1);
        message1.setNumber(1);
        message1.setMessagecontent("はじめまして！");
        message1.setSex(true);
        message2.setMatchingid(1);
        message2.setWhospost(4);
        message2.setNumber(2);
        message2.setMessagecontent("こちらこそ！");
        message2.setSex(false);

        Message.add(message1);
        Message.add(message2);

    	when(userService.CheckMatchingid()).thenReturn(1);
    	when(userService.takeMessage(anyInt())).thenReturn(Message);
    	when(userService.Hisname(anyInt(), anyString())).thenReturn("ボブ");

        mockMvc.perform(get("/newmessage"))
       .andExpect(status().isOk())
       .andExpect(content().string(containsString("ボブ")))
       .andExpect(content().string(containsString("はじめまして！")))
       .andExpect(content().string(containsString("こちらこそ！")))
       .andExpect(model().attribute("hisname", "ボブ"));
    }

    @Test
    @WithMockUser
    public void メッセージ記入() throws Exception {

      MessageBox mb = new MessageBox();
      mb.setNowwritten("テスト。");

	List<Message> Message = new ArrayList<>();

	when(userService.takeMessage(anyInt())).thenReturn(Message);

      mockMvc.perform(post("/newmessage").flashAttr("messageBox", mb).with(SecurityMockMvcRequestPostProcessors.csrf()));

      verify(userService, times(1)).MessageWritten("テスト。");
    }

    @Test
    @WithMockUser
    public void 登録画面() throws Exception {

    	Date date = new Date();

        SignupBox sb = new SignupBox();
        sb.setName("グレイ");
        sb.setSex(true);
        sb.setBirthday(date);
        sb.setMailaddress("gray@yahoo.co.jp");
        sb.setPassword("gggg");

        User user = new User();

        user.setId(1);
        user.setName("グレイ");
        user.setSex(true);
        user.setBirthday(date);
        user.setMailaddress("gray@yahoo.co.jp");
        user.setPassword("gggg");
        user.setAge(1);
        user.setMatchingid(1);
        user.setState(1);
        user.setGaitousuruka(true);
        user.setRole("ROLE_GENERAL");

      mockMvc.perform(post("/signup").flashAttr("signupBox", sb).with(SecurityMockMvcRequestPostProcessors.csrf()));

      verify(userService, times(1)).insert(user);
    }

    @Test
    @DatabaseSetup(value = "/controller/top/setUp/")
    public void ログインテスト() throws Exception {
        this.mockMvc.perform(post("/login")
            .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockUser(username="micheal@yahoo.co.jp")
    public void いいねした() throws Exception {
      mockMvc.perform(get("/iineshita/2").with(SecurityMockMvcRequestPostProcessors.csrf()));
      verify(userService, times(1)).Iineshita(2, "micheal@yahoo.co.jp");
    }
    
    //異常系追加
    @Test
    @WithMockUser(username="micheal@yahoo.co.jp")
    public void いいねした２() throws Exception {
      mockMvc.perform(get("/iineshita/10").with(SecurityMockMvcRequestPostProcessors.csrf()));
      verify(userService, times(1)).Iineshita(10, "micheal@yahoo.co.jp");
    }
    
    //異常系追加
    @Test
    @WithMockUser(username="micheal@yahoo.co.jp")
    public void いいねした３() throws Exception {
      mockMvc.perform(get("/iineshita/-1").with(SecurityMockMvcRequestPostProcessors.csrf()));
      verify(userService, times(1)).Iineshita(-1, "micheal@yahoo.co.jp");
    }
    
    //異常系追加
    @Test
    @WithMockUser(username="micheal@yahoo.co.jp")
    public void 存在しないＵＲＬ() throws Exception {
        mockMvc.perform(get("/a"))
                .andExpect(content().string(containsString("404エラー")));
    }
}
