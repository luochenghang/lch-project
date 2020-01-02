package com.lch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.DataController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.supervise.TaskInfo;
import com.lch.entity.supervise.Criteria.TaskCriteria;
import com.lch.service.subpervise.TaskInfoService;
import com.lch.utils.StringUtils;

@RestController
@RequestMapping("/api")
public class TaskInfoController extends DataController<TaskInfoService> {

	/**
	 * 添加任务
	 * 
	 * @param task
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/addTask")
	public AjaxResponse addTask(TaskInfo task) throws ServiceException {
		return succees(service.addTask(task));
	}

	/**
	 * 修改任务
	 * 
	 * @param task
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/updTask")
	public AjaxResponse updTask(TaskInfo task) throws ServiceException {
		return succees(service.updTask(task));
	}

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/v1/delTask")
	public AjaxResponse delTask(Long id) throws ServiceException {
		return succees(service.delTask(id));
	}

	/**
	 * 获取任务
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping("/v1/getAllTask")
	public AjaxResponse getAllTask(TaskCriteria criteria) {
		return succees(service.getAllTask(criteria));
	}
	
	/**
	 * 获取任务
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping("/v1/getTaskCreateByMe")
	public AjaxResponse getTaskCreateByMe(TaskCriteria criteria) {
		return succees(service.getTaskCreateByMe(criteria));
	}

	/**
	 * 获取任务
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping("/v1/getTAAllTask")
	public AjaxResponse getTAAllTask(TaskCriteria criteria) {
		return succees(service.getTAAllTask(criteria));
	}

	/**
	 * 审核任务
	 * 
	 * @param id
	 * @param auditStatus
	 * @param reason
	 * @return
	 */
	@GetMapping("/v1/auditTask")
	public AjaxResponse auditTask(Long id, Long auditStatus, String reason) throws ServiceException {

		if (auditStatus == 2L && StringUtils.isBlank(reason)) {
			return fail("审核失败的原因不能为空!");
		}

		if (!StringUtils.isAppointParam(auditStatus, 1L, 2L)) {
			return fail("参数auditStatus错误!(1审核通过 2审核失败)");
		}

		if (auditStatus == 1L) {
			reason = "";
		}
		return succees(service.auditTask(id, auditStatus, reason));
	}

	/**
	 * 审核任务
	 * 
	 * @param id
	 * @param auditStatus
	 * @param reason
	 * @return
	 */
	@GetMapping("/v1/auditTaskDone")
	public AjaxResponse auditTaskDone(Long id, Long status) throws ServiceException {

		if (!StringUtils.isAppointParam(status, 1L, 2L)) {
			return fail("参数status错误!(1审核通过 2审核失败)");
		}
		return succees(service.auditTaskDone(id, status));
	}

	/**
	 * 根据id 查询任务
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getTaskById")
	public AjaxResponse getTaskById(Long id) {
		return succees(service.getTaskById(id));
	}
	
	/**
	 * 根据id 查询任务
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getTaskGiftCount")
	public AjaxResponse getTaskGiftCount() {
		return succees(service.getTaskGiftCount());
	}
}
