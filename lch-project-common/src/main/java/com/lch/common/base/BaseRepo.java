package com.lch.common.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 基础mapper封装
 *
 * @ClassName: BaseMapper
 *  @date 2019年12月10日 下午3:23:56
 * @param <T>
 */
public interface BaseRepo<T> {

	/**
	 * 获取单条数据
	 *
	 * @Title: get
	 * @param
	 * @return T
	 */
	T get(Long id);

	T get(T entity);

	T get(@Param("id")Long id, @Param("userId")Long userId);

	/**
	 * 查询部分数据
	 *
	 * @Title: findList
	 * @param
	 * @return List<T>
	 */
	List<T> findList(T entity);

	List<T> findList(Long id);

	List<T> findList(Criteria criteria);

	/**
	 * 查询多个指定ID的数据
	 * @param idList
	 * @return
	 */
	List<T> findList(@Param("idList")List<String> idList);

	/**
	 * 查询所有数据
	 *
	 * @Title: findAllList
	 * @param
	 * @return List<T>
	 */
	List<T> findAllList(T entity);

	/**
	 * 更新数据
	 *
	 * @Title: update
	 * @param
	 * @return int
	 */
	int update(T entity);

	/**
	 * 新增数据
	 *
	 * @Title: insert
	 * @param
	 * @return int
	 */
	int insert(T entity);

	/**
	 * 批量新增
	 * @param list
	 * @return
	 */
	int batchInsert(@Param("list")List<T> list);

	int batchInsert(@Param("list")List<T> list, @Param("objId")Long objId);

	/**
	 * 删除数据
	 *
	 * @Title: delete
	 * @param
	 * @return int
	 */
	int delete(Long id);

	int delete(T entity);

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	int batchDelete(@Param("idList") List<String> idList);

	/**
	 * 获取当前操作数据是否属于当前登录用户
	 * @param id
	 * @param userId
	 * @param dbName
	 * @return
	 */
	@Select("SELECT COUNT(id) FROM ${dbName} WHERE id = #{id} AND create_user_id = #{userId}")
	int getCount(@Param("id")Long id, @Param("userId")Long userId, @Param("dbName")String dbName);


	int insertSelective(T t);

	T selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

}
