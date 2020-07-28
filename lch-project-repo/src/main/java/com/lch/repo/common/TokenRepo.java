package com.lch.repo.common;

import com.lch.entity.common.Dict;
import com.lch.entity.common.bo.Token;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据字典的Repo
 */

@Mapper
public interface TokenRepo {


	/**
	 * 根据token查询，有结果则存在 反之不存在
	 * @param token
	 * @return
	 */
	@Select("SELECT id, token, userId, createDate FROM t_sys_token WHERE token = #{token} " +
			"and TIME_TO_SEC(TIMEDIFF(now(), createDate)) < 8 * 3600 limit 1")
	Token findUserByToken(String token);
	
	/**
	 * 插入token
	 * @return
	 */
	@Select("insert into t_sys_token(token,userId,createDate) values(#{token},#{userId},now())")
	Integer insertToken(String token, Long userId);

	/**
	 * 删除token
	 * @param token
	 * @return
	 */
	@Delete("delete from t_sys_token where token = #{token}")
	Integer delToken(String token);
}
