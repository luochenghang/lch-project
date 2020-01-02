package com.lch.entity.common.authority.vo;

import java.util.List;

import com.lch.entity.common.authority.SysRole;
import com.lch.entity.common.authority.SysUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserVo extends SysUser {

	private static final long serialVersionUID = -2583857325741296125L;

	private List<SysRole> role;


}