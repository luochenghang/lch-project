package com.lch.entity.common.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

//用户登录信息封装对象

@Data
public class UlBo implements Serializable{

	private static final long serialVersionUID = 1L;

	//(value = "小程序登录code", required = true)
	private String code;

	//(value = "小程序登录加密字段", required = true)
	private String encryptedData;

	//(value = "小程序登录初始向量", required = true)
	private String iv;

	//(value = "用户昵称", required = true)
	private String nickName;

	//(value = "用户头像路径", required = true)
	private String portrait;

	//(value = "用户性别", required = true)
	private Integer sex;

	//(value = "用户标识", required = true)
	private String sign;

	private String sessionKey;

	@JsonIgnore
	private Integer userType;//微信用户类型(0监督小程序)

	@JsonIgnore
	private String openid;

	@JsonIgnore
	private String unionid;

}
