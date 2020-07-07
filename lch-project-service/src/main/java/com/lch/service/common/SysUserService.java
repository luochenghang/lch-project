package com.lch.service.common;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.authority.SysUser;

/**
 * 处理系统用户相关业务逻辑的Service
 * 
 */
public interface SysUserService {

	SysUser findByUserName(String userName);
	
	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	List<SysUser> findPageUser();
	
	/**
	 * 获取用户详情
	 * @param id
	 * @return
	 */
	SysUser getSysUser(Long id);
	
	/**
	 * 保存用户信息：注册
	 * @param user
	 */
	int register(SysUser user) throws ServiceException;
	
	/**
	 * 删除用户
	 * @param id
	 */
	int deleteSysUser(List<String> ids) throws ServiceException;
	
	
	int updateSysUserStatus(Long id, Long status) throws ServiceException;
	
	int updateSysUser(SysUser user) throws ServiceException;
	
	int updateSysUser(String oldPwd, String newPwd) throws ServiceException;
	
	int updLoginNum(Long id) throws ServiceException;
}
