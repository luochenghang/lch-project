package com.lch.repo.supervise;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.supervise.TaskInfo;
import com.lch.entity.supervise.UserSimpleInfo;
import com.lch.entity.supervise.Criteria.TaskCriteria;

@Mapper
public interface TaskInfoRepo extends BaseRepo<TaskInfo> {

	/**
	 * 添加任务(给别人设计以及给自己设计)
	 * 
	 * @param Task
	 * @return
	 */
	int addTask(TaskInfo Task);

	/**
	 * 修改任务
	 * 
	 * @param Task
	 * @return
	 */
	int updTask(TaskInfo task);

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	@Delete("delete from t_task where id = #{id} and createUser = #{userId}")
	int delTask(Long id, Long userId);

	/**
	 * 查询我所有的任务
	 * 
	 * @return
	 */
	List<TaskInfo> getAllTask(TaskCriteria criteria);

	/**
	 * 查询他创建所有任务
	 * 
	 * @param criteria
	 * @return
	 */
	List<TaskInfo> getTAAllTask(TaskCriteria criteria);

	/**
	 * 获取我创建的任务
	 * 
	 * @param criteria
	 * @return
	 */
	List<TaskInfo> getTaskCreateByMe(TaskCriteria criteria);

	/**
	 * 审核任务
	 * 
	 * @param id
	 * @param auditStatus 0审核中, 1 审核成功 2审核失败
	 * @param reason      失败原因
	 * @return
	 */
	int auditTask(Long id, Long auditStatus, String reason, Long userId);

	/**
	 * 任务完成审核
	 * 
	 * @param id
	 * @param status
	 * @param userId
	 * @return
	 */
	int auditTaskDone(Long id, Long status, Long userId);

	/**
	 * 根据id查询任务详情
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT id, userId, `level`,case when `level`=0 then '一般' when `level` = 1 then '紧急' else '加急' end  as 'levelStr', endDate, rewardCoins, punishCoins, auditStatus, reason, title, content, `STATUS`, coverImg, isForMe, createUser, createDate FROM t_task t WHERE	t.id = #{id}")
	TaskInfo getTaskById(Long id);

	/**
	 * 获取任务和礼物需要操作的数量(未审核 未完成)
	 * 
	 * @param userId,relatedId
	 * @return
	 */
	Map<String, Object> getTaskGiftCount(Long userId, Long relatedId);

	/**************************************************************************************************************/
	// 这里是用户信息相关操作
	/**
	 * 更新用户信息,主要有段位以及金币
	 * 
	 * @param userId
	 * @return
	 */
	@Update("update t_user_simple_info set totalCoins= totalCoins + #{coins},victoryPoint=#{victoryPoint} ,levelId=#{levelId} where userId=#{userId}")
	int updUserSimpleInfo(Long userId, Long coins, Long victoryPoint, Long levelId);

	/**
	 * 获取用户段位金币信息
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select u.id,u.userId,u.totalCoins,u.victoryPoint,u.levelId,l.minPoint,IFNULL(l.maxPoint,l.minPoint) as maxPoint from t_user_simple_info u join t_user_level l on u.levelId = l.id where u.userId=#{userId}")
	UserSimpleInfo getUserSimpleInfo(Long userId);

	/**
	 * 获取段位信息
	 * @param id
	 * @return
	 */
	@Select("select minPoint, IFNULL(maxPoint,minPoint) as maxPoint from t_user_level where id = #{id}")
	UserSimpleInfo getLevel(Long id);
}
