package com.lch.service.common.handle;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.lch.common.base.BaseService;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.wechat.wxprogram.WeChatSession;
import com.lch.component.wechat.wxprogram.WeChatUtils;
import com.lch.component.wechat.wxprogram.WxNumAesUtil;
import com.lch.entity.common.UserBase;
import com.lch.entity.common.UserInfo;
import com.lch.entity.common.bo.UlBo;
import com.lch.repo.common.UserBaseRepo;
import com.lch.repo.common.UserInfoRepo;
import com.lch.utils.JsonUtils;
import com.lch.utils.MapUtils;
import com.lch.utils.StringUtils;

@Service
@Transactional(readOnly = false)
public abstract class UserHandle extends BaseService<UserBaseRepo, UserBase> implements IUser {

	@Autowired
	private UserInfoRepo userInfoRepo;

	@Autowired
	private TokenService tokenService;


	/**
	 * 封装返回值
	 * @param map
	 */
	public abstract void setResultMap(Map<String, Object> map);

	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> login(UlBo vo, String src, String appid, String appSecret) throws ServiceException{
		//保存用户信息
		UserInfo user = this.save(vo, src, appid, appSecret);
		Long uid = user.getId();
		Integer type = repo.getUserStatus(uid);
		if(type != null && type == 1) {
			doThrow("当前账号已被禁用，请联系客服处理");
		}
		//用户登录
		String token = tokenService.login(uid);
		//封装返回值
		Map<String, Object> map = Maps.newHashMap();
		map.put("token", token);
		map.put("phone", user.getPhone());
		//设置其它返回值
		this.setResultMap(map);
		return map;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false)
	public String decipherPhone(String phone, String iv, String code, String appid, String appSecret) {
		WeChatSession chat = WeChatUtils.login(code, appid, appSecret);
		if (chat != null) {
			String key = chat.getSession_key();
			String info = WxNumAesUtil.wxDecrypt(phone, key, iv);
			Map<String, String> map = JsonUtils.getInstance().fromJson(info, Map.class);
			try {
				phone = map.get("phoneNumber");
				if (StringUtils.isNotBlank(phone)) {
					repo.updateUserPhone(phone, TokenServiceImpl.getCurrentUserId());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return phone;
			}
		}
		return phone;
	}

	/**
	 * 保存用户信息
	 * @param src
	 * @param appid
	 * @param appSecret
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public UserInfo save(UlBo bo, String src, String appid, String appSecret) throws ServiceException {
		// 解析用户openid
		String openid = this.decipherOpenid(bo, appid, appSecret);
		// 判断用户信息是否存在
		int userType = bo.getUserType();
		UserInfo userInfo = userInfoRepo.get(openid, userType);
		UserBase user = new UserBase();
		if (userInfo == null) {
			//解析用户unionid 没有绑定开放平台 解析不出来
			/*
			String unionid = bo.getUnionid();
			unionid = StringUtils.isBlank(unionid) ? this.decipherUnionid(bo.getEncryptedData(), bo.getSessionKey(), bo.getIv()) : unionid;
			*/
			//这里使用构造唯一的id
			String unionid = userType + "_" + openid; 
			
			// 判断用户基础信息是否存在
			user = repo.getUserBaseByUnionid(unionid);
			// 若不存在则新增
			if (user == null) {
				// 新增基本用户信息
				user = new UserBase();
				user.setUnionid(unionid);
				user.setNickName(bo.getNickName());
				user.setPortrait(bo.getPortrait());
				user.setSex(bo.getSex());
				user.setCreateDate(new Date());
			}else {
				// 若存在，则更新基本信息
				user.setNickName(bo.getNickName());
				user.setPortrait(bo.getPortrait());
				user.setSex(bo.getSex());
			}
			save(user);

			// 新增用户信息
			userInfo = new UserInfo();
			userInfo.setUserBaseId(user.getId());
			userInfo.setOpenid(openid);
			userInfo.setUserType(userType);
			userInfoRepo.insert(userInfo);
		}else {
			// 若存在，则更新基本信息
			user.setId(userInfo.getUserBaseId());
			user.setNickName(bo.getNickName());
			user.setPortrait(bo.getPortrait());
			user.setSex(bo.getSex());
			repo.update(user);
		}

		// 更新用户登录次数
		userInfoRepo.updateLoginNum(userInfo.getId());
		return userInfo;
	}

	/**
	 * 解析微信用户数据
	 * @param bo
	 * @param appid
	 * @param appSecret
	 * @throws ServiceException
	 */
	protected String decipherOpenid(UlBo bo, String appid, String appSecret) throws ServiceException {
		WeChatSession chat = WeChatUtils.login(bo.getCode(), appid, appSecret);
		String openid = null;
		if (chat == null || StringUtils.isBlank(openid = chat.getOpenid())) {
			log.error("小程序登录获取openid失败，微信登录接口返回参数为：{}", chat.toString());
			doThrow("小程序登录失败，请稍后重试");
		}
		bo.setOpenid(openid);
		bo.setUnionid(chat.getUnionid());
		bo.setSessionKey(chat.getSession_key());
		return openid;
	}

	/**
	 * 解析用户unionid
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	protected String decipherUnionid(String encrypt, String sessionKey, String iv) throws ServiceException {
		String result = WxNumAesUtil.wxDecrypt(encrypt, sessionKey, iv);
		Map<String, String> map = JsonUtils.getInstance().fromJson(result, Map.class);
		String unionid = MapUtils.getString(map, "unionId");
		if(StringUtils.isBlank(unionid)) {
			log.error("小程序登录解析unionid失败，解密字符串为：{}", result);
			doThrow("小程序登录失败，请稍后重试");
		}
		return unionid;
	}

}
