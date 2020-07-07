package com.lch.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.lamp.Goods;
import com.lch.service.lamp.GoodsService;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;

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
	@AuthIgnore(login = false)
	public AjaxResponse getAllGoods(Long status, Long goodsTypeId, String title) {
		return succees(goodsService.getAllGoods(status, goodsTypeId, title));
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

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/delGoods")
	//@AuthIgnore(login = false)
	public AjaxResponse delGoods(@RequestParam(value = "ids") List<String> ids) throws ServiceException {
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
	//@AuthIgnore(login = false)
	public AjaxResponse updGoods(MultipartFile file, Goods goods) throws ServiceException {
		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		if(file != null) {//修改了封面图
			String name = file.getOriginalFilename();//文件名
			String path = FileUtils.save(file, floder, name);
			goods.setCoverImg(App.BASE_PATH + path);
		}
		
		int result = goodsService.updGoods(goods);
		if(result > 0 ) {
			return succees(result);
		}
		return fail("已经存在该产品了，请换一个名称或者产品类型！");
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
	//@AuthIgnore(login = false)
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
	//@AuthIgnore(login = false)
	public AjaxResponse addGoods(MultipartFile file, Goods goods, String filesDetailPath) throws ServiceException {
		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String name = file.getOriginalFilename();//文件名
		String path = FileUtils.save(file, floder, name);
		goods.setCoverImg(App.BASE_PATH + path);
		int result = goodsService.addGoods(goods, filesDetailPath);
		if(result > 0 ) {
			return succees(result);
		}
		return fail("已经存在该产品了，请换一个名称或者产品类型！");
	};

}
