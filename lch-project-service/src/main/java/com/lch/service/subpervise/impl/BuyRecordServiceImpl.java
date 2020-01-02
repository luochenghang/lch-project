package com.lch.service.subpervise.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.DataService;
import com.lch.common.config.UserUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.supervise.BuyRecord;
import com.lch.entity.supervise.GiftInfo;
import com.lch.entity.supervise.UserSimpleBase;
import com.lch.entity.supervise.Criteria.BuyRecordCriteria;
import com.lch.repo.supervise.BuyRecordRepo;
import com.lch.repo.supervise.GiftInfoRepo;
import com.lch.repo.supervise.RelatedUserRepo;
import com.lch.service.subpervise.BuyRecordService;
import com.lch.utils.RegexUtils;
import com.lch.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public class BuyRecordServiceImpl extends DataService<BuyRecordRepo, BuyRecord> implements BuyRecordService {

	@Autowired
	private GiftInfoRepo giftRepo;

	@Autowired
	private RelatedUserRepo userRepo;

	@Override
	@Transactional(readOnly = false)
	public int addBuyRecord(BuyRecord record) throws ServiceException {

		Long userId = UserUtils.getCurrentUserId();
		// 获取礼物的数量计算总消费
		GiftInfo gift = giftRepo.getGiftById(record.getGiftId(), userId);
		if (gift == null) {
			throw new ServiceException("礼物不合法");
		}

		if (RegexUtils.isINTEGER_POSITIVE(record.getBuyNum() + "")) {
			throw new ServiceException("购买次数不合法");
		}

		Long totalCoins = gift.getCoins() * record.getBuyNum();// 消费总金币数

		// 获取个人用户的信息
		UserSimpleBase userInfo = userRepo.getMyInfo(userId);
		if (userInfo == null) {
			throw new ServiceException("请登录,你还没有信息");
		}

		if (userInfo.getTotalCoins() < totalCoins) {
			throw new ServiceException("您的金币不足,快去做任务赚取金币吧!");
		}
		
		if (gift.getUserId() != userId) {
			throw new ServiceException("这不是给你创建的礼物,喜欢就找你的TA设计吧!");
		}
		
		record.setUserId(userId).setTotalCoins(totalCoins);
		// 满足条件,更新个人信息,减少金币数,添加购买记录
		repo.updUserTotalCoins(userId, -totalCoins);

		return repo.addBuyRecord(record);

	}

	@Override
	@Transactional(readOnly = false)
	public int updBuyRecord(BuyRecord record) throws ServiceException {
		// 这里只是更新状态
		Long userId = UserUtils.getCurrentUserId();
		Long status = record.getStatus();

		if (!StringUtils.isAppointParam(status, 1L, 2L, 3L)) {
			throw new ServiceException("status参数不合法!");
		}

		BuyRecord r = repo.get(record.getId());
		// status 0配送中 1已到货 2未到货 3取消
		if (r == null || r.getStatus() == 1L || r.getStatus() == 3L) {
			throw new ServiceException("该订单已经不能进行处理了!");
		}

		record.setUserId(userId);

		if (status == 1L) {// 设置到货时间
			record.setArrivalDate(new Date());
		}

		if (status == 3L) {// 更新为取消
			// 返还金币数
			repo.updUserTotalCoins(userId, r.getTotalCoins());

			return repo.updBuyRecord(record);
		}
		return repo.updBuyRecord(record);
	}

	@Override
	@Transactional(readOnly = false)
	public int delBuyRecord(Long id) throws ServiceException {

		Long userId = UserUtils.getCurrentUserId();

		BuyRecord r = repo.get(id);

		if (r.getUserId() != userId) {
			throw new ServiceException("您不能删除TA的订单哟!");
		}
		return repo.delBuyRecord(id, userId);
	}

	@Override
	public Page<BuyRecord> getAllBuyRecord(BuyRecordCriteria criteria) {

		setPage(criteria);

		Long userId = UserUtils.getCurrentUserId();

		if (criteria.getIsMe() == 0L) {
			criteria.setUserId(giftRepo.getRelatedUser(userId));
		} else {
			criteria.setUserId(userId);
		}

		return PageAdapter.adpater(new PageInfo<BuyRecord>(repo.getAllBuyRecord(criteria)));
	}

}
