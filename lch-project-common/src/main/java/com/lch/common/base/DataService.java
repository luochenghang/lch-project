package com.lch.common.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lch.cache.Cache;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Transactional(readOnly = true)
public class DataService<D extends BaseRepo<T>, T extends DataEntity<T>> extends Cache {

	/** 日志对象 **/
	protected Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 持久层对象
	 */
	@Autowired
	protected D repo;

	/**
	 * 获取单条数据
	 *
	 * @param id
	 * @return
	 */
	protected T get(Long id) {
		return repo.get(id);
	}

	/**
	 * 获取单条数据
	 *
	 * @param entity
	 * @return
	 */
	protected T get(T entity) {
		return repo.get(entity);
	}

	/**
	 * 查询列表数据
	 *
	 * @param entity
	 * @return
	 */
	protected List<T> findList(T entity) {
		return repo.findList(entity);
	}

	/**
	 * 查询列表数据
	 *
	 * @param entity
	 * @return
	 */
	public List<T> findList(Long id) {
		return repo.findList(id);
	}

	/**
	 * 删除旧值，批量替换新值
	 * @param list
	 * @param objId
	 */
	@Transactional(readOnly = false)
	public void replace(List<T> list, Long objId) {
		repo.delete(objId);
		repo.batchInsert(list, objId);
	}

	/**
	 * 删除数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	protected int delete(T entity) {
		return repo.delete(entity);
	}

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	protected int delete(Long id) {
		return repo.delete(id);
	}

	/**
	 * 设置分页对象
	 * @param criteria
	 */
	protected void setPage(Criteria criteria) {
		PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
	}

	/**
	 * 查询分页数据
	 *
	 * @param entity 分页对象
	 * @return
	 */
	protected Page<T> findPage(Criteria criteria) {
		setPage(criteria);
		return PageAdapter.adpater(new PageInfo<T>(repo.findList(criteria)));
	}

	/**
	 * 抛出自定义异常信息
	 *
	 * @param errmsg
	 * @throws ServiceException
	 */
	protected void doThrow(String errmsg) throws ServiceException {
		throw new ServiceException(errmsg);
	}

}
