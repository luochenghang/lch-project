package com.lch.service.login;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问控制Filter,可以解决跨域问题
 * 
 * @ClassName: AccessFilter
 */
public class AccessFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// 设置IP地址的机器可以直接访问到这个项目的后端
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String originHeader = request.getHeader("Origin");
		if ((originHeader != null)) {
			// 设置响应头
			response.setHeader("Access-Control-Allow-Origin", originHeader);
			response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent, token");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
