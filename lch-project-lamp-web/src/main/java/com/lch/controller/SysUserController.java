package com.lch.controller;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lch.cache.redis.RedisUtil;
import com.lch.common.base.BaseController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.config.UserUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.common.authority.SysUser;
import com.lch.service.common.SysUserService;
import com.lch.utils.CodeUtil;
import com.lch.utils.MD5Util;
import com.lch.utils.StringUtils;

@RestController
@RequestMapping("/lamp/v1")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private RedisUtil redisUtil;
	

	// 登录接口
	@PostMapping("/login")
	@AuthIgnore(login = false)
	public AjaxResponse login(HttpServletRequest req, String userName, String pwd,String code) throws ServiceException {

		if (StringUtils.isBlank(userName))
			return fail("用户不得为空");

		if (StringUtils.isBlank(pwd))
			return fail("密码不得为空");

		SysUser sysUser = sysUserService.findByUserName(userName);
		if (sysUser == null)
			return fail("用户不存在");

		if (sysUser.getStatus() != 1)
			return fail("该账号可能被禁用或被删除,请联系管理员!");

		// 验证密码是否正确
		if (!MD5Util.validPassword(pwd, sysUser.getPwd()))
			return fail("密码错误");

//		String ip  = CodeUtil.getIpAddr(req);
//		if(redisUtil.get(ip + "code") == null || !redisUtil.get(ip + "code").toString().toLowerCase().equals(code.toLowerCase())) {
//			return fail("验证码错误或者失效！");
//		}
		//更新登陆次数和时间
		sysUserService.updLoginNum(sysUser.getId());
		// 验证通过，则同步到redis缓存
		String token = UserUtils.login(sysUser.getId());
		return succees(token);
	}

	@AuthIgnore(login = false)
	@PostMapping("/reg")
	public AjaxResponse register(SysUser user) throws ServiceException {
		return succees(sysUserService.register(user));
	}

	@PostMapping("/logout")
	public AjaxResponse loginout(HttpServletRequest request) {
		UserUtils.logout(request.getHeader("token"));
		return succees();
	}

	/**
	 * 修改密码
	 * @return
	 */
	@PostMapping("/updPassword")
	public AjaxResponse updPassword(String oldPwd, String newPwd) throws ServiceException{
		return succees(sysUserService.updateSysUser(oldPwd, newPwd));
	}
	
	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	@AuthIgnore(login = false)
	@GetMapping("/findSysUserList")
	public AjaxResponse findSysUserList() {
		return succees(sysUserService.findPageUser());
	}
	
	//@AuthIgnore(login = false)
	@GetMapping("/delSysUser")
	public AjaxResponse delSysUser(@RequestParam(value = "ids") List<String> ids) throws ServiceException{
		if (ids.contains("1")) {
			return fail("管理员账号不能删除");
		}
		
		return succees(sysUserService.deleteSysUser(ids));
	}
	
	/**
	 * 更新用户状态列表
	 * 
	 * @return
	 * @throws ServiceException 
	 */
	//@AuthIgnore(login = false)
	@PostMapping("/updateSysUserStatus")
	public AjaxResponse updateSysUserStatus(Long id, Long status) throws ServiceException {
		if(status != 1L && status != 2L) {
			return fail("参数status不正确！");
		}
		return succees(sysUserService.updateSysUserStatus(id, status));
	}
	
	@GetMapping("/getLoginUserInfo")
	public AjaxResponse getLoginUserInfo() throws ServiceException {
		Long uid = UserUtils.getCurrentUserId();
		if(uid != null) {
			return succees(sysUserService.getSysUser(uid));
		}
		return fail();
	}
	
	@PostMapping("/updateLoginUserInfo")
	public AjaxResponse updateLoginUserInfo(SysUser sysUser) throws ServiceException {
		Long uid = UserUtils.getCurrentUserId();
		
		if(uid != null) {
			sysUser.setId(uid);
			return succees(sysUserService.updateSysUser(sysUser));
		}
		return fail();
	}
	
	
	/*************************************************************/
	//生成验证码
//	@AuthIgnore(login = false)
//	@GetMapping("/getCode")
//	public void getCode(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
//		 // 调用工具类生成的验证码和验证码图片
//        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();
//
//        // 将四位数字的验证码保存到Session中。
//       // HttpSession session = req.getSession();
//       // session.setAttribute("code", codeMap.get("code").toString());
//        String ip  = CodeUtil.getIpAddr(req);
//        redisUtil.del(ip + "code");
//        redisUtil.set(ip + "code", codeMap.get("code").toString(), 60L);//过期时间60秒
//
//        // 禁止图像缓存。
//        resp.setHeader("Pragma", "no-cache");
//        resp.setHeader("Cache-Control", "no-cache");
//        resp.setDateHeader("Expires", -1);
//
//        resp.setContentType("image/jpeg");
//
//        // 将图像输出到Servlet输出流中。
//        ServletOutputStream sos;
//        try {
//            sos = resp.getOutputStream();
//            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
//            sos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
	

}
