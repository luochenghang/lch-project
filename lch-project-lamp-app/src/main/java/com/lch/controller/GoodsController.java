package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.Goods;
import com.lch.entity.lamp.criteria.GoodsCriteria;
import com.lch.service.lamp.GoodsService;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lampApp/v1")

public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 分页查询全部的商品
	 * 
	 * @return
	 */
	@GetMapping("/getPageAllGoods")
	@AuthIgnore(login = false)
	public AjaxResponse getAllGoods(GoodsCriteria criteria) {
		return succees(goodsService.getPageAllGoods(criteria));
	};

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getGoodsById")
	@AuthIgnore(login = false)
	public AjaxResponse getGoodsById(Long id) {
		return succees(goodsService.getGoodsById(id));
	};




}
