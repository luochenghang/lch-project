package com.lch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.GoodsType;
import com.lch.service.lamp.GoodsTypeService;

@RestController
@RequestMapping("/lamp/v1")

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

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/batchDelGoodsType")
	//@AuthIgnore(login = false)
	public AjaxResponse delGoodsType(@RequestParam(value = "ids") List<String> ids) throws ServiceException {
		return succees(goodsTypeService.delGoodsType(ids));
	};

	/**
	 * 编辑
	 * 
	 * @param goods
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updGoodsType")
	//@AuthIgnore(login = false)
	public AjaxResponse updGoodsType(GoodsType goodsType) throws ServiceException {
		return succees(goodsTypeService.updGoodsType(goodsType));
	};

	/**
	 * 上下架
	 * 
	 * @param status
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/updGoodsTypeStatus")
	//@AuthIgnore(login = false)
	public AjaxResponse updGoodsStatus(Long id,Long status) throws ServiceException {
		return succees(goodsTypeService.updGoodsTypeStatus(id, status));
	};

	/**
	 * 新增商品类型
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/addGoodsType")
	//@AuthIgnore(login = false)
	public AjaxResponse addGoodsType(GoodsType goodsType) throws ServiceException {

		return succees(goodsTypeService.addGoodsType(goodsType));
	};
	
	/**
	 * 根据分类查询订单数量
	 * 
	 * @return
	 */
	@GetMapping("/getOrderCountGroupByType")
	@AuthIgnore(login = false)
	public AjaxResponse getOrderCountGroupByType() {
		return succees(goodsTypeService.getOrderCountGroupByType());
	};

}
