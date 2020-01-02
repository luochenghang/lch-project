package com.lch.entity.common.authority;

import com.lch.common.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class SysRole extends BaseEntity<SysRole> {

	private static final long serialVersionUID = -5988752441910514790L;

	private String name;

	private Short status;

	private String memo;
}