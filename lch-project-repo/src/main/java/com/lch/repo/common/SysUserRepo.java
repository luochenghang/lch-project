package com.lch.repo.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lch.common.base.BaseRepo;
import com.lch.entity.common.authority.SysUser;

@Mapper
public interface SysUserRepo extends BaseRepo<SysUser> {

	/**
	 * @Title: findByUserName @Description: 根据用户名获得用户信息 @param @param
	 *         userName @param @return 设定文件 @return SysUserVo 返回类型 @throws
	 */
	@Select("select id,  user_name userName, pwd,  is_admin isAdmin, status, last_login_time lastLoginTime, create_date createDate, updat_date updatDate from t_sys_user where user_name =#{userName} and `status` != 3")
	SysUser findByUserName(String userName);

	/**
	 * 获取用户详情
	 *
	 * @param id
	 * @return
	 */
	SysUser getSysUser(Long id);

	/**
	 * 获取用户列表
	 *
	 * @param user
	 * @return
	 */
	List<SysUser> findSysUserList(@Param("user") SysUser user);

	/**
	 * 获取相同名称的用户数量
	 *
	 * @param userName
	 * @param userId
	 * @return
	 */
	@Select("select count(1) from t_sys_user where user_name =#{userName} and `status` != 3")
	int getUsernameCount(@Param("userName") String userName);

}