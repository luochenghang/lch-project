package com.lch.repo.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lch.entity.common.Dict;

/**
 * 数据字典的Repo
 */

@Mapper
public interface DictRepo {

	/**
	 * 根据类型获取数据
	 * @param type
	 * @return
	 */
	@Select("SELECT `key`, value, tag, url1, url2 FROM t_sys_dict WHERE type = #{type} ORDER BY `sort` ASC")
	List<Dict> findList(String type);
	
	/**
	 * 根据类型获取数据
	 * @param type
	 * @return
	 */
	@Select("SELECT value FROM t_sys_dict WHERE type = #{type} LIMIT 1")
	String getValue(String type);
}
