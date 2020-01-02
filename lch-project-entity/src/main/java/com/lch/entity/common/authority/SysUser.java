package com.lch.entity.common.authority;

import java.util.Date;
import java.util.List;

import com.lch.common.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<SysUser> {

	private static final long serialVersionUID = -1407323269687112364L;

	private Long roleId;//角色id
	
	private Long userType;//用户类型
	
	//(example = "用户名", value = "用户名", required = true)
	private String userName;

	//(example = "密码", value = "密码", required = true)
	private String pwd;

	//(example = "真实姓名", value = "真实姓名", required = true)
	private String realName;

	//(example = "是否是超级管理员(0:否 1:是)", value = "是否是超级管理员(0:否 1:是)", required = true)
	private Short isAdmin;

	//(example = "状态(0:未激活 1:正常 2:禁用 3:删除)", value = "状态(0:未激活 1:正常 2:禁用 3:删除)", required = true)
	private Short status;

	//(example = "最后登录时间", value = "最后登录时间", required = false)
	private Date lastLoginTime;

	private List<SysRole> role;
}