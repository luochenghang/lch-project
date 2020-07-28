package com.lch.service.common.handle;

import com.lch.common.constant.Redis;
import com.lch.entity.common.bo.Token;
import com.lch.repo.common.TokenRepo;
import com.lch.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String UID = "UID";

    private static final String MToken = "M-Token";

    @Autowired
    private TokenRepo tokenRepo;


    @Override
    public Token findUserByToken(String token) {
        return tokenRepo.findUserByToken(token);
    }

    @Override
    public int logout(String token) {
        getsession().invalidate();
        return tokenRepo.delToken(token);
    }

    @Override
    public String login(Long uid) {
        String token = IdGen.uuid();
        // 插入数据库
        tokenRepo.insertToken(token,uid);
        setCurrentUserId(uid);
        return token;
    }


    /******************************************************************/

    /* 设置当前登录用户的ID */
    public static void setCurrentUserId(Long uid) {
        getsession().setAttribute(UID, uid);
    }

    /* 获取当前登录用户的ID */
    public static Long getCurrentUserId() {
        Long uid = null;
        try {
            uid = (Long) getsession().getAttribute(UID);
        } catch (Exception e) {
        }
        return uid;
    }
    /* 验证用户是否登录 */
    public static boolean isLogin() {
        return getCurrentUserId() != null;
    }
    public static HttpSession getsession() {
        return getRequest().getSession();
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
