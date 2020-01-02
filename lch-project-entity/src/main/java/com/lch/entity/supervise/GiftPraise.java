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
public class GiftPraise extends DataEntity<GiftPraise> {

	private static final long serialVersionUID = 1L;

	private Long userId; // 用户id

	private Long giftId; // 礼物id

	private Long status; // 0未点赞 1已点赞

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate; // 更新时间
}
