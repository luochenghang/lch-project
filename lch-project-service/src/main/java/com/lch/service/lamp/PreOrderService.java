package com.lch.service.lamp;

import java.util.Date;
import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.entity.lamp.PreOrder;
import com.lch.entity.lamp.criteria.PreOrderCriteria;

public interface PreOrderService {

	/**
	 * 查询全部
	 * @return
	 */
	List<PreOrder> getAllPreOrder(Long status, Date createDate);

	Page<PreOrder> getPageAllPreOrder(PreOrderCriteria criteria);

	Integer updPreOrderStatus(Long id, Long status) throws ServiceException;
	
	Integer updPreOrderIsCollect(Long id, Long isCollect) throws ServiceException;
	
	int delPreOrder(List<String> idList) throws ServiceException;
	
	PreOrder getPreOrderById(Long id);
	
	int addPreOrder(PreOrder preOrder) throws ServiceException;

	//获取每种状态的数量
	PreOrder getPreOrderCountGroupByStatus();

	int batchAddOrder(List<String> ids) throws ServiceException;
}