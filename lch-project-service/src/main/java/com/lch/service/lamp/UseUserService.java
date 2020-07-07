package com.lch.service.lamp;

import java.util.Date;
import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.UserBase;
import com.lch.entity.common.UserInfo;

/**
 * 处理微信用户或者手动添加用户相关业务逻辑的Service
 * 
 */
public interface UseUserService {

	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	List<UserInfo> findPageUser(String queryStr, Date createDate);
	
	/**
	 * 获取用户详情
	 * @param id
	 * @return
	 */
	UserInfo getUser(Long id);
	
	/**
	 * 保存用户信息：注册
	 * @param user
	 */
	int addUser(UserBase userInfo) throws ServiceException;
	
	int updUser(UserBase userInfo) throws ServiceException;
	
	int delUser(Long id) throws ServiceException;

	List<UserInfo> findTopThreeUser();
	
}
