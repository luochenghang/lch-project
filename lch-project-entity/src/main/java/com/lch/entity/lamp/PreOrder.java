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
public class PreOrder extends DataEntity<PreOrder> {

	private static final long serialVersionUID = 1L;

	
	private Long goodsId; // 商品id

	private Long userId; // 用户id

	private Double sellPrice; //  
	
	private Long  status; // 0未读 1生成订单  2未生成订单
	private Long  isCollect; //1已收藏 0未收藏
	
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; // 更新时间
	
	
	private String title; // 商品名称
	private String coverImg; // 商品封面图
	
	private String goodsTypeName; // 商品类型
	
	private String phone; // 用户电话

	private String userName; // 用户名称
	
//每种数量
	private int unReadPreOrderCount;//未读数量
	private int donePreOrderCount;//已生成订单数量
	private int noDoPreOrderCount;//未生成
	
}
