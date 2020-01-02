package com.lch.repo.lamp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lch.common.base.BaseRepo;
import com.lch.entity.common.authority.SysMenu;

@Mapper
public interface SysMenuRepo extends BaseRepo<SysMenu>{

	
	/**
	 * 根据用户查询相应的用户菜单
	 * @param userId
	 * @return
	 */
	List<SysMenu> getMenu(Long userId);
	
	/**
	 * 总管理员 查询全部
	 * @return
	 */
	List<SysMenu> getAllMenu();
	
	
	
}