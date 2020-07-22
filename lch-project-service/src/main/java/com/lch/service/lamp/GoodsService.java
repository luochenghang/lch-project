package com.lch.service.lamp;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.entity.lamp.Goods;
import com.lch.entity.lamp.criteria.GoodsCriteria;

public interface GoodsService {

	/**
	 * 查询全部的商品
	 * 
	 * @return
	 */
	List<Goods> getAllGoods(Long status, Long goodsTypeId, String title);

	/**
	 * 查询全部的商品
	 *
	 * @return
	 */
	Page<Goods> getPageAllGoods(GoodsCriteria criteria);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	Goods getGoodsById(Long id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	Integer delGoods(List<String> ids) throws ServiceException;

	/**
	 * 编辑
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer updGoods(Goods goods) throws ServiceException;

	/**
	 * 上下架
	 * 
	 * @param status
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Integer updGoodsStatus(Long status, Long id) throws ServiceException;

	/**
	 * 新增商品
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	Integer addGoods(Goods goods, String filesDetailPath) throws ServiceException;

}