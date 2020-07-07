package com.lch.service.lamp.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.SysLog;
import com.lch.repo.lamp.SysLogRepo;
import com.lch.service.lamp.SysLogService;

@Service
@Transactional(readOnly = false)
public class SysLogServiceImpl extends DataService<SysLogRepo, SysLog> implements SysLogService {

	@Override
	public List<SysLog> getAllSysLog(String createDate) {
		return repo.getAllSysLog(createDate);
	}

	@Override
	public Integer addSysLog(SysLog sysLog) throws ServiceException {
		return repo.insert(sysLog);
	}



	

}
