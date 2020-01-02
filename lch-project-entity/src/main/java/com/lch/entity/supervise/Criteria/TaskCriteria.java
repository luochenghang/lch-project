package com.lch.entity.supervise.Criteria;

import com.lch.common.base.Criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = true)
public class TaskCriteria extends Criteria{

	private static final long serialVersionUID = 1L;
	
	private Long userId; //用户id

	private Long status; //0任务进行中 1任务完成 2任务失败
	
	private Long auditStatus; //0 未审核 1审核通过 2审核失败
	
	private Long createUser; //创建用户id

}
