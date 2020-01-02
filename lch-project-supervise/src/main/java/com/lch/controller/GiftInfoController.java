package com.lch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lch.common.base.DataController;
import com.lch.common.config.AjaxResponse;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.annotation.auth.AuthIgnore;
import com.lch.entity.supervise.GiftInfo;
import com.lch.entity.supervise.Criteria.GiftCriteria;
import com.lch.service.subpervise.GiftInfoService;
import com.lch.utils.StringUtils;

@RestController
@RequestMapping("/api")
public class GiftInfoController extends DataController<GiftInfoService> {

	/**
	 * 添加礼物
	 * 
	 * @param gift
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/addGift")
	public AjaxResponse addGift(GiftInfo gift) throws ServiceException {
		return succees(service.addGift(gift));
	}

	/**
	 * 点赞或者取消点赞
	 * @param giftId
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/v1/addGiftPraise")
	public AjaxResponse addGiftPraise(Long giftId) throws ServiceException {
		return succees(service.addGiftPraise(giftId));
	}
	
	/**
	 * 修改礼物
	 * 
	 * @param gift
	 * @return
	 * @throws ServiceException
	 */
	@PostMapping("/v1/updGift")
	public AjaxResponse updGift(GiftInfo gift) throws ServiceException {
		return succees(service.updGift(gift));
	}

	/**
	 * 删除礼物
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/v1/delGift")
	public AjaxResponse delGift(Long id) throws ServiceException {
		return succees(service.delGift(id));
	}

	/**
	 * 获取礼物
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping("/v1/getAllGift")
	@AuthIgnore(login = false)
	public AjaxResponse getAllGift(GiftCriteria criteria) {
		return succees(service.getAllGift(criteria));
	}

	/**
	 * 审核礼物
	 * 
	 * @param id
	 * @param auditStatus
	 * @param reason
	 * @return
	 */
	@GetMapping("/v1/auditGift")
	public AjaxResponse auditGift(Long id, Long auditStatus, String reason) throws ServiceException {

		if (auditStatus == 2L && StringUtils.isBlank(reason)) {
			return fail("审核失败的原因不能为空!");
		}

		if (!StringUtils.isAppointParam(auditStatus, 1L, 2L)) {
			return fail("参数auditStatus错误!(1审核通过 2审核失败)");
		}

		if (auditStatus == 1L) {
			reason = "";
		}
		return succees(service.auditGift(id, auditStatus, reason));
	}

	/**
	 * 根据id 查询礼物
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/getGiftById")
	@AuthIgnore(login = false)
	public AjaxResponse getGiftById(Long id) {
		return succees(service.getGiftById(id));
	}

	/**
	 * 根据用户 查询礼物
	 * 
	 * @param isMe 1自己 0监督者
	 * @return
	 */
	@GetMapping("/v1/getGiftByUser")
	public AjaxResponse getGiftByUser(GiftCriteria criteria, Long isMe) {
		return succees(service.getGiftByUser(criteria, isMe));
	}
}
