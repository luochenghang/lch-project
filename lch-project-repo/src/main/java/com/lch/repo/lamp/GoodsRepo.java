package com.lch.repo.lamp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lch.common.base.BaseRepo;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.Goods;

@Mapper
public interface GoodsRepo extends BaseRepo<Goods>{

	
	/**
	 * 查询全部的商品
	 * @return
	 */
	List<Goods> getAllGoods();
	
	/**
	 * 上下架
	 * @param status
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Integer updGoodsStatus(Long status,Long id) throws ServiceException;
	
}