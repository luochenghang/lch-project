package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.UserBase;
import com.lch.entity.common.bo.UlBo;
import com.lch.service.lamp.UseUserService;
import com.lch.service.subpervise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/lampApp/v1")
public class UseUserController extends BaseController {


	@Autowired
	private UseUserService userUserService;
	


	@GetMapping("/getUser")
	public AjaxResponse getUser(Long id) {
		return succees(userUserService.getUser(id));
	}


	@Autowired
	private UserService userService;


	@AuthIgnore(login = false)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AjaxResponse login(UlBo bo) throws ServiceException {
		bo.setUserType(App.JBS_TYPE);
		Map<String, Object> map = userService.login(bo, null, App.JBSLAMP_APPID, App.JBSLAMP_APP_SECRET);
		return succees(map);
	}

	

	


}
