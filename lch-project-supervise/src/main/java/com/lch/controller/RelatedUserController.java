package com.lch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.DataController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.supervise.RelatedUser;
import com.lch.entity.supervise.Criteria.UserCriteria;
import com.lch.service.subpervise.RelatedUserService;
import com.lch.utils.StringUtils;

@RestController
@RequestMapping("/api")
public class RelatedUserController extends DataController<RelatedUserService> {

	/**
	 * 添加监督好友
	 * 
	 * @param gift
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/addFriend")
	public AjaxResponse addFriend(RelatedUser relatedUser) throws ServiceException {
		return succees(service.addFriend(relatedUser));
	}

	/**
	 * 查询好友
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping("/v1/findFriend")
	public AjaxResponse findFriend(UserCriteria criteria) {
		return succees(service.findFriend(criteria));
	}

	/**
	 * 是否同意请求
	 * 
	 * @param id
	 * @param auditStatus
	 * @param reason
	 * @return
	 */
	@GetMapping("/v1/agreeApply")
	public AjaxResponse agreeApply(Long status, Long id) throws ServiceException {
		if (!StringUtils.isAppointParam(status, 1L, 2L)) {
			return fail("参数status错误!(1表示关联 2拒绝关联)");
		}
		return succees(service.agreeApply(status, id));
	}

	@GetMapping("/v1/delRelatedUser")
	public AjaxResponse delRelatedUser(Long id) throws ServiceException {
		return succees(service.delRelatedUser(id));
	}

	/**
	 * 获取监督好友相关信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getRelatedUser")
	public AjaxResponse getRelatedUser(Long status) {
		return succees(service.getRelatedUser(status));
	}
	
	/**
	 * 获取监督好友申请
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getRelatedApply")
	public AjaxResponse getRelatedApply(Long status) {
		return succees(service.getRelatedApply(status));
	}
	
	/**
	 * 获取自己的信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getMyInfo")
	public AjaxResponse getMyInfo() {
		return succees(service.getMyInfo());
	}
	
	/**
	 * 获取监督者的信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getSuperviseInfo")
	public AjaxResponse getSuperviseInfo() {
		return succees(service.getSuperviseInfo());
	}
}
