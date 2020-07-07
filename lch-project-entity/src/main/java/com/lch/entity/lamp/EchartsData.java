package com.lch.entity.lamp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EchartsData{

	
	private String days; //某一天
	
	private String months; //某一月
	
	private int orderCount; //订单数量
	
	private Float totalPrice; //总交易额
	
	private Float totalEarnPrice; //净收入
	
	
}
