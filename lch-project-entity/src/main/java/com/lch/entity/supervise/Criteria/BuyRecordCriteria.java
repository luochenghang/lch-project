package com.lch.entity.supervise.Criteria;

import com.lch.common.base.Criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = true)
public class BuyRecordCriteria extends Criteria{

	private static final long serialVersionUID = 1L;
	
	private Long userId; //用户id
	
	private Long status; //0配送中 1已到货  2未到货
	
	private Long isMe; //0他  1我
	
	
}
