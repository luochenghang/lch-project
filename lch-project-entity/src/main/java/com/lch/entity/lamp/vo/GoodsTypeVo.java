package com.lch.entity.lamp.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsTypeVo extends DataEntity<GoodsTypeVo> {

	private static final long serialVersionUID = 1L;

	private String name; // 礼物标题

	private Long sort ; // 封面图

	private Long status; // 成本价

	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date createDate; // 创建时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date updateDate; // 更新时间
	
	//商品分类下的数量
	private Integer goodsCount;

}
