package com.lch.repo.lamp;

import org.apache.ibatis.annotations.Mapper;

import com.lch.common.base.BaseRepo;
import com.lch.entity.common.authority.SysUser;

@Mapper
public interface SysUserRoleRepo extends BaseRepo<SysUser> {

	
}