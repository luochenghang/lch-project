package com.lch.repo.lamp;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.EchartsData;
import com.lch.entity.lamp.vo.OrderVo;

@Mapper
public interface OrderRepo extends BaseRepo<OrderVo>{

	
	/**
	 * 查询全部的订单
	 * @return
	 */
	List<OrderVo> getAllOrder(Long status, String orderNo, String userName, String title, Date createDate);
	
	List<OrderVo> getAllOrderAmount(Long status, Integer isCurrentDay, Integer isCurrentMonth);
	
	OrderVo getAmountCount();
	
	OrderVo getOrderCountGroupByStatus();
	
	/**
	 * 更新订单状态 
	 * @param id
	 * @param status 0未完成 1已完成
	 * @return
	 */
	@Update("update t_order set status = #{status} where id = #{id}")
	Integer updOrderStatus(Long id, Long status);
	
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