package com.lch.entity.lamp;

import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.DataEntity;
import com.lch.entity.common.FileResource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsBanner extends DataEntity<GoodsBanner> {

	private static final long serialVersionUID = 1L;

	private String title; // 礼物标题

	private String coverImg; // 封面图

	private Long costPrice; // 成本价

	private Long sellPrice; // 卖出价
	
	private Long sellCount; // 卖出去的总数

	private List<FileResource> files; // 审核失败原因

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate; // 创建时间
}
