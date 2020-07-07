package com.lch.service.common;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
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
	
	/**
	 * 查询所有的资源，根据类型分类
	 * @return
	 */
	List<FileResource> getAllFileResourceGroupByType();
	
	/**
	 * 根据type查询 如果为null 查询全部
	 * @param type
	 * @return
	 */
	List<FileResource> getAllFileResource(Long status, Long type, String title);
	
	/**
	 * 启用 禁用
	 * @return
	 */
	Integer updStatus(Long id,Long status) throws ServiceException;
	
	/**
	 * 批量删除
	 * @param idList
	 * @return
	 * @throws ServiceException
	 */
	Integer delFileResource(List<String> idList) throws ServiceException;
}
