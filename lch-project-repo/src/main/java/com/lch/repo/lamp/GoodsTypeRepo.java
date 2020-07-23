package com.lch.repo.lamp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.GoodsType;

@Mapper
public interface GoodsTypeRepo extends BaseRepo<GoodsType>{

	
	/**
	 * 查询全部的商品
	 * 
	 * @return
	 */
	List<GoodsType> getAllGoodsType(@Param("status") Long status);
	
	/**
	 * 启用 禁用
	 * @return
	 */
	@Update("update t_goods_type set status = #{status} where id = #{id}")
	Integer updStatus(@Param("id")Long id, @Param("status")Long status);
	
	/*
	 * 根据名字查询分类
	 */
	@Select("select * from t_goods_type where name = #{name}")
	GoodsType getGoodsTypeByName(String name);
	/*
	 * 根据分类查询订单数量
	 */
	List<GoodsType> getOrderCountGroupByType();
}