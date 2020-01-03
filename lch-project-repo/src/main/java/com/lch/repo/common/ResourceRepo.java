package com.lch.repo.common;

import org.apache.ibatis.annotations.Mapper;

import com.lch.entity.common.FileResource;

/**
 * 数据字典的Repo
 */

@Mapper
public interface ResourceRepo {
	
	/**
	 * 添加文件资源
	 * 
	 * @param r
	 * @return
	 */
	int addFileResource(FileResource r);

}
