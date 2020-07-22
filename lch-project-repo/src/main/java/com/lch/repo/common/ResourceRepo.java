package com.lch.repo.common;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.lch.entity.common.FileResource;

/**
 * 数据字典的Repo
 */

@Mapper
public interface ResourceRepo {
	
	/**
	 * 添加文件资源
	 * 
	 * @param r
	 * @return
	 */
	int addFileResource(FileResource r);
	
	/**
	 * 查询所有的资源，根据类型分类
	 * @return
	 */
	List<FileResource> getAllFileResourceGroupByType();
	
	/**
	 * 根据type查询 如果为null 查询全部
	 * @param type
	 * @return
	 */
	List<FileResource> getAllFileResource(@Param("status")Long status, @Param("type") Long type, @Param("title")String title);
	
	/**
	 * 启用 禁用
	 * @return
	 */
	@Update("update t_file_resource set status = #{status} where id = #{id}")
	Integer updStatus(@Param("id")Long id, @Param("status")Long status);
	
	Integer batchDelete(@Param("idList") List<String> idList);
	
	/**
	 * 根据业务对象id和类型删除所有的资源，适用于删除产品时顺带将资源也一起删除
	 * @return
	 */
	@Delete("delete from t_file_resource where objId = #{objId} and type = #{type}")
	Integer delFilesByObjIdAndType(@Param("objId")Long objId, @Param("type")Long type);

	List<FileResource> getBanner();
}
