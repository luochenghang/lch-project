package com.lch.service.subpervise.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.service.common.handle.UserHandle;
import com.lch.service.subpervise.RelatedUserService;
import com.lch.service.subpervise.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends UserHandle implements UserService {

	@Autowired
	private RelatedUserService relatedUserService;
	
	@Override
	public void setResultMap(Map<String, Object> map) {
		
		//初始化用户信息
		relatedUserService.initUserSimpleInfo();
	}

}
