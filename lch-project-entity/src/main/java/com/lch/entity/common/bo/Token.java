package com.lch.entity.common.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典实体类
 */
@Data
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 主键

	private String token;

	private Long userId;

	private Date createDate;



}
