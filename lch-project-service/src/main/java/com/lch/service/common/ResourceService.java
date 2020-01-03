package com.lch.service.common;

import com.lch.entity.common.FileResource;

/**
 * 数据字典Service
 */
public interface ResourceService {

	/**
	 * 添加文件资源
	 * 
	 * @param r
	 * @return
	 */
	int addFileResource(FileResource r);
}
