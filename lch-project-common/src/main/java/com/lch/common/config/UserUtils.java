package com.lch.common.config;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lch.cache.Cache;
import com.lch.common.constant.Redis;
import com.lch.utils.IdGen;
import com.lch.utils.ObjectUtils;
import com.lch.utils.StringUtils;

@Component
public class UserUtils extends Cache{
	private static UserUtils util;

	private static final String UID = "UID";

	private static final String MToken = "M-Token";
	
	@PostConstruct
	public void init() {
		util = this;
		util.cache = this.cache;
	}

	/* 用户登录，设置缓存 */
	public static String login(Long uid) {
		String token = IdGen.uuid();
		util.cache.set(REDIS_UID_PREFIX + token, uid, Redis.EIGHT_HOUR);
		setCurrentUserId(uid);
		return token;
	}

	/* 退出登录，清空缓存 */
	public static void logout(String token) {
		getsession().invalidate();
		util.cache.del(REDIS_UID_PREFIX + token);
	}

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

	/* 从缓存中获取当前登录用户的ID */
	public static Long getCurrentUserIdForCache() {
		Long uid = null;
		try {
			String token = getRequest().getHeader("token");
			if(StringUtils.isNotBlank(token)) {
				uid = ObjectUtils.toLongForNull(util.cache.get(REDIS_UID_PREFIX + token));
			}
		} catch (Exception e) {
			return null;
		}
		return uid;
	}

	/**
	 * 获取当前用户的位置信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getUserLocation(){
		String mToken = null;
		try {
			mToken = getRequest().getHeader(MToken);
		} catch (Exception e) {
			return null;
		}
		if(StringUtils.isNotBlank(mToken)) {
			return (Map<String, Object>) util.cache.get(LOCATION + mToken);
		}
		return null;
	}

	/* 验证用户是否登录 */
	public static boolean isLogin() {
		return getCurrentUserId() != null;
	}

	/** 验证用户是否有登录权限 **/
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
