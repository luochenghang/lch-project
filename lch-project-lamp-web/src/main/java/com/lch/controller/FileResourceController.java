package com.lch.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.FileResource;
import com.lch.service.common.ResourceService;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;

@RestController
@RequestMapping("/lamp/v1")
public class FileResourceController extends BaseController {

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
	//@AuthIgnore(login = false) // 方便测试
	public AjaxResponse upload(MultipartFile file, FileResource fileResource) {

		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String name = file.getOriginalFilename();//文件名
		String path = FileUtils.save(file, floder, name);
		fileResource.setUrl(App.BASE_PATH + path);
		if(fileResource.getObjId() == null) {
			fileResource.setObjId(0L);
		}
//		if(fileResource.getType().equals(1L)) {
//			fileResource.setTypeDesc("首页banner轮播图！");
//		}
		return succees(resourceService.addFileResource(fileResource));
	}
	
	
	

	@RequestMapping(value = "/uploadCoverImg", method = RequestMethod.POST)
	@AuthIgnore(login = false) // 方便测试
	public AjaxResponse uploadCoverImg(MultipartFile file, String name) {

		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String path = FileUtils.save(file, floder, name);
		
		return succees(App.BASE_PATH + path);
	}
	
	@RequestMapping(value = "/uploadGoodsDetail", method = RequestMethod.POST)
	//@AuthIgnore(login = false) // 方便测试
	public AjaxResponse uploadGoodsDetail(MultipartFile file, String name) {

		String floder = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String path = FileUtils.save(file, floder, name);
		
		return succees(App.BASE_PATH + path);
	}
	
	@GetMapping("/getAllFileResourceGroupByType")
	@AuthIgnore(login = false)
	public AjaxResponse getAllFileResourceGroupByType() {
		return succees(resourceService.getAllFileResourceGroupByType());
	}
	
	@GetMapping("/getAllFileResource")
	@AuthIgnore(login = false)
	public AjaxResponse getAllFileResource(Long status, Long type, String title) {
		return succees(resourceService.getAllFileResource(status, type, title));
	}
	
	@PostMapping("/updFileStatus")
	//@AuthIgnore(login = false)
	public AjaxResponse updStatus(Long id,Long status) throws ServiceException {
		return succees(resourceService.updStatus(id, status));
	}
	
	@GetMapping("/delFileResource")
	//@AuthIgnore(login = false)
	public AjaxResponse delFileResource(@RequestParam(value = "ids") List<String> ids) throws ServiceException {
		return succees(resourceService.delFileResource(ids));
	}
	
	
}
