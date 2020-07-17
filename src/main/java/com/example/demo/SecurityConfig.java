package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org. springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org. springframework. security. config. annotation. web. builders. WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private DataSource dataSource;
	private static final String MEMBER_SQL = "SELECT mailaddress, password FROM members WHERE mailaddress = ?";

	@Override
	public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/webjars/∗∗","/css/∗∗");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//直リンクなどの設定
		http
	    .authorizeRequests()
	    .antMatchers("/webjars/∗∗").permitAll()
        .antMatchers("/login"). permitAll()
        .antMatchers("/signup"). permitAll()
        .anyRequest().authenticated();

//ログイン処理
		http
		.formLogin()
		.loginProcessingUrl("/login")
		.loginPage("/login")
		.failureUrl("/login")
		.usernameParameter("mailaddress")
		.passwordParameter("password")
		.defaultSuccessUrl("/home", true);
	}

		@Override
		protected void configure(AuthenticationManagerBuilder auth)
		throws Exception {
			auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(MEMBER_SQL)
			.passwordEncoder(passwordEncoder());
			
	}
}