package com.lch.service.lamp.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.ShopInfo;
import com.lch.repo.lamp.ShopInfoRepo;
import com.lch.service.lamp.ShopInfoService;

@Service
@Transactional(readOnly = false)
public class ShopInfoServiceImpl extends DataService<ShopInfoRepo, ShopInfo> implements ShopInfoService {

	@Override
	public Integer addShopInfo(ShopInfo shopInfo) throws ServiceException {
		if(repo.getShopInfoCount() > 0) {
			doThrow("店铺信息已存在");
		}
		return repo.addShopInfo(shopInfo);
	}

	@Override
	public Integer updShopInfo(ShopInfo shopInfo) throws ServiceException {
		return repo.updShopInfo(shopInfo);
	}

	@Override
	public ShopInfo getShopInfo() {
		return repo.getShopInfo();
	}

	@Override
	public ShopInfo getSysHomeOrderProductNum() {
		// TODO Auto-generated method stub
		return repo.getSysHomeOrderProductNum();
	}

	

}
