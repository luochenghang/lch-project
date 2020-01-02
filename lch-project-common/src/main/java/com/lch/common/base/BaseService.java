package com.lch.common.base;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 基础Service
 *
 * @date 2019年12月10日 下午3:23:56
 * @param <D>
 * @param <T>
 */
@Transactional(readOnly = true)
public abstract class BaseService<D extends BaseRepo<T>, T extends BaseEntity<T>> extends DataService<D, T> {

	/**
	 * 查询分页数据
	 *
	 * @param page   分页对象
	 * @param entity
	 * @return
	 */
	protected PageInfo<T> findPage(T entity) {
		PageHelper.startPage(entity.getPageNum(), entity.getPageSize());
		return new PageInfo<T>(findList(entity));
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	protected int save(T entity) {
		if (entity.getIsNewRecord()) {
			entity.setCreateDate(new Date());
			return repo.insert(entity);
		} else {
			return repo.update(entity);
		}
	}

}
