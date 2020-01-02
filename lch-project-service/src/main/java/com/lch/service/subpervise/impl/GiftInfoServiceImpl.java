package com.lch.service.subpervise.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.DataService;
import com.lch.common.config.UserUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.supervise.GiftInfo;
import com.lch.entity.supervise.GiftPraise;
import com.lch.entity.supervise.Criteria.GiftCriteria;
import com.lch.repo.supervise.GiftInfoRepo;
import com.lch.service.subpervise.GiftInfoService;

@Service
@Transactional(readOnly = true)
public class GiftInfoServiceImpl extends DataService<GiftInfoRepo, GiftInfo> implements GiftInfoService {

	@Override
	@Transactional(readOnly = false)
	public int addGift(GiftInfo gift) throws ServiceException {

		Long userId = UserUtils.getCurrentUserId();
		// 获取有关联的userId
		Long relatedUser = repo.getRelatedUser(userId);

		if (relatedUser == null) {
			throw new ServiceException("您还没有与你的好友存在关联关系哦");
		}
		gift.setCreateUser(userId);
		// 判断是否是未自己创建礼物
		if (gift.getIsForMe() == 1L) {
			// 为自己创建
			gift.setUserId(userId).setAuditStatus(0L);
		} else {
			// 为他建立礼物
			gift.setUserId(relatedUser).setAuditStatus(1L);
		}

		if (repo.isExistGift(gift) > 0) {
			throw new ServiceException("你已经创建这个礼物了");
		}

		return repo.addGift(gift);
	}

	@Override
	public Page<GiftInfo> getAllGift(GiftCriteria criteria) {

		setPage(criteria);
		return PageAdapter.adpater(new PageInfo<GiftInfo>(repo.getAllGift(criteria)));
	}

	@Override
	@Transactional(readOnly = false)
	public int auditGift(Long id, Long auditStatus, String reason) throws ServiceException {
		Long userId = UserUtils.getCurrentUserId();

		GiftInfo gift = repo.getGiftById(id, userId);
		if (gift == null) {
			throw new ServiceException("您的礼物id不合法,或者不存在该礼物!");
		}

		if (gift.getAuditStatus() != 0) {
			throw new ServiceException("您已经审核过这个礼物了,不能进行审核了");
		}
		// 获取有关联的userId
		Long relatedUser = repo.getRelatedUser(userId);
		return repo.auditGift(id, auditStatus, reason, relatedUser);
	}

	@Override
	public GiftInfo getGiftById(Long id) {
		Long userId = UserUtils.getCurrentUserId();
		return repo.getGiftById(id, userId);
	}

	@Override
	@Transactional(readOnly = false)
	public int updGift(GiftInfo gift) throws ServiceException {
		Long userId = UserUtils.getCurrentUserId();

		GiftInfo giftInfo = repo.getGiftById(gift.getId(), userId);

		if (giftInfo == null) {
			throw new ServiceException("该礼物的id存在问题!");
		}
		if (giftInfo.getCreateUser() != userId) {
			throw new ServiceException("该礼物不是您创建的,不能编辑别人的哟!");
		}
		if (giftInfo.getAuditStatus() == 0L) {
			throw new ServiceException("该礼物不能在审核中进行编辑!");
		}

		gift.setCreateUser(UserUtils.getCurrentUserId());
		return repo.updGift(gift);
	}

	@Override
	@Transactional(readOnly = false)
	public int delGift(Long id) throws ServiceException {
		Long userId = UserUtils.getCurrentUserId();

		GiftInfo giftInfo = repo.getGiftById(id, userId);

		if (giftInfo == null) {
			throw new ServiceException("该礼物的id存在问题!");
		}
		if (giftInfo.getCreateUser() != userId) {
			throw new ServiceException("该礼物不是您创建的,不能删除别人的哟!");
		}
		if (giftInfo.getAuditStatus() == 0L) {
			throw new ServiceException("该礼物不能在审核中删除!");
		}

		return repo.delGift(id, userId);
	}

	@Override
	public Page<GiftInfo> getGiftByUser(GiftCriteria criteria, Long isMe) {
		Long userId = UserUtils.getCurrentUserId();
		if (isMe == 1L) {
			criteria.setUserId(userId);
		} else {
			// 获取有关联的userId
			Long relatedUser = repo.getRelatedUser(userId);
			criteria.setUserId(relatedUser);
		}

		setPage(criteria);
		return PageAdapter.adpater(new PageInfo<GiftInfo>(repo.getGiftByUser(criteria)));
	}

	@Override
	@Transactional(readOnly = false)
	public int addGiftPraise(Long giftId) {
		Long userId = UserUtils.getCurrentUserId();
		int r = 0;
		GiftPraise p = repo.getGiftPraise(userId, giftId);
		if (p == null) {
			repo.addGiftPraise(userId, giftId);
			// 更新总数
			r = repo.updPriase(userId, giftId, 1L, 1L);
		} else {
			Long praiseNum = p.getStatus() == 1L ? -1L : 1L; // 正负1

			Long status = p.getStatus() == 1L ? 0L : 1L;// 1点赞 0取消点赞

			r = repo.updPriase(userId, giftId, praiseNum, status);
		}
		return r;
	}

}
