package com.lch.service.lamp;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.GoodsType;

public interface GoodsTypeService {

	/**
	 * 查询全部的商品类型
	 * 
	 * @return
	 */
	List<GoodsType> getAllGoodsType(Long status);

	/**
	 * 根据id查询商品类型
	 * 
	 * @param id
	 * @return
	 */
	GoodsType getGoodsTypeById(Long id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	Integer delGoodsType(List<String> idList) throws ServiceException;

	/**
	 * 编辑
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer updGoodsType(GoodsType goodsType) throws ServiceException;


	/**
	 * 新增商品类型
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer addGoodsType(GoodsType goodsType) throws ServiceException;

	/**
	 * 启用 禁用
	 * @param id
	 * @param status
	 * @return
	 */
	Integer updGoodsTypeStatus(Long id, Long status);
	
	/*
	 * 根据分类查询订单数量
	 */
	List<GoodsType> getOrderCountGroupByType();

}