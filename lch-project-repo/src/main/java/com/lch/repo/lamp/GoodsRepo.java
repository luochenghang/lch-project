package com.lch.repo.lamp;

import java.util.List;

import com.lch.entity.lamp.criteria.GoodsCriteria;
import com.lch.entity.supervise.GiftInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.Goods;

@Mapper
public interface GoodsRepo extends BaseRepo<Goods>{

	
	/**
	 * 查询全部的商品
	 * @return
	 */
	List<Goods> getAllGoods(Long status, Long goodsTypeId, String title, String orderBy, Long userId);
	
	/**
	 * 通过title 和type 查询是否存在产品
	 * @param title
	 * @param goodsTypeId
	 * @return
	 */
	@Select("SELECT count(1) FROM t_goods WHERE title = #{title} and goodsTypeId =#{goodsTypeId}")
	int getGoodsByTypeAndTitle(String title, Long goodsTypeId);
	
	/**
	 * 上下架
	 * @param status
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Integer updGoodsStatus(Long status,Long id) throws ServiceException;
	
	/**
	 * 更新卖出数量
	 * @param sellCount
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@Update("update t_goods set sellCount = sellCount + #{sellCount} where id = #{id}")
	Integer updGoodsSellCount(Long sellCount,Long id) throws ServiceException;

}