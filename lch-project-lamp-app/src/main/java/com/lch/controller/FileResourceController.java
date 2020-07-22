package com.lch.controller;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.FileResource;
import com.lch.service.common.ResourceService;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lampApp/v1")
public class FileResourceController extends BaseController {

	@Autowired
	private ResourceService resourceService;


	//查询banner图
	@GetMapping("/getBanner")
	public AjaxResponse getBanner() {
		return succees(resourceService.getBanner());
	}



}
