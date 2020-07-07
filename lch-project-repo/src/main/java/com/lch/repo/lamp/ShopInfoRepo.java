package com.lch.repo.lamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.ShopInfo;

@Mapper
public interface ShopInfoRepo extends BaseRepo<ShopInfo>{

	
	/**
	 * 添加店铺信息
	 * 
	 * @return
	 */
	@Insert("insert t_shop_info(shopName,address,phone,email,contactName) values(#{shopName},#{address},#{phone},#{email},#{contactName})")
	Integer addShopInfo(ShopInfo shopInfo);
	
	/**
	 * 修改店铺信息
	 * @return
	 */
	@Update("update t_shop_info set shopName = #{shopName},address = #{address},phone = #{phone},email = #{email},contactName = #{contactName}")
	Integer updShopInfo(ShopInfo shopInfo);
	
	/*
	 * 店铺信息
	 */
	@Select("select * from t_shop_info limit 0,1")
	ShopInfo getShopInfo();
	/*
	 * 店铺信息数量
	 */
	@Select("select count(1) from t_shop_info limit 0,1")
	int getShopInfoCount();

	ShopInfo getSysHomeOrderProductNum();
}