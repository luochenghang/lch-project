package com.lch.entity.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 数据字典实体类
 */
@Data
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 主键

	// (example = "字典的KEY", value = "字典的KEY", required = false)
	private Integer key;

	// (example = "字典的值", value = "字典的值", required = false)
	private String value;

	// (example = "数据类型", value = "数据类型", required = true)
	private String type;

	// (example = "字典描述", value = "字典描述", required = true)
	private String desc;

	// (example = "排序（值越小越靠前）", value = "排序（值越小越靠前）", required = true)
	private Integer sort;

	// (example = "关联标签类型", value = "关联标签类型", required = true)
	private String tag;

	// (example = "资源路径1", value = "资源路径1", required = true)
	private String url1;

	// (example = "资源路径2", value = "资源路径2", required = true)
	private String url2;

}
