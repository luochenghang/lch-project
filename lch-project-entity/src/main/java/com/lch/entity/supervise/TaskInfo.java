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
public class TaskInfo extends DataEntity<TaskInfo> {

	private static final long serialVersionUID = 1L;

	private Long userId; // 用户id

	private Long level; // 0 一般 1紧急 2 加急

	private String levelStr; // 程度描述

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate; // 结束时间

	private Long rewardCoins; // 完成任务获得金币数

	private Long punishCoins; // 失败惩罚扣除金币数

	private String title; // 任务标题

	private String content; // 任务描述内容

	private Long status; // 0任务未开始 1任务进行中 2任务失败 3任务完成

	private String coverImg; // 封面图

	private Long createUser; // 创建用户id

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate; // 创建时间

	private Long auditStatus; // 审核时间

	private String reason; // 审核失败原因

	private Long isForMe; // 是否是为自己创建 0不是 1是(为自己创建的需要审核)

	private String nickName;// 昵称
}
