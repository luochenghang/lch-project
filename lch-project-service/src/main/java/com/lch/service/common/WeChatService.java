package com.lch.service.common;

/**
 * 微信小程序操作相关Service
 */
public interface WeChatService {

	/**
	 * 获取微信AccessToken
	 * 
	 * @param appid
	 * @return
	 */
	String getAccessToken(String appid);

}
