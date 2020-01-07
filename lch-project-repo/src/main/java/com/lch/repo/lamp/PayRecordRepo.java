package com.lch.repo.lamp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.vo.PayRecordVo;

@Mapper
public interface PayRecordRepo extends BaseRepo<PayRecordVo>{

	
	/**
	 * 查询全部的商品
	 * @return
	 */
	List<PayRecordVo> getAllPayRecord();
	
}