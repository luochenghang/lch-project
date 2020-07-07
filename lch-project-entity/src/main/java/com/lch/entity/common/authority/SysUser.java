package com.lch.entity.common.authority;

import java.util.Date;

import com.lch.common.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<SysUser> {

	private static final long serialVersionUID = -1407323269687112364L;
	
	//(example = "用户名", value = "用户名", required = true)
	private String userName;

	//(example = "密码", value = "密码", required = true)
	private String pwd;

	//(example = "真实姓名", value = "真实姓名", required = true)
	private String realName;

	//状态(0:禁用 1:正常)
	private Short status;

	//(example = "最后登录时间", value = "最后登录时间", required = false)
	private Date lastLoginTime;

	private String email; //邮件
	
	private Long loginNum; //登陆次数
}