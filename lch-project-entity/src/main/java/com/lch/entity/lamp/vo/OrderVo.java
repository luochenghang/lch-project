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
public class OrderVo extends DataEntity<OrderVo> {

	private static final long serialVersionUID = 1L;
	
	private String orderNo;// 订单编号
	
	private Long goodsId; // 商品id
	
	private String title; // 商品名称
	private String coverImg; // 商品封面图
	
	private String goodsTypeName; // 商品类型
	
	private String phone; // 用户电话

	private String userName; // 用户名称

	private Long num; // 购买数量
	
	private Long userId; // 用户id
	
	private Long userType; // 1 微信用户 2线下用户

	private Long status; // 订单状态 //0未完成 1已完成
	private Long goodsStatus; // 产品状态 0下架 1上架
	
	private Long sex; //性别 0男1女
	
	private Double costPrice; // 成本价
	private Double sellPrice; // 卖出价
	private Double finalPrice; // 最终价格

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; // 更新时间
	
	private int doingOrderCount;//未完成订单数
	private int doneOrderCount;//完成订单数
	private int allOrderCount;//所有订单数
	
	private Float totalPriceTodayBefore;//今天之前总交易价格
	private Float earnTotalPriceTodayBefore;//今天之前净收入
	private Float totalPriceToday;//今天总交易价格
	private Float earnTotalPriceToday;//今天净收入
	private String todayZero;
	
	private Float totalPrice; //总交易额
	
	private Float earnTotalPrice; //赚的总金额
	
	
}
