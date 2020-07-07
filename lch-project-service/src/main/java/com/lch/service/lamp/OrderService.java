package com.lch.service.lamp;

import java.util.Date;
import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.UserBase;
import com.lch.entity.lamp.EchartsData;
import com.lch.entity.lamp.vo.OrderVo;

public interface OrderService {

	/**
	 * 查询全部的订单
	 * @return
	 */
	List<OrderVo> getAllOrder(Long status, String orderNo, String userName,String title, Date createDate);
	
	List<OrderVo> getAllOrderAmount(Long status, Integer isCurrentDay, Integer isCurrentMonth);
	
	OrderVo getAmountCount();
	
	/**
	 * 根据订单编号查询
	 * 
	 * @param id
	 * @return
	 */
	OrderVo getOrderCountGroupByStatus();
	
	/**
	 * 更新订单状态 
	 * @param id
	 * @param status 0未完成 1已完成
	 * @return
	 * @throws ServiceException 
	 */
	Integer updOrderStatus(Long id, Long status) throws ServiceException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	OrderVo getOrderById(Long id);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Integer delOrder(List<String> idList) throws ServiceException;

	/**
	 * 编辑
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer updOrder(OrderVo Order) throws ServiceException;


	/**
	 * 新增购买记录
	 * 
	 * @return
	 * @throws ServiceException
	 */
	Integer addOrder(OrderVo Order) throws ServiceException;
	
	/**
	 * 通过类型获取所有的用户信息
	 * @param userType
	 * @return
	 */
	List<UserBase> findAllUserByUserType(Long userType);
	
	/**
	 * 查询本月每一天的订单数，总交易额，净收入
	 * @return
	 */
	List<EchartsData> getEveryOrderCountAndAmounts(Date dateDay);
	
	/**
	 * 查询某年每一月的订单数，总交易额，净收入
	 * @return  dateDay xxxx-01-01 主要是年份
	 */
	List<EchartsData> getMonthOrderCountAndAmounts(String dateDay);
	
	/**
	 * 查询所有的订单交易额 总收入
	 * @return
	 */
	OrderVo getEarnTotalAndOrderCount();
	
}