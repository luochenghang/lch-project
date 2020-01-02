package com.lch.component.wechat.wxprogram;

import java.util.Arrays;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.lch.utils.JsonUtils;

/**
 * 微信小程序工具类
 *
 */
public class WeChatUtils {

	/** 小程序授权类型 **/
	private static final String GRANT_TYPE_LOGIN = "authorization_code";

	/** 小程序授权类型 **/
	private static final String GRANT_TYPE_TOKEN = "client_credential";


	/**
	 * 微信登录
	 * @param code
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	public static WeChatSession login(String code, String appid, String appSecret) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appSecret + "&js_code="
		        + code + "&grant_type=" + GRANT_TYPE_LOGIN;
		return getWeChatSession(url);
	}

	/**
	 * 获取小程序全局唯一后台接口调用凭据（access_token）
	 *
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	public static WeChatSession getAccessToken(String appid, String appSecret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + GRANT_TYPE_TOKEN + "&appid=" + appid + "&secret=" + appSecret;
		return getWeChatSession(url);
	}

	/**
	 * 发送请求，解析数据
	 *
	 * @param url
	 * @return
	 */
	private static WeChatSession getWeChatSession(String url) {
		RestTemplate restTemplate = new RestTemplate();
		// 进行网络请求,访问url接口
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
			// 解析从微信服务器获得的数据;
			return (WeChatSession) JsonUtils.fromJsonString(responseEntity.getBody(), WeChatSession.class);
		}
		return null;
	}


	/**
	 * 排序方法
	 *
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);

		StringBuilder sbuilder = new StringBuilder();
		for (String str : strArray) {
			sbuilder.append(str);
		}

		return sbuilder.toString();
	}
}
