package com.lch.repo.common;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.common.UserInfo;

/**
 * 微信用户相关的repo
 *
 */

@Mapper
public interface UserInfoRepo extends BaseRepo<UserInfo> {

	/**
	 * 根据微信openid获取用户信息
	 *
	 * @param openid
	 * @return
	 */
	UserInfo get(@Param("openid") String openid, @Param("userType") int userType);

	/**
	 * 根据openid获取用户id
	 * @param openid
	 * @return
	 */
	@Select("SELECT id FROM t_user_info WHERE openid = #{openid}")
	Long getUserId(String openid);

	/**
	 * 获取用户基本信息id
	 *
	 * @param id
	 * @return
	 */
	@Select("SELECT user_base_id FROM t_user_info WHERE id = #{id}")
	Long getUserBaseId(Long id);

	/**
	 * 新增用户信息(奔跑宝公众号)
	 *
	 * @param userInfo
	 * @return
	 */
	@Insert("INSERT INTO t_user_info(user_base_id,user_type,status,openid,subscribe_scene,create_date) VALUES(#{userBaseId},#{userType},#{status},#{openid},#{subscribeScene},#{createDate})")
	int insertForWechat(UserInfo userInfo);

	/**
	 * 更新用户登录次数
	 *
	 * @param id
	 * @return
	 */
	@Update("UPDATE t_user_info SET login_num = login_num + 1 WHERE id = #{id}")
	int updateLoginNum(Long id);

	/**
	 * 根据用户的unionid获取用户的基本信息id
	 *
	 * @param unionid 用户的unionid
	 * @return
	 */
	@Select("SELECT id FROM t_user WHERE unionid=#{unionid} LIMIT 1")
	Long checkBaseId(String unionid);

}
