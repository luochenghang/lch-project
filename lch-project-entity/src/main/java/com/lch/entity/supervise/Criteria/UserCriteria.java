package com.lch.entity.supervise.Criteria;

import com.lch.common.base.Criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = true)
public class UserCriteria extends Criteria{

	private static final long serialVersionUID = 1L;
	
	private Long userId;
}
