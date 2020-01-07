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
public class PayRecord extends DataEntity<PayRecord> {

	private static final long serialVersionUID = 1L;

	private Long goodsId; // 商品id

	private String name; // 用户名称

	private String address; // 用户详细地址

	private Long num; // 购买数量
	
	private Double realPrice; // 真实的交易价格

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date payDate; // 购买时间
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate; // 更新时间
}
