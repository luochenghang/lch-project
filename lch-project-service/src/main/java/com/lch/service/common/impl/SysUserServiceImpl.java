package com.lch.service.common.impl;

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
	public List<SysUser> findPageUser() {
		return repo.findSysUserList();
	}

	@Override
	public SysUser getSysUser(Long id) {
		return repo.getSysUser(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int register(SysUser user) throws ServiceException {
		user = this.checkParameter(user);
		return repo.insert(user);
	}

	@Override
	@Transactional(readOnly = false)
	public int deleteSysUser(List<String> ids) throws ServiceException {
		return repo.batchDelete(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int updateSysUserStatus(Long id, Long status) throws ServiceException {
		return repo.updateSysUserStatus(id, status);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int updateSysUser(SysUser user) throws ServiceException {
		SysUser sys = repo.getSysUser(user.getId());
		if(sys == null) {
			doThrow("用户不存在");
		}
		if (repo.getUsernameCount(user.getUserName()) > 0 && !sys.getUserName().equals(user.getUserName()))
			doThrow("用户名已存在");
		
		if (repo.getEmailCount(user.getEmail()) > 0 && !sys.getEmail().equals(user.getEmail()))
			doThrow("邮箱已存在");
		return repo.update(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int updateSysUser(String oldPwd, String newPwd) throws ServiceException {
		
		Long uid = UserUtils.getCurrentUserId();
		SysUser user = repo.getSysUser(uid);
		if(user == null) {
			doThrow("用户不存在");
		}
		
		if (StringUtils.isNotBlank(oldPwd)) {
			// 如果密码不为空，则表示用户修改了密码，需要验证密码，为其加密
			if (!StringUtils.isAppointLength(oldPwd, 6, 16))
				doThrow("原密码格式错误");
			// 验证密码是否正确
			if (!MD5Util.validPassword(oldPwd, user.getPwd()))
				doThrow("原密码错误");
			if (!StringUtils.isAppointLength(newPwd, 6, 16))
				doThrow("新密码格式错误");
			// 密码加密
			newPwd = MD5Util.getEncryptedPwd(newPwd);
			return repo.updatePwd(uid, newPwd);
		}else {
			doThrow("新密码为空");
		}
		return 0;
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
		if (repo.getEmailCount(user.getEmail()) > 0)
			doThrow("邮箱已存在");
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
