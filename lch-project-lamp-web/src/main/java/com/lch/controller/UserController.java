package com.lch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.config.UserUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.authority.SysUser;
import com.lch.service.lamp.SysMenuService;
import com.lch.service.lamp.SysUserRoleService;
import com.lch.utils.MD5Util;
import com.lch.utils.StringUtils;

@RestController
@RequestMapping("/lamp/v1")

public class UserController extends BaseController {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysMenuService menuService;

	// 登录接口
	@PostMapping("/login")
	@AuthIgnore(login = false)
	public AjaxResponse login(String userName, String pwd) {

		if (StringUtils.isBlank(userName))
			return fail("用户不得为空");

		if (StringUtils.isBlank(pwd))
			return fail("密码不得为空");

		SysUser sysUser = sysUserRoleService.findByUserName(userName);
		if (sysUser == null)
			return fail("用户不存在");

		if (sysUser.getStatus() != 1)
			return fail("该账号可能未激活或被禁用或被删除,请联系管理员!");

		// 验证密码是否正确
		if (!MD5Util.validPassword(pwd, sysUser.getPwd()))
			return fail("密码错误");

		// 验证通过，则同步到redis缓存
		String token = UserUtils.login(sysUser.getId());
		return succees(token);
	}

	@AuthIgnore(login = false)
	@PostMapping("/reg")
	public AjaxResponse register(SysUser user) throws ServiceException {
		sysUserRoleService.register(user);
		return succees();
	}

	@PostMapping("/logout")
	public AjaxResponse loginout(HttpServletRequest request) {
		UserUtils.logout(request.getHeader("token"));
		return succees();
	}

	/**
	 * 获取菜单信息
	 * 
	 * @return
	 */
	@GetMapping("/getMenu")
	public AjaxResponse getMenu() {
		return succees(menuService.getMenu());
	}

}
