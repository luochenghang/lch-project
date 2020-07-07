package com.lch.service.lamp;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.ShopInfo;

public interface ShopInfoService {

	/**
	 * 添加店铺信息
	 * 
	 * @return
	 */
	Integer addShopInfo(ShopInfo shopInfo) throws ServiceException;
	
	/**
	 * 修改店铺信息
	 * @return
	 */
	Integer updShopInfo(ShopInfo shopInfo) throws ServiceException;
	
	/*
	 * 店铺信息
	 */
	ShopInfo getShopInfo();

	//主页界面需要的api
	ShopInfo getSysHomeOrderProductNum();
	

}