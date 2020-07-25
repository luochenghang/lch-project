package com.lch.entity.lamp.criteria;

import com.lch.common.base.Criteria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = true)
public class GoodsCriteria extends Criteria{

	private static final long serialVersionUID = 1L;
	
	private Long status; //0 下架 1上架

	private Long goodsTypeId; // 商品类型

	private String orderBy; //根据什么排序  null 或者'' 根据创建时间降序 sellPrice 根据价格排序  sellCount 根据销售数量倒叙

	
	
}
