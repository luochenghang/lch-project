package com.lch.service.lamp;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.SysLog;

public interface SysLogService {

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<SysLog> getAllSysLog(String createDate);


	/**
	 * 新增
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer addSysLog(SysLog sysLog) throws ServiceException;


}