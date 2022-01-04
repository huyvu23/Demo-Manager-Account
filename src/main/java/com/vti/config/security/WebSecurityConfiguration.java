package com.vti.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.vti.service.IAccountService;

@Component // đánh dấu đây là 1 DI
@EnableWebSecurity // phải có anotation này để bật tường bảo mật lên

// -	Class này dùng để cấu hình mã hóa Password và các giao thức bảo mật khi truyền dữ liệu
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private IAccountService accountService;

//	Hàm này định nghĩa ra accountDetail được lấy từ đâu
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
	}

	// .antMatchers(HttpMethod.GET).permitAll() // không xác thực các phương thức
	// get
}
