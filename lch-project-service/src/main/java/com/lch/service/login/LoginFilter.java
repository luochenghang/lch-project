package com.lch.service.login;

import com.google.common.collect.Lists;
import com.lch.common.config.UserUtils;
import com.lch.common.enums.ResponseCode;
import com.lch.component.annotation.auth.AuthIgnoreScan;
import com.lch.entity.common.bo.Token;
import com.lch.service.common.handle.TokenService;
import com.lch.service.common.handle.TokenServiceImpl;
import com.lch.utils.HttpUtils;
import com.lch.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录过滤器
 */
public class LoginFilter implements Filter {

    @Autowired
    private TokenService tokenService;

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
        if (outFilter.contains(request.getRequestURI()) || "options".equalsIgnoreCase(request.getMethod()) || TokenServiceImpl.isLogin()) {
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
        // 存在从redis中获取用户id
        String uid = null;
        Token userByToken = tokenService.findUserByToken(token);
        if (userByToken == null) {
            HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "暂未登录");
            return;
        }
        try {
            uid = String.valueOf(userByToken.getUserId());
        } catch (Exception e) {
            HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "登录已过期");
            return;
        }
        if (StringUtils.isBlank(uid)) {
            HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "登录已过期");
            return;
        }
        // 存在用户id，则保存
        TokenServiceImpl.setCurrentUserId(Long.valueOf(uid));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
