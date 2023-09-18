package com.back.splitmeet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

	// httpBasic, formlogin, logout, and... 등등 제거 금지
	// 에러로 경고가 뜨지만 security 이후 버전에서 제거될 예정이라 뜨는것 build 및 사용은 정상적으로 됨.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.and()
			.httpBasic();

		http.formLogin()
			.loginPage("/login").permitAll()
			.and()
			.logout().permitAll();  // 로그아웃은 기본설정으로 (/logout으로 인증해제)

		return http.build();
	}
}
