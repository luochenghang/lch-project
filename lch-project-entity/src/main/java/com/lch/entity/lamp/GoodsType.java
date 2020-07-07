package com.lch.entity.lamp;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsType extends DataEntity<GoodsType> {

	private static final long serialVersionUID = 1L;

	private String name; // 名称

	private Long sort ; // 排序编号

	private Long status; // 启用1  禁用0

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; // 更新时间

	//商品分类下的数量
	private Integer goodsCount;
	
	//商品总数量
	private Integer sumCount;
	
	//商品分类下订单数量
	private Integer orderCount;
}
