package com.lch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.config.AjaxResponse;
import com.lch.common.enums.ResponseCode;
import com.lch.service.common.DictService;


@RestController
@RequestMapping("/lamp/v1")
public class DictController {

	@Autowired
	private DictService dictService;

	/**
	 * 获取不同类型的数据列表
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/dictList", method = RequestMethod.GET)
	public AjaxResponse list(String type) {

		return AjaxResponse.toReturn(ResponseCode.SUCCEED, dictService.findList(type));
	}

	/**
	 * 获取不同类型的数据
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/dictValue", method = RequestMethod.GET)
	public AjaxResponse value(String type) {

		return AjaxResponse.toReturn(ResponseCode.SUCCEED, dictService.getValue(type));
	}

}

