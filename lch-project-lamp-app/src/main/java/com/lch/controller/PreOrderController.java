package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.config.UserSessionUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.PreOrder;
import com.lch.service.lamp.PreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/lampApp/v1")

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

	@GetMapping("/getPreOrderByUserId")
	public AjaxResponse getPreOrderByUserId() {
		Long id = UserSessionUtils.getCurrentUserId();
		return succees(preOrderService.getPreOrderByUserId(id));
	};


	//获取每种状态的数量
	@GetMapping("/getPreOrderCountGroupByStatus")
	public AjaxResponse  getPreOrderCountGroupByStatus() {
		return succees(preOrderService.getPreOrderCountGroupByStatus());
	};


	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updPreOrderStatus")
	//@AuthIgnore(login = false)
	public AjaxResponse updPreOrderStatus(Long id, Long status) throws ServiceException {
		return succees(preOrderService.updPreOrderStatus(id, status));
	};


	/**
	 * 新增
	 * @param preOrder
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addPreOrder")
	public AjaxResponse addPreOrder(PreOrder preOrder) throws ServiceException {

		return succees(preOrderService.addPreOrder(preOrder));
	};

	/**
	 * 删除收藏
	 * @param id
	 * @param isCollect
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updPreOrderIsCollect")
	public AjaxResponse updPreOrderIsCollect(Long id, Long isCollect) throws ServiceException {

		return succees(preOrderService.updPreOrderIsCollect(id,isCollect));
	};
	
}
