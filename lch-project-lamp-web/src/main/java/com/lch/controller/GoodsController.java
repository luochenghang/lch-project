package com.lch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.base.Criteria;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.Goods;
import com.lch.service.lamp.GoodsService;

@RestController
@RequestMapping("/lamp/v1")

public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	/**
	 * 查询全部的商品
	 * 
	 * @return
	 */
	@GetMapping("/getAllGoods")
	public AjaxResponse getAllGoods(Criteria criteria) {
		return succees(goodsService.getAllGoods(criteria));
	};

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getGoodsById")
	public AjaxResponse getGoodsById(Long id) {
		return succees(goodsService.getGoodsById(id));
	};

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/delGoods")
	public AjaxResponse delGoods(String ids) throws ServiceException {
		return succees(goodsService.delGoods(ids));
	};

	/**
	 * 编辑
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updGoods")
	public AjaxResponse updGoods(Goods goods) throws ServiceException {
		return succees(goodsService.updGoods(goods));
	};

	/**
	 * 上下架
	 * 
	 * @param status
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updGoodsStatus")
	public AjaxResponse updGoodsStatus(Long status, Long id) throws ServiceException {
		return succees(goodsService.updGoodsStatus(status, id));
	};

	/**
	 * 新增商品
	 * 
	 * @param goods
	 * @param name  文件上传的名字
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addGoods")
	public AjaxResponse addGoods(Goods goods) throws ServiceException {

		return succees(goodsService.addGoods(goods));
	};

}
