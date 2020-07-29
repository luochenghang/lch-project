package com.lch.common.config;

import com.lch.utils.ObjectUtils;
import com.lch.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static com.lch.common.config.JwtUtils.JWT_KEY_USER_ID;

@Component
public class UserSessionUtils {

    private static final String UID = "UID";

    /* 设置当前登录用户的ID */
    public static void setCurrentUserId(Long uid) {
        getsession().setAttribute(UID + "_" + uid, uid);
    }

    /* 获取当前登录用户的ID */
    public static Long getCurrentUserId() {

        Long uid = null;
        try {
            String token = getRequest().getHeader("token");
            if (StringUtils.isNotBlank(token)) {
                uid = ObjectUtils.toLongForNull(JwtUtils.validateJWT(token).getClaims()
                        .get(JWT_KEY_USER_ID).toString());
            }
        } catch (Exception e) {
            return null;
        }
        return uid;
    }

    /* 从缓存中获取当前登录用户的ID */
    public static Long getCurrentUserIdForCache() {
        Long uid = null;
        try {
            String token = getRequest().getHeader("token");
            if (StringUtils.isNotBlank(token)) {
                uid = ObjectUtils.toLongForNull(JwtUtils.validateJWT(token).getClaims()
                        .get(JWT_KEY_USER_ID).toString());
            }
        } catch (Exception e) {
            return null;
        }
        return uid;
    }

    /* 验证用户是否登录 */
    public static boolean isLogin() {
        return getCurrentUserId() != null;
    }

    /**
     * 验证用户是否有登录权限
     **/
    public static boolean isLoginAuth(String systems, String systemType) {
        if (StringUtils.isBlank(systems) || !Arrays.asList(systems.split(",")).contains(systemType)) {
            return false;
        }
        return true;
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
