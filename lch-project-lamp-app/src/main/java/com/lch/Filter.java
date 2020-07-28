package com.lch;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.lch.service.login.AccessFilter;
import com.lch.service.login.LoginFilter;

@Configuration
public class Filter {

	private static final String URL_MATCH = "/lampApp/*";

	//** 访问控制Filter **//
	@Bean
	@Order(10)
	public FilterRegistrationBean<AccessFilter> someFilterRegistration() {
		FilterRegistrationBean<AccessFilter> registration = new FilterRegistrationBean<AccessFilter>();
		registration.setFilter(accessFilter());
		registration.addUrlPatterns(URL_MATCH);
		registration.setName("accessFilter");
		return registration;
	}


	//** 登录控制Filter **//
	@Bean
	@Order(30)
	public FilterRegistrationBean<LoginFilter> loginFilterRegistration() {
		FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<LoginFilter>();
		registration.setFilter(loginFilter());
		registration.addUrlPatterns(URL_MATCH);
		registration.setName("loginFilter");
		return registration;
	}

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

	@Bean
	public LoginFilter loginFilter() {
		return new LoginFilter();
	}
}
