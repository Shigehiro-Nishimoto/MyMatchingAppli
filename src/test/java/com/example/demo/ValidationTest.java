package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.example.demo.login.domain.model.MessageBox;
import com.example.demo.login.domain.model.SignupBox;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ValidationTest {

    @Autowired
    Validator validator;

    private SignupBox signupBox = new SignupBox();
    private BindingResult bindingResult = new BindException(signupBox, "SignupBox");
    
    private MessageBox messageBox = new MessageBox();
    private BindingResult bindingResult2 = new BindException(messageBox, "MessageBox");

    @Before
    public void before() {
    	Date date = new Date();
        signupBox.setName("太郎");
        signupBox.setSex(true);
        signupBox.setBirthday(date);
        signupBox.setMailaddress("tarou@yahoo.co.jp");
        signupBox.setPassword("tarou");
        
        messageBox.setNowwritten("おはよう。");
    }

    /**
     * エラーなし
     */
    @Test
    public void エラーがない場合のテスト() {
        validator.validate(signupBox, bindingResult);
        assertThat(bindingResult.getFieldError(), is(nullValue()));
    }
    
    /**
     * nameがnull
     */
    @Test
    public void nameがnull() {
        signupBox.setName(null);
        validator.validate(signupBox, bindingResult);
        assertThat(bindingResult.getFieldError().getField(), is("name"));
        assertThat(bindingResult.getFieldError().getDefaultMessage(), is("この欄は入力必須です！"));
    }
    
    /**
     * mailaddressがメールアドレス形式でない
     */
    @Test
    public void メールアドレスがa() {
        signupBox.setMailaddress("a");
        validator.validate(signupBox, bindingResult);
        assertThat(bindingResult.getFieldError().getField(), is("mailaddress"));
        assertThat(bindingResult.getFieldError().getDefaultMessage(), is("EMail形式で入力してください！"));
    }

    /**
     * passwordが半角空白
     */
    @Test
    public void パスワードが半角空白() {
        signupBox.setPassword("     ");
        validator.validate(signupBox, bindingResult);
        assertThat(bindingResult.getFieldError().getField(), is("password"));
        assertThat(bindingResult.getFieldError().getDefaultMessage(), is("この欄は入力必須です！"));
    }

    /**
     * mailaddressがメールアドレス形式でない
     */
    @Test
    public void パスワードが１文字() {
        signupBox.setPassword("a");
        validator.validate(signupBox, bindingResult);
        assertThat(bindingResult.getFieldError().getField(), is("password"));
        assertThat(bindingResult.getFieldError().getDefaultMessage(), is("４文字以上、１００文字以内で入力してください！"));
    }
    
    /**
     * mailaddressがメールアドレス形式でない
     */
    @Test
    public void パスワードが半角でない() {
        signupBox.setPassword("あああああ");
        validator.validate(signupBox, bindingResult);
        assertThat(bindingResult.getFieldError().getField(), is("password"));
        assertThat(bindingResult.getFieldError().getDefaultMessage(), is("半角英数字で入力してください！"));
    }
    

    /**
     * エラーなし
     */
    @Test
    public void エラーがない場合のテスト２() {
        validator.validate(messageBox, bindingResult2);
        assertThat(bindingResult2.getFieldError(), is(nullValue()));
    }
    
    /**
     * nowwrittenが空白
     */
    @Test
    public void nowwrittenが空白() {
        messageBox.setNowwritten("");
        validator.validate(messageBox, bindingResult2);
        assertThat(bindingResult2.getFieldError().getField(), is("nowwritten"));
        assertThat(bindingResult2.getFieldError().getDefaultMessage(), is("入力してください！"));
    }
    
    /**
     * nowwrittenが１００文字以上
     */
    @Test
    public void nowwrittenが１００文字以上() {
        messageBox.setNowwritten("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        validator.validate(messageBox, bindingResult2);
        assertThat(bindingResult2.getFieldError().getField(), is("nowwritten"));
        assertThat(bindingResult2.getFieldError().getDefaultMessage(), is("１００文字以内で入力してください！"));
    }

}
