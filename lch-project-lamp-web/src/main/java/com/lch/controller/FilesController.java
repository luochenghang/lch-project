package com.lch.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.FileResource;
import com.lch.service.common.ResourceService;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;

@RestController
@RequestMapping("/lamp/v1")
public class FilesController extends BaseController {

	@Autowired
	private ResourceService resourceService;

	/**
	 * 文件上传
	 * 
	 * @param file     文件
	 * @param floder   文件夹
	 * @param fileName 带扩展名的文件名
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@AuthIgnore(login = false) // 方便测试
	public AjaxResponse upload(MultipartFile file, String name, Integer isBanner, Long objId) {

		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String path = FileUtils.save(file, floder, name);
		if (isBanner == 1) {
			// 上传banner图,不做详细图了,就使用banner图代替详细图
			// 添加数据
			FileResource r = new FileResource();
			r.setObjId(objId).setType(1L).setUrl(path);

			return succees(resourceService.addFileResource(r));
		}

		return succees(path);
	}

	@RequestMapping(value = "/uploadCoverImg", method = RequestMethod.POST)
	@AuthIgnore(login = false) // 方便测试
	public AjaxResponse uploadCoverImg(MultipartFile file, String name) {

		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String path = FileUtils.save(file, floder, name);
		
		return succees(path);
	}
}
