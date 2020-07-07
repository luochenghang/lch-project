package com.lch.common.constant;

import com.lch.utils.ConfigUtils;

/**
 * 微信APP相关常量
 * 
 * @date @date 2019年12月10日 下午3:23:56
 */
public final class App {

	private static ConfigUtils loader;

	static {
		loader = ConfigUtils.getInstance();
	}

	/** 监督小程序appId appSecret **/
	public static final String SUPERVISE_APPID = loader.getConfigValue("wx.supervise.app.id");

	public static final String SUPERVISE_APP_SECRET = loader.getConfigValue("wx.supervise.app.secret");

	/** 监督小程序缓存的accessToken的key **/
	public static final String REDIS_SUPERVISE_TOKEN_KEY = "supervise_user_token";

	/** 上传的基础路径 */
	public static final String BASE_PATH = loader.getConfigValue("base_path");
	
	/** 定义常量 **/
	// 监督小程序类型
	public static final Integer SUPERVISE_TYPE = 0;
	
	public static final Long VICTORY_MIN_POINT = 15L;
	public static final Long VICTORY_MAX_POINT = 25L;
	
	public static final Long DEFEAT_MIN_POINT = 10L;
	public static final Long DEFEAT_MAX_POINT = 15L;
	
	
	//web常量类型
	
	

}
