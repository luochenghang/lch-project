package com.lch.service.lamp.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.DataService;
import com.lch.common.config.UserUtils;
import com.lch.entity.common.authority.SysMenu;
import com.lch.entity.common.authority.SysUser;
import com.lch.repo.common.SysUserRepo;
import com.lch.repo.lamp.SysMenuRepo;
import com.lch.service.lamp.SysMenuService;

@Service
@Transactional(readOnly = true)
public class SysMenuServiceImpl extends DataService<SysMenuRepo,SysMenu> implements SysMenuService {

	@Autowired
	private SysUserRepo userRepo;
	
	@Override
	public List<SysMenu> getMenu() {
		
		Long userId = UserUtils.getCurrentUserId();
		SysUser user = userRepo.getSysUser(userId);
		if(user != null) {
			if(user.getIsAdmin() == 0) {
				return	repo.getMenu(userId);
			}else {
				return	repo.getAllMenu();
			}
		}
		return null;
	}


}
