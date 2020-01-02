package com.lch.entity.supervise;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, of = { "coins", "content", "coverImg", "createUser", "isForMe", "title",
		"userId" })
public class GiftInfo extends DataEntity<GiftInfo> {

	private static final long serialVersionUID = 1L;

	private Long userId; // 用户id

	private String title; // 礼物标题

	private Long isTapPraise;// 是否点赞 1已点 0没有点

	private String coverImg; // 封面图

	private String content; // 礼物描述内容

	private Long coins; // 购买所需金币

	private Long priseNum; // 喜爱数

	private Long auditStatus; // 0 未审核 1审核通过 2审核失败

	private String reason; // 审核失败原因

	private Long isForMe; // 是否是为自己创建 0不是 1是(为自己创建的需要审核)

	private Long createUser; // 创建用户

	private String nickName; // 创建用户昵称

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate; // 创建时间
}
