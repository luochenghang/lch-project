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
public class Order extends DataEntity<Order> {

	private static final long serialVersionUID = 1L;

	private Long orderNo;// 订单编号
	
	private Long userType; // 1 微信用户 2线下用户
	
	private Long goodsId; // 商品id

	private Long userId; // 用户id

	private Long num; // 购买数量
	
	private Long status; // 订单状态 //0未完成 1已完成
	
	private Double costPrice; // 成本价
	private Double sellPrice; // 卖出价
	private Double finalPrice; // 最终价格

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; // 更新时间
}
