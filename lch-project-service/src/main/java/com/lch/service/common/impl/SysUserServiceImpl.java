package com.lch.service.common.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.DataService;
import com.lch.common.config.UserUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.authority.SysUser;
import com.lch.repo.common.SysUserRepo;
import com.lch.service.common.SysUserService;
import com.lch.utils.MD5Util;
import com.lch.utils.RegexUtils;
import com.lch.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public abstract class SysUserServiceImpl extends DataService<SysUserRepo, SysUser> implements SysUserService {

	@Override
	public SysUser findByUserName(String userName) {
		return repo.findByUserName(userName);
	}

	@Override
	public List<SysUser> findPageUser(SysUser user) {
		return repo.findSysUserList(user);
	}

	@Override
	public SysUser getSysUser(Long id) {
		return repo.getSysUser(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void register(SysUser user) throws ServiceException {
		user = this.checkParameter(user);
		user.setStatus((short) 2);
		user.setIsAdmin((short) 0);
		repo.insert(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteSysUser(String ids) throws ServiceException {
		Long userId = UserUtils.getCurrentUserId();
		if (repo.getSysUser(userId) == null || repo.getSysUser(userId).getIsAdmin() == (short) 0) {
			doThrow("你不是管理员,没有操作权限");
		}

		if (StringUtils.isNotBlank(ids)) {
			// 管理员账号不能删除
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			if (idList.contains("1")) {
				doThrow("管理员账号不能删除");
			}
			repo.batchDelete(idList);
		}
	}

	/**
	 * 参数校验
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	private SysUser checkParameter(SysUser user) throws ServiceException {
		if (user == null)
			doThrow("用户信息不能为空");
		// 用户名校验
		String userName = user.getUserName();
		if (StringUtils.isBlank(userName))
			doThrow("用户名不能为空");
		if (!RegexUtils.match(userName, "^(?![0-9]+$)[0-9A-Za-z]{4,16}$"))
			doThrow("用户名格式错误");
		if (repo.getUsernameCount(userName) > 0)
			doThrow("用户名已存在");
		// 密码校验
		String password = user.getPwd();
		if (StringUtils.isBlank(password))
			doThrow("密码不能为空");
		if (StringUtils.isNotBlank(password)) {
			// 如果密码不为空，则表示用户修改了密码，需要验证密码，为其加密
			if (!StringUtils.isAppointLength(password, 6, 16))
				doThrow("密码格式错误");
			// 密码加密
			user.setPwd(MD5Util.getEncryptedPwd(password));
		}
		return user;
	}

}
