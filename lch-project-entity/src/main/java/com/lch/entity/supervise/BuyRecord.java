package com.lch.entity.supervise;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BuyRecord extends DataEntity<BuyRecord> {

	private static final long serialVersionUID = 1L;

	private Long userId; // 用户id

	private Long giftId; // 礼物id

	private Long buyNum; // 购买数量

	private Long status;// 0配送中 1已到货 2未到货

	private String title; // 礼物标题

	private String coverImg; // 封面图

	private String content; // 礼物描述内容

	private Long coins; // 购买所需金币

	private Long totalCoins;// 消费金币

	private Long priseNum; // 喜爱数

	private String nickName; // 创建用户昵称

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date arrivalDate;// 到货时间
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;//更新时间
}
