package com.lch.service.common.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.cache.redis.RedisUtil;
import com.lch.common.constant.App;
import com.lch.component.wechat.wxprogram.WeChatSession;
import com.lch.component.wechat.wxprogram.WeChatUtils;
import com.lch.service.common.WeChatService;
import com.lch.utils.StringUtils;


@Service
@Transactional(readOnly = true)
public class WeChatServiceImpl implements WeChatService {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public String getAccessToken(String appid) {
		String accessToken = null;
		if (Objects.equals(App.SUPERVISE_APPID, appid)) {
			accessToken = getAccessToken(appid, App.SUPERVISE_APP_SECRET, App.REDIS_SUPERVISE_TOKEN_KEY);
		}
		return accessToken;
	}

	/**
	 * 获取accessToken
	 * 
	 * @param appid
	 * @param appSecret
	 * @param redisTokenKey
	 * @return
	 */
	private String getAccessToken(String appid, String appSecret, String redisTokenKey) {
		// 微信凭证默认有效时间为7200秒，需要在超时后更新
		if (redisUtil.hasKey(redisTokenKey)) {
			return redisUtil.get(redisTokenKey).toString();
		}
		WeChatSession data = WeChatUtils.getAccessToken(appid, appSecret);
		if (data != null) {
			String token = data.getAccess_token();
			if (StringUtils.isNotBlank(token)) {
				redisUtil.set(redisTokenKey, token, 7000);
				return token;
			}
		}
		return null;
	}

}
