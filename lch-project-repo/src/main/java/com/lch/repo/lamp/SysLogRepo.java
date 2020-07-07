package com.lch.repo.lamp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.SysLog;

@Mapper
public interface SysLogRepo extends BaseRepo<SysLog>{

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<SysLog> getAllSysLog(@Param("createDate")String createDate);

}