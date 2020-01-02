package com.lch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.DataController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.supervise.BuyRecord;
import com.lch.entity.supervise.Criteria.BuyRecordCriteria;
import com.lch.service.subpervise.BuyRecordService;

@RestController
@RequestMapping("/api")
public class BuyRecordController extends DataController<BuyRecordService> {

	/**
	 * 添加兑换记录
	 * 
	 * @param BuyRecord
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/addBuyRecord")
	public AjaxResponse addBuyRecord(BuyRecord BuyRecord) throws ServiceException {
		return succees(service.addBuyRecord(BuyRecord));
	}

	/**
	 * 修改兑换记录
	 * 
	 * @param BuyRecord
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/updBuyRecord")
	public AjaxResponse updBuyRecord(BuyRecord BuyRecord) throws ServiceException {
		return succees(service.updBuyRecord(BuyRecord));
	}

	/**
	 * 删除兑换记录
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/v1/delBuyRecord")
	public AjaxResponse delBuyRecord(Long id) throws ServiceException {
		return succees(service.delBuyRecord(id));
	}

	/**
	 * 获取兑换记录
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping("/v1/getAllBuyRecord")
	public AjaxResponse getAllBuyRecord(BuyRecordCriteria criteria) {
		return succees(service.getAllBuyRecord(criteria));
	}

}
