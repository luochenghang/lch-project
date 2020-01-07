package com.lch.service.lamp;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.vo.PayRecordVo;

public interface PayRecordService {

	/**
	 * 查询全部的购买记录
	 * @return
	 */
	List<PayRecordVo> getAllPayRecord();

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	PayRecordVo getPayRecordById(Long id);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Integer delPayRecord(Long id) throws ServiceException;

	/**
	 * 编辑
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer updPayRecord(PayRecordVo payRecord) throws ServiceException;


	/**
	 * 新增购买记录
	 * 
	 * @return
	 * @throws ServiceException
	 */
	Integer addPayRecord(PayRecordVo payRecord) throws ServiceException;

}