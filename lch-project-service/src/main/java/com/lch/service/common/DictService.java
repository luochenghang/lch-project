package com.lch.service.common;

import java.util.List;

import com.lch.entity.common.Dict;

/**
 * 数据字典Service
 */
public interface DictService {

	/**
	 * 根据数据类型获取数据
	 * @param type
	 * @return
	 */
	List<Dict> findList(String type);

	/**
	 * 根据数据类型获取数据
	 * @param type
	 * @return
	 */
	String getValue(String type);
}
