package com.lch.service.wechat;

import com.alibaba.fastjson.JSONObject;
import com.lch.component.wechat.wxpublic.entity.ImageMessage;

public interface WxService {

	
	/**
	 * 发送文本信息
	 * 
	 * @param openid
	 * @param text
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public String sendKfMessage(String openid, String text, String access_token) throws Exception;

	/**
	 * 发送图片
	 * 
	 * @param imageMessage
	 * @return 
	 */
	public boolean sendImage(ImageMessage imageMessage, String access_token);

	/**
	 * 发送客服消息
	 */
	public JSONObject customSend(JSONObject json, String accessToken);


}
