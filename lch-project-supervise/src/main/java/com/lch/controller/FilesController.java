package com.lch.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;

@RestController
@RequestMapping("/api")
public class FilesController extends BaseController {

	/**
	 * 文件上传
	 * 
	 * @param file     文件
	 * @param floder   文件夹
	 * @param fileName 带扩展名的文件名
	 * @return
	 */
	@RequestMapping(value = "/v1/upload", method = RequestMethod.POST)
	public AjaxResponse upload(MultipartFile file, String fileName) {

		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String path = FileUtils.save(file, floder, fileName);
		return succees(path);
	}
}
