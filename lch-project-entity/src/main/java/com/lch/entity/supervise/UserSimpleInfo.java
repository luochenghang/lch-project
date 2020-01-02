package com.lch.entity.supervise;

import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserSimpleInfo extends DataEntity<UserSimpleInfo>{

	private static final long serialVersionUID = 1L;
	
	private Long totalCoins;// 金币总数

	private Long victoryPoint;// 胜点 计算段位(完成20输了10)

	private Long levelId;//段位id
	
	private Long minPoint; // 段位最小值
	
	private Long maxPoint; // 段位最大值
}
