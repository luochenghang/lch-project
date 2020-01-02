package com.lch.entity.common;

import java.util.Date;

import com.lch.common.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户基础信息
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserBase extends BaseEntity<UserBase> {

	private static final long serialVersionUID = 1L;
 
	//(example = "用户名", value = "用户名", required = true)
	private String userName;

	//(example = "密码", value = "密码", required = true)
	private String pwd;

	//(example = "手机号", value = "手机号", required = true)
	private String phoneNo;

	//(example = "微信UnionID", value = "微信UnionID", required = true)
	private String unionid;

	//(example = "微信OpenID", value = "微信OpenID", required = true)
	private String openid;

	//(example = "昵称", value = "昵称", required = true)
	private String nickName;

	//(example = "头像", value = "头像", required = true)
	private String portrait;

	//(example = "性别（0男 1女）", value = "性别（0男 1女）", required = true)
	private Integer sex;

	//(example = "真实姓名", value = "真实姓名", required = true)
	private String realName;

	//(example = "身份证号", value = "身份证号", required = true)
	private String idCard;

	//(example = "所在城市编号", value = "所在城市编号", required = true)
	private Long cityId;

	//(example = "所在城市", value = "所在城市", required = true)
	private String city;

	//(example = "生日", value = "生日", required = true)
	private Date birthday;

	//(example = "用户类型(0车主 1乘客)", value = "用户类型(0车主 1乘客)", required = true)
	private Integer userType;

	//(example = "状态(0:正常 1:禁用 2:删除)", value = "状态(0:正常 1:禁用 2:删除)", required = true)
	private Integer status;

	//(example = "用户关注的渠道来源", value = "用户关注的渠道来源", required = false)
	private String subscribeScene;
}
