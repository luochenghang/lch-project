package com.lch.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.bo.UlBo;
import com.lch.service.subpervise.UserService;


@RestController
@RequestMapping("/api")
public class UserController extends BaseController{
	
	
	@Autowired
	private UserService userService;
	
	
	@AuthIgnore(login = false)
	@RequestMapping(value = "/v1/login", method = RequestMethod.POST)
	public AjaxResponse login(UlBo bo) throws ServiceException {
		bo.setUserType(App.SUPERVISE_TYPE);
		Map<String, Object> map = userService.login(bo, null, App.SUPERVISE_APPID, App.SUPERVISE_APP_SECRET);
		return succees(map);
	}
}
