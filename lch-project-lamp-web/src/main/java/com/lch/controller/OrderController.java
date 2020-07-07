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
import com.lch.entity.lamp.vo.OrderVo;
import com.lch.service.lamp.OrderService;

@RestController
@RequestMapping("/lamp/v1")

public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	/**
	 * 查询全部的购买记录
	 * 
	 * @return
	 */
	@GetMapping("/getAllOrder")
	@AuthIgnore(login = false)
	public AjaxResponse getAllOrder(Long status, String orderNo, String userName, String title, Date createDate) {
		return succees(orderService.getAllOrder(status, orderNo, userName, title, createDate));
	};

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getOrderById")
	@AuthIgnore(login = false)
	public AjaxResponse getOrderById(Long id) {
		return succees(orderService.getOrderById(id));
	};

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/delOrder")
	//@AuthIgnore(login = false)
	public AjaxResponse delOrder(@RequestParam(value = "ids") List<String> ids) throws ServiceException {
		return succees(orderService.delOrder(ids));
	};

	/**
	 * 编辑
	 * 
	 * @param Order
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updOrder")
	//@AuthIgnore(login = false)
	public AjaxResponse updOrder(OrderVo order) throws ServiceException {
		return succees(orderService.updOrder(order));
	};
	
	/**
	 * 更新状态
	 * 
	 * @param Order
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updOrderStatus")
	//@AuthIgnore(login = false)
	public AjaxResponse updOrderStatus(Long id, Long status) throws ServiceException {
		return succees(orderService.updOrderStatus(id, status));
	};


	/**
	 * 新增
	 * 
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addOrder")
	//@AuthIgnore(login = false)
	public AjaxResponse addOrder(OrderVo order) throws ServiceException {

		return succees(orderService.addOrder(order));
	};
	
	@GetMapping("/findAllUserByUserType")
	@AuthIgnore(login = false)
	public AjaxResponse findAllUserByUserType(Long userType) throws ServiceException {

		return succees(orderService.findAllUserByUserType(userType));
	};
	
	/**
	 * 获取完成订单数  未完成订单数 所有订单数
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/getOrderCountGroupByStatus")
	@AuthIgnore(login = false)
	public AjaxResponse getOrderCountGroupByStatus() throws ServiceException {

		return succees(orderService.getOrderCountGroupByStatus());
	};
	
	/**
	 * 获取订单的今日收入 本月收入
	 * @param status
	 * @param updateDate
	 * @param isCurrentMonth
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/getAllOrderAmount")
	@AuthIgnore(login = false)
	public AjaxResponse getAllOrderAmount(Long status, Integer isCurrentDay, Integer isCurrentMonth) throws ServiceException {
		
		return succees(orderService.getAllOrderAmount(status, isCurrentDay, isCurrentMonth));
	};
	
	/**
	 * 获取总收入  今日收入
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/getAmountCount")
	@AuthIgnore(login = false)
	public AjaxResponse getAmountCount() throws ServiceException {
		
		return succees(orderService.getAmountCount());
	};
	/**
	 * 查询某一个月每天的完成订单数，成交金额，净收入
	 */
	@GetMapping("/getEveryOrderCountAndAmounts")
	@AuthIgnore(login = false)
	public AjaxResponse getEveryOrderCountAndAmounts(Date dateDay) throws ServiceException {
		
		return succees(orderService.getEveryOrderCountAndAmounts(dateDay));
	};
	
	/**
	 * 查询某年每一月的订单数，总交易额，净收入
	 */
	@GetMapping("/getMonthOrderCountAndAmounts")
	//@AuthIgnore(login = false)
	public AjaxResponse getMonthOrderCountAndAmounts(Long year) throws ServiceException {
		String dateDay = "";
		if(year > 1990) { 
			dateDay = year.toString() + "-01-01";
		}else {
			return fail("年份输入不对！");
		}
		return succees(orderService.getMonthOrderCountAndAmounts(dateDay));
	};
	
	/**
	 * 查询所有的订单交易额 总收入
	 */
	@GetMapping("/getEarnTotalAndOrderCount")
	//@AuthIgnore(login = false)
	public AjaxResponse getEarnTotalAndOrderCount() throws ServiceException {
		
		return succees(orderService.getEarnTotalAndOrderCount());
	};
}
