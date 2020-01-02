
package com.lch.repo.common;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.common.UserBase;

/**
 * 小程序用户repo
 *
 */

@Mapper
public interface UserBaseRepo extends BaseRepo<UserBase> {

	/**
	 * 获取用户基本信息
	 *
	 * @param openid
	 * @param userType
	 * @return
	 */
	@Select("SELECT b.id,b.nick_name AS nickName, b.phone_no AS phoneNo FROM t_user b LEFT JOIN t_user_info u ON u.user_base_id = b.id WHERE u.openid = #{openid} AND u.user_type = #{userType}")
	UserBase getUserBase(@Param("openid") String openid, @Param("userType") int userType);

	/**
	 * 根据unionid获取用户信息
	 *
	 * @param unionid
	 * @return
	 */
	@Select("SELECT id, nick_name AS nickName, phone_no AS phoneNo FROM t_user WHERE unionid = #{unionid} LIMIT 1")
	UserBase getUserBaseByUnionid(String unionid);

	/**
	 * 根据id获取用户信息
	 */
	@Select("SELECT id, nick_name AS nickName, phone_no AS phoneNo FROM t_user WHERE id = #{id} LIMIT 1")
	UserBase get(Long id);

	/**
	 * 获取用户openid
	 *
	 * @param userId
	 * @return
	 */
	@Select("SELECT openid FROM t_user_info WHERE id = #{userId}")
	String getUserOpenid(@Param("userId") Long userId);

	/**
	 * 更新用户手机号
	 *
	 * @param phone
	 * @param userId
	 * @return
	 */
	@Update("UPDATE t_user SET phone_no = #{phone} WHERE id = (SELECT user_base_id FROM t_user_info WHERE id = #{userId})")
	int updateUserPhone(@Param("phone") String phone, @Param("userId") Long userId);

	/**
	 * 修改用户状态
	 *
	 * @param openid
	 * @param status
	 * @param userType
	 * @return
	 */
	@Update("UPDATE t_user_info SET `status` = #{status},update_date=NOW() WHERE openid = #{openid} and user_type = #{userType}")
	int updateUserStatus(@Param("openid") String openid, @Param("status") int status, @Param("userType") int userType);

	/**
	 * @Title: insertForWechat @Description: 新增用户基础信息(公众号) @param @param
	 *         userBase @param @return 设定文件 @return int 返回类型 @throws
	 */
	int insertForWechat(UserBase userBase);

	/**
	 * 获取用户真实姓名和手机号
	 *
	 * @param id
	 * @return
	 */
	@Select("SELECT b.real_name name, b.phone_no phone FROM t_user b LEFT JOIN t_user_info u ON u.user_base_id = b.id WHERE u.id = #{id}")
	Map<String, String> getUserNameAndPhone(Long id);

	/**
	 * 更新用户真实名称
	 *
	 * @param realName
	 * @param userId
	 * @return
	 */
	@Update("UPDATE t_user SET real_name = #{realName}, city_id = #{cityId} WHERE id = (SELECT user_base_id FROM t_user_info WHERE id = #{userId})")
	int updateUserRealName(@Param("realName") String realName, @Param("cityId") int cityId,
			@Param("userId") Long userId);

	@Select("select phone_no phoneNo,unionid,openid,nick_name nickName from t_user where id = #{id}")
	UserBase findUserInfoById(Long id);
	
	/**
	 * 获取用户状态
	 * @param id
	 * @return 用户状态(0正常  1禁用  2删除)
	 */
	@Select("SELECT `status` FROM t_user_info WHERE id =#{id}")
	Integer getUserStatus(Long id);
}
