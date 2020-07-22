package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.GoodsType;
import com.lch.service.lamp.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lampApp/v1")

public class GoodsTypeController extends BaseController {

	@Autowired
	private GoodsTypeService goodsTypeService;

	/**
	 * 查询全部的商品
	 * 
	 * @return
	 */
	@GetMapping("/getAllGoodsType")
	@AuthIgnore(login = false)
	public AjaxResponse getAllGoodsType(Long status) {
		return succees(goodsTypeService.getAllGoodsType(status));
	};

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getGoodsTypeById")
	@AuthIgnore(login = false)
	public AjaxResponse getGoodsTypeById(Long id) {
		return succees(goodsTypeService.getGoodsTypeById(id));
	};

}
