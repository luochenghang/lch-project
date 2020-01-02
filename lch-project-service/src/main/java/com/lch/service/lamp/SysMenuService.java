package com.lch.service.lamp;

import java.util.List;

import com.lch.entity.common.authority.SysMenu;

public interface SysMenuService {

	/**
	 * 根据用户查询相应的用户菜单
	 * 
	 * @param userId
	 * @return
	 */
	List<SysMenu> getMenu();
}