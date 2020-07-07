package com.lch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.service.lamp.SysLogService;

@RestController
@RequestMapping("/lamp/v1")

public class SysLogController extends BaseController {

	@Autowired
	private SysLogService sysLogService;



	/**
	 * 根据日志
	 * 
	 * @return
	 */
	@GetMapping("/getAllSysLog")
	@AuthIgnore(login = false)
	public AjaxResponse getAllSysLog(String createDate) {
		return succees(sysLogService.getAllSysLog(createDate));
	};

}
