 package com.lch.service.subpervise;

import java.util.Map;

import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.entity.supervise.TaskInfo;
import com.lch.entity.supervise.Criteria.TaskCriteria;

public interface TaskInfoService{

	
	/**
	 * 添加任务(给别人设计以及给自己设计)
	 * 
	 * @param Task
	 * @return
	 */
	int addTask(TaskInfo Task) throws ServiceException;
	
	/**
	 * 修改任务
	 * 
	 * @param Task
	 * @return
	 */
	int updTask(TaskInfo task) throws ServiceException;

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	int delTask(Long id) throws ServiceException;

	/**
	 * 查询所有的任务
	 * @param criteria
	 * @return
	 */
	Page<TaskInfo> getAllTask(TaskCriteria criteria);
	
	/**
	 * 查询他的所有的任务
	 * @param criteria
	 * @return
	 */
	Page<TaskInfo> getTAAllTask(TaskCriteria criteria);
	
	/**
	 * 获取我创建的任务
	 * @param criteria
	 * @return
	 */
	Page<TaskInfo> getTaskCreateByMe(TaskCriteria criteria);

	/**
	 * 审核任务
	 * 
	 * @param id
	 * @param auditStatus 0审核中, 1 审核成功 2审核失败
	 * @param reason      失败原因
	 * @return
	 */
	int auditTask(Long id, Long auditStatus, String reason) throws ServiceException;
	
	/**
	 * 任务完成审核
	 * @param id
	 * @param status
	 * @param userId
	 * @return
	 */
	int auditTaskDone(Long id, Long status) throws ServiceException;

	/**
	 * 根据id查询任务详情
	 * 
	 * @param id
	 * @return
	 */
	TaskInfo getTaskById(Long id);
	
	/**
	 * 获取任务和礼物需要操作的数量(未审核 未完成)
	 * @param userId
	 * @return
	 */
	Map<String, Object> getTaskGiftCount();

}
