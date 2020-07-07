package com.lch.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.FileResource;
import com.lch.repo.common.ResourceRepo;
import com.lch.service.common.ResourceService;


/**
 * 数据字典业务逻辑处理类
 */
@Service
@Transactional(readOnly = false)
public class ResourceServiceImpl  implements ResourceService {

	@Autowired
	private ResourceRepo repo;

	@Override
	public int addFileResource(FileResource r) {
		return repo.addFileResource(r);
	}

	@Override
	public List<FileResource> getAllFileResourceGroupByType() {
		return repo.getAllFileResourceGroupByType();
	}

	@Override
	public Integer updStatus(Long id, Long status) throws ServiceException {
		return repo.updStatus(id, status);
	}

	@Override
	public Integer delFileResource(List<String> idList) throws ServiceException {
		return repo.batchDelete(idList);
	}

	@Override
	public List<FileResource> getAllFileResource(Long status, Long type, String title) {
		return repo.getAllFileResource(status, type, title);
	}



}
