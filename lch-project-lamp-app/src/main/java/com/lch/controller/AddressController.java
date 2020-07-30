package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.config.UserSessionUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.Address;
import com.lch.service.common.ResourceService;
import com.lch.service.lamp.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lampApp/v1")
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;


	//查询用户的所有地址列表
	@GetMapping("/getAddressList")
	public AjaxResponse getAddressList() {

		return succees(addressService.getAddressList());

	}

	//根据id查询用户的地址信息
	@GetMapping("/getAddressById")
	public AjaxResponse getAddressById(Long id) {
		return succees(addressService.getAddressById(id));
	}

	//添加或者修改用户的地址
	@PostMapping("/addAddress")
	public AjaxResponse addAddress(Address address) throws ServiceException {
		return succees(addressService.updAddress(address));
	}

	//删除用户的地址记录
	@PostMapping("/delAddress")
	public AjaxResponse delAddress(Long id) throws ServiceException {
		return succees(addressService.delAddress(id));
	}





}
