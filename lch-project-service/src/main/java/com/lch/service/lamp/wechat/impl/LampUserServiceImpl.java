package com.lch.service.lamp.wechat.impl;

import com.lch.service.common.handle.UserHandle;
import com.lch.service.lamp.wechat.LampUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class LampUserServiceImpl extends UserHandle implements LampUserService {


	@Override
	public void setResultMap(Map<String, Object> map) {
		
		//初始化用户信息
	}

}
