package com.lch.service.lamp.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.BaseService;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.UserBase;
import com.lch.entity.common.UserInfo;
import com.lch.repo.common.UserBaseRepo;
import com.lch.repo.common.UserInfoRepo;
import com.lch.service.lamp.UseUserService;
import com.lch.utils.IdGen;

@Service
@Transactional(readOnly = false)
public class UseUserServiceImpl extends BaseService<UserBaseRepo, UserBase> implements UseUserService {


	@Autowired
	protected UserInfoRepo userInfoRepo;
	
	@Override
	public List<UserInfo> findPageUser(String queryStr, Date createDate) {
		return repo.getAllUser(queryStr, createDate);
	}

	@Override
	public UserInfo getUser(Long id) {
		return repo.getUserByInfoId(id);
	}

	@Override
	public int addUser(UserBase user) throws ServiceException {
		//先添加userBase 再添加userInfo
		
		if(repo.getCountByPhone(user.getPhoneNo()) > 0) {
			doThrow("手机号已经存在");
		}
		
		String unionId = IdGen.uuid();
		user.setUnionid(unionId); //这里唯一的unionid
		user.setCreateDate(new Date());
		save(user);
		
		// 新增用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setUserBaseId(user.getId());
		userInfo.setOpenid(unionId);
		userInfo.setUserType(2);//手动添加用户
		return userInfoRepo.insert(userInfo);
	}

	@Override
	public int updUser(UserBase user) throws ServiceException {
			//不需要更新userinfo，但是需要取id
		if(user == null) {
			doThrow("用户信息不存在");
		}
		UserInfo u = repo.getUserByInfoId(user.getId());
		if(u == null) {
			doThrow("用户信息不存在");
		}
		if(repo.getCountByPhone(user.getPhoneNo()) > 0 && !u.getUserBase().getPhoneNo().equals(user.getPhoneNo())) {
			doThrow("手机号已经存在");
		}
		return save(user);
	}

	@Override
	public int delUser(Long id) throws ServiceException {
		//删除2张表的数据 
		userInfoRepo.delete(id);
		return repo.delete(id);
	}

	@Override
	public List<UserInfo> findTopThreeUser() {
		return repo.findTopThreeUser();
	}




	

}
