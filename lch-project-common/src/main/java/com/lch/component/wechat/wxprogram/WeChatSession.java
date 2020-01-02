package com.lch.component.wechat.wxprogram;

import java.io.Serializable;

import lombok.Data;

/**
 * 微信登录返回封装对象
 * 
 * @author Jun
 * @date 2019年1月4日 下午1:51:47
 */
@Data
public class WeChatSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private String openid;// 用户唯一标识

	private String session_key;// 会话密钥

	private String unionid;// 用户在开放平台的唯一标识符

	private String access_token;// 获取到的凭证

	private Long expires_in;// 凭证有效时间，单位：秒。目前是7200秒之内的值。

	private String errcode;// 错误码 -1系统繁忙，此时请开发者稍候再试 0请求成功 40029code无效
	                       // 45011频率限制，每个用户每分钟100次

	private String errmsg;// 错误信息

}
