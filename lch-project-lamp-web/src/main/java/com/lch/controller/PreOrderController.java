package com.lch.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.PreOrder;
import com.lch.service.lamp.PreOrderService;

@RestController
@RequestMapping("/lamp/v1")

public class PreOrderController extends BaseController {

	@Autowired
	private PreOrderService preOrderService;

	/**
	 * 查询全部的购买记录
	 * 
	 * @return
	 */
	@GetMapping("/getAllPreOrder")
	@AuthIgnore(login = false)
	public AjaxResponse getAllPreOrder(Long status, Date createDate) {
		return succees(preOrderService.getAllPreOrder(status, createDate));
	};

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getPreOrderById")
	@AuthIgnore(login = false)
	public AjaxResponse getPreOrderById(Long id) {
		return succees(preOrderService.getPreOrderById(id));
	};
	

	//获取每种状态的数量
	@GetMapping("/getPreOrderCountGroupByStatus")
	public AjaxResponse  getPreOrderCountGroupByStatus() {
		return succees(preOrderService.getPreOrderCountGroupByStatus());
	};

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/delPreOrder")
	public AjaxResponse delPreOrder(@RequestParam(value = "ids") List<String> ids) throws ServiceException {
		return succees(preOrderService.delPreOrder(ids));
	};
	
	@PostMapping("/batchAddOrder")
	public AjaxResponse batchAddOrder(@RequestParam(value = "ids") List<String> ids) throws ServiceException {
		return succees(preOrderService.batchAddOrder(ids));
	};

	
	/**
	 * 更新状态
	 * 
	 * @param PreOrder
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updPreOrderStatus")
	//@AuthIgnore(login = false)
	public AjaxResponse updPreOrderStatus(Long id, Long status) throws ServiceException {
		return succees(preOrderService.updPreOrderStatus(id, status));
	};


	/**
	 * 新增 app
	 * 
	 * @param PreOrder
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addPreOrder")
	public AjaxResponse addPreOrder(PreOrder preOrder) throws ServiceException {

		return succees(preOrderService.addPreOrder(preOrder));
	};
	
	
}
