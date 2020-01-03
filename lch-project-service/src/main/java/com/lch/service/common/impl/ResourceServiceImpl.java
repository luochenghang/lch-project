package com.lch.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.entity.common.FileResource;
import com.lch.repo.common.ResourceRepo;
import com.lch.service.common.ResourceService;


/**
 * 数据字典业务逻辑处理类
 */
@Service
@Transactional(readOnly = false)
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepo repo;

	@Override
	public int addFileResource(FileResource r) {
		return repo.addFileResource(r);
	}



}
