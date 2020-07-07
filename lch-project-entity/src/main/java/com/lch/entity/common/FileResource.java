package com.lch.entity.common;

import java.util.Date;

import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FileResource extends DataEntity<FileResource> {

	private static final long serialVersionUID = 1L;

	private Long objId;// 业务对象id
	private String title; //描述业务对象

	private Long type;// 资源类型（1灯具首页banner轮播图）
	private String typeDesc;// 资源类型描述
	
	private Long status; //1正常 0禁用
	
	private Long sort;

	private String url; // 资源路径

	private Date createDate; // 创建时间
	
	//每个类型的数量
	private Long typeCount;
	//总数
	private Long sumCount;
	
	
}
