package com.lch.common.base;

import java.io.Serializable;

import com.lch.component.supcan.annotation.treelist.SupTreeList;

import lombok.Data;

/**
 * 所有实体类的顶级基类
 * @date 2019年6月23日 上午1:38:46
 */
@Data
@SupTreeList
public class DataEntity<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	protected Long id;// id

}
