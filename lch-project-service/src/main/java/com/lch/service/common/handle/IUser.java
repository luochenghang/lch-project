package com.lch.service.common.handle;

import java.util.Map;

import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.bo.UlBo;

/**
 * 微信用户处理顶级接口
 *
 * @date 2019年1月5日 上午9:18:07
 */
public interface IUser {

	/**
	 * 用户登录
	 * @param vo
	 * @param src
	 * @param appid
	 * @param appSecret
	 * @return
	 * @throws ServiceException
	 */
	Map<String, Object> login(UlBo vo, String src, String appid, String appSecret) throws ServiceException;


	/**
	 * 用户授权后，解密用户手机号
	 *
	 * @param phone
	 * @return
	 */
	String decipherPhone(String phone, String iv, String code, String appid, String appSecret);

}
