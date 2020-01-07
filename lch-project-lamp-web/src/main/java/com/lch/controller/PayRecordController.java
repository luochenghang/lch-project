package com.lch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.vo.PayRecordVo;
import com.lch.service.lamp.PayRecordService;

@RestController
@RequestMapping("/lamp/v1")

public class PayRecordController extends BaseController {

	@Autowired
	private PayRecordService payRecordService;

	/**
	 * 查询全部的购买记录
	 * 
	 * @return
	 */
	@GetMapping("/getAllPayRecord")
	public AjaxResponse getAllPayRecord() {
		return succees(payRecordService.getAllPayRecord());
	};

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getPayRecordById")
	public AjaxResponse getPayRecordById(Long id) {
		return succees(payRecordService.getPayRecordById(id));
	};

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/delPayRecord")
	public AjaxResponse delPayRecord(Long id) throws ServiceException {
		return succees(payRecordService.delPayRecord(id));
	};

	/**
	 * 编辑
	 * 
	 * @param PayRecord
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updPayRecord")
	public AjaxResponse updPayRecord(PayRecordVo payRecord) throws ServiceException {
		return succees(payRecordService.updPayRecord(payRecord));
	};


	/**
	 * 新增
	 * 
	 * @param PayRecord
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addPayRecord")
	public AjaxResponse addPayRecord(PayRecordVo payRecord) throws ServiceException {

		return succees(payRecordService.addPayRecord(payRecord));
	};

}
