package com.example.demo;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org. springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.login.domain.model.Message;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

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
                .andExpect(content().string(containsString("気になる方に「いいね」してみましょう。")));
    }

    @Test
    @WithMockUser
    public void マッチング画面表示() throws Exception {
        mockMvc.perform(get("/matching"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("お互いにいいねした方の一覧です。")))
                .andExpect(content().string(containsString("メッセージ画面を開き、お話ししましょう！")));
    }

    @Test
    @WithMockUser
    public void メッセージ画面表示() throws Exception {

		List<Message> Message = new ArrayList<>();

    	when(userService.CheckMatchingid()).thenReturn(1);
    	when(userService.takeMessage(anyInt())).thenReturn(Message);
    	when(userService.Hisname(anyInt(), anyString())).thenReturn("ボブ");

        mockMvc.perform(get("/tomessage"))
       .andExpect(status().isOk())
       .andExpect(content().string(containsString("ボブ")));
    }
}