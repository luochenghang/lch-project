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
public class Goods extends DataEntity<Goods> {

	private static final long serialVersionUID = 1L;

	private String title; // 商品标题

	private Long goodsTypeId; // 商品类型id 和商品类型表相关联

	private String typeDesc; // 商品类型

	private String coverImg; // 封面图
	
	private Long status; // 0下架 1上架

	private Double costPrice; // 成本价

	private Double sellPrice; // 卖出价

	private Long sellCount; // 卖出去的总数

	private List<FileResource> detailImg; // 商品介绍资源

	private List<FileResource> bannerImg; // 商品banner图资源

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate; // 更新时间
}
