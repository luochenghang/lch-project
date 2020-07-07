package com.lch.entity.lamp.criteria;

import com.lch.common.base.Criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = true)
public class GoodsTypeCriteria extends Criteria{

	private static final long serialVersionUID = 1L;
	
	private Long status; //0 禁用 1正常
	
	
}
