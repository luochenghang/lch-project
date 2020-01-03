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

	private Long type;// 资源类型（1小橙卡小程序评论图片

	private String url; // 资源路径

	private Date createDate; // 创建时间
}
