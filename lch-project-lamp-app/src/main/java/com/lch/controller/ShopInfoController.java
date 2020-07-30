package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.ShopInfo;
import com.lch.service.lamp.ShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lampApp/v1")

public class ShopInfoController extends BaseController {

	@Autowired
	private ShopInfoService shopInfoService;



	/**
	 * 获取店铺
	 * 
	 * @return
	 */
	@GetMapping("/getShopInfo")
	@AuthIgnore(login = false)
	public AjaxResponse getShopInfo() {
		return succees(shopInfoService.getShopInfo());
	}


//
//	////主页界面需要的api
//	@GetMapping("/getSysHomeOrderProductNum")
//	public AjaxResponse getSysHomeOrderProductNum() throws ServiceException {
//		return succees(shopInfoService.getSysHomeOrderProductNum());
//	};
//
	

}
