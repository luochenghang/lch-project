package com.lch.entity.lamp.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.Criteria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = true)
public class PreOrderCriteria extends Criteria{

	private static final long serialVersionUID = 1L;
	
	private Long status; // 0未读 1生成订单  2未生成订单

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 商品类型

	
	
}
