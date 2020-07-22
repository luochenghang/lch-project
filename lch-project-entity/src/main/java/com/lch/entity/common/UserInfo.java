package com.lch.entity.common;

import com.lch.common.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信用户信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseEntity<UserInfo> {

	private static final long serialVersionUID = 1L;

	//(value = "用户基本信息ID", required = true)
	private Long userBaseId;

	//(value = "用户名", required = true)
	private String userName;

	//(value = "密码", required = true)
	private String pwd;

	//(value = "用户类型", required = true)
	private Integer userType;

	//(value = "状态(0正常 1禁用 2删除)", required = true)
	private Integer status;

	//(example = "微信OpenID", value = "微信OpenID", required = true)
	private String openid;

	//(value = "公众号来源（ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他）", required = false)
	private String subscribeScene;

	private String phone;
	

	private UserBase userBase;
}
