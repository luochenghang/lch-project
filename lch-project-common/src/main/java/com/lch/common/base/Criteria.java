package com.lch.common.base;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础查询条件封装对象
 * @date  @date 2019年12月10日 下午3:23:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Criteria extends DataEntity<Criteria>{

	private static final long serialVersionUID = 1L;

	protected String queryStr;// 自定义查询对象

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date beginTime;// 开始时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date endTime; // 结束时间

	protected Integer pageNum; // 页码

	protected Integer pageSize; // 页大小

	public Integer getPageNum() {
		return pageNum == null ? 1 : pageNum;
	}

	public Integer getPageSize() {
		return pageSize == null ? 15 : pageSize;
	}

}
