package com.lch.service.login;

import com.google.common.collect.Lists;
import com.lch.common.config.CheckResult;
import com.lch.common.config.JwtUtils;
import com.lch.common.enums.ResponseCode;
import com.lch.component.annotation.auth.AuthIgnoreScan;
import com.lch.utils.HttpUtils;
import com.lch.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lch.common.config.JwtUtils.JWT_KEY_USER_ID;

/**
 * 登录过滤器
 */
public class LoginFilter implements Filter {


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
        if (outFilter.contains(request.getRequestURI()) || "options".equalsIgnoreCase(request.getMethod())) {
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
        // 存在从redis中获取用户id  改为jwt模式
        String uid = null;
        CheckResult checkResult = JwtUtils.validateJWT(token);
        if (checkResult == null) {
            HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "暂未登录");
            return;
        }
        try {
            uid = checkResult.getClaims().get(JWT_KEY_USER_ID).toString();
        } catch (Exception e) {
            HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "登录已过期");
            return;
        }
        if (StringUtils.isBlank(uid)) {
            HttpUtils.onAccessDenied(response, ResponseCode.NOLOGIN.getCode(), "登录已过期");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
