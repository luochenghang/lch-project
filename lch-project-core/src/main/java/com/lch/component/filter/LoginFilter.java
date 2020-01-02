package com.lch.component.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.lch.cache.redis.RedisUtil;
import com.lch.common.config.UserUtils;
import com.lch.common.constant.Redis;
import com.lch.common.enums.ResponseCode;
import com.lch.component.annotation.auth.AuthIgnoreScan;
import com.lch.utils.HttpUtils;
import com.lch.utils.ObjectUtils;
import com.lch.utils.StringUtils;
import com.google.common.collect.Lists;

/**
 * 登录过滤器
 * 
 * @author Jun
 * @date 2018年11月9日 下午3:57:53
 */
public class LoginFilter implements Filter {

	@Autowired
	private RedisUtil redisUtil;

	//不需要拦截的url
	private List<String> outFilter = Lists.newArrayList();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		outFilter = AuthIgnoreScan.getInstance().getNoLoginUris();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 若用户已登录则直接放行
		if (outFilter.contains(request.getRequestURI()) || "options".equalsIgnoreCase(request.getMethod()) || UserUtils.isLogin()) {
			chain.doFilter(request, response);
			return;
		}
		// 获取用户会话标识
		String token = request.getHeader("token");
		// 判断是否登录
		if (StringUtils.isBlank(token)) {
			HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "暂未登录");
			return;
		}
		// 存在JESSIONID，从redis中获取用户id
		String uid = null;
		try {
			uid = ObjectUtils.toString(redisUtil.get(Redis.REDIS_UID_PREFIX + token));
		}
		catch (Exception e) {
			HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "登录已过期");
			return;
		}
		if (StringUtils.isBlank(uid)) {
			HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "登录已过期");
			return;
		}
		// 存在用户id，则保存
		UserUtils.setCurrentUserId(Long.valueOf(uid));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
