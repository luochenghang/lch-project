package com.lch.controller;

import com.lch.utils.GetLngAndLat;
import com.lch.utils.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.ShopInfo;
import com.lch.service.lamp.ShopInfoService;

@RestController
@RequestMapping("/lamp/v1")

public class ShopInfoController extends BaseController {

	@Autowired
	private ShopInfoService shopInfoService;



	/**
	 * 获取店铺
	 * 
	 * @return
	 */
	@GetMapping("/getShopInfo")
	public AjaxResponse getShopInfo() {
		return succees(shopInfoService.getShopInfo());
	}
	/**
	 * 编辑
	 *
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updShopInfo")
	public AjaxResponse updShopInfo(ShopInfo shopInfo) throws ServiceException {
		Location location = GetLngAndLat.getURLContent(shopInfo.getAddress());
		if (location != null){
			shopInfo.setLat(location.getLat()).setLng(location.getLng());
		}else{
			return fail("请输入详细的地址！");
		}
		return succees(shopInfoService.updShopInfo(shopInfo));
	};


	/**
	 * 新增
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addShopInfo")
	public AjaxResponse addShopInfo(ShopInfo shopInfo) throws ServiceException {
		Location location = GetLngAndLat.getURLContent(shopInfo.getAddress());
		if (location != null){
			shopInfo.setLat(location.getLat()).setLng(location.getLng());
		}else{
			return fail("请输入详细的地址！");
		}
		return succees(shopInfoService.addShopInfo(shopInfo));
	};
	
	////主页界面需要的api
	@GetMapping("/getSysHomeOrderProductNum")
	public AjaxResponse getSysHomeOrderProductNum() throws ServiceException {
		return succees(shopInfoService.getSysHomeOrderProductNum());
	};
	
	

}
