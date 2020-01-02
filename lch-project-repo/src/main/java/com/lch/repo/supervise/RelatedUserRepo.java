package com.lch.repo.supervise;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.common.UserBase;
import com.lch.entity.supervise.RelatedUser;
import com.lch.entity.supervise.UserSimpleBase;
import com.lch.entity.supervise.Criteria.UserCriteria;

@Mapper
public interface RelatedUserRepo extends BaseRepo<UserBase> {

	/**
	 * 建立互相监督关联关系
	 * 
	 * @param relatedUser
	 * @return
	 */
	int addFriend(RelatedUser relatedUser);

	/**
	 * 查找用户 关键字查询
	 * 
	 * @param criteria
	 * @return
	 */
	List<UserSimpleBase> findFriend(UserCriteria criteria);

	/**
	 * 获取自己的信息
	 * 
	 * @param userId
	 * @return
	 */
	UserSimpleBase getMyInfo(Long userId);

	/**
	 * 是否同意关联关系
	 * 
	 * @param status 0表示未关联 1表示已关联 2拒绝关联
	 * @return
	 */
	@Update("update t_related_user t  set t.`status`=#{status} where t.relatedUserId=#{userId} and t.id = #{id}")
	int agreeApply(Long userId, Long status,Long id);

	/**
	 * 删除关联的人
	 * 
	 * @param userId
	 * @return
	 */
	@Delete("delete from t_related_user where (userId = #{userId} or relatedUserId = #{userId}) and id= #{id} ")
	int delRelatedUser(Long userId, Long id);

	/**
	 * 获取有关联人的信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserSimpleBase> getRelatedUser(Long userId, Long status);

	/**
	 * 初始化用户信息 金币为0 胜点为0 计算段位(完成20输了10)
	 * 
	 * @param userId
	 * @return
	 */
	@Insert("insert into t_user_simple_info(userId) select #{userId} from dual where NOT EXISTS(SELECT id FROM t_user_simple_info WHERE userId = #{userId}) ")
	int addUserSimpleInfo(Long userId);

	/**
	 * 完成或者失败任务的时候进行更新 金币和段位胜点
	 * 
	 * @param userId
	 * @param totalCoins
	 * @param victoryPoint
	 * @return
	 */
	@Update("update t_user_simple_info set totalCoins = totalCoins + #{totalCoins} , victoryPoint = victoryPoint + #{victoryPoint} where userId = #{userId}")
	int updateSimpleInfo(Long userId, Long totalCoins, Long victoryPoint);

	/**
	 * 判断是否存在好友
	 * @param userId
	 * @param relatedUserId
	 * @return
	 */
	@Select("select count(1) from t_related_user where userId=#{userId} and relatedUserId=#{relatedUserId} and status!=2")
	int isExistsFriend(Long userId,Long relatedUserId);
	
	/**
	 * 获取好友申请列表
	 * @param userId
	 * @param status
	 * @return
	 */
	List<UserSimpleBase> getRelatedApply(Long userId, Long status);
}
