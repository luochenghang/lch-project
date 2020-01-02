package com.lch.entity.supervise;

import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RelatedUser extends DataEntity<RelatedUser>{

	private static final long serialVersionUID = 1L;

	private Long userId; // 用户id

	private Long relatedUserId; // 关联用户id
	
	private Long status; // 0表示未关联 1表示已关联
}
