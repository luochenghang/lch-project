package com.lch.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.UserBase;
import com.lch.service.lamp.UseUserService;

@RestController
@RequestMapping("/lamp/v1")
public class UseUserController extends BaseController {


	@Autowired
	private UseUserService userService;
	
	@AuthIgnore(login = false)
	@GetMapping("/findAllUser")
	public AjaxResponse findAllUser(String queryStr, Date createDate) throws ServiceException {
		return succees(userService.findPageUser(queryStr, createDate));
	}

	@GetMapping("/getUser")
	public AjaxResponse getUser(Long id) {
		return succees(userService.getUser(id));
	}

	/**
	 * @return
	 */
	@PostMapping("/addUser")
	public AjaxResponse addUser(UserBase user) throws ServiceException{
		return succees(userService.addUser(user));
	}
	
	@PostMapping("/updUser")
	public AjaxResponse updUser(UserBase user) throws ServiceException{
		return succees(userService.updUser(user));
	}
	
	@GetMapping("/delUser")
	public AjaxResponse delUser(Long id) throws ServiceException {
		return succees(userService.delUser(id));
	}
	
	/**
	 * 查询前三名微信用户
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/findTopThreeUser")
	public AjaxResponse findTopThreeUser() throws ServiceException {
		return succees(userService.findTopThreeUser());
	}

}
