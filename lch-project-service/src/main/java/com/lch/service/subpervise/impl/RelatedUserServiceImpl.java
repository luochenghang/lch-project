package com.lch.service.subpervise.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.DataService;
import com.lch.common.config.UserUtils;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.common.UserBase;
import com.lch.entity.supervise.RelatedUser;
import com.lch.entity.supervise.UserSimpleBase;
import com.lch.entity.supervise.Criteria.UserCriteria;
import com.lch.repo.supervise.RelatedUserRepo;
import com.lch.service.subpervise.RelatedUserService;
import com.lch.utils.ListUtils;

@Service
@Transactional(readOnly = false)
public class RelatedUserServiceImpl extends DataService<RelatedUserRepo, UserBase> implements RelatedUserService {

	@Override
	public int addFriend(RelatedUser relatedUser) throws ServiceException {

		Long userId = UserUtils.getCurrentUserId();

		if (userId == relatedUser.getRelatedUserId()) {
			throw new ServiceException("不能添加自己作为监督者");
		}

		if (repo.isExistsFriend(userId, relatedUser.getRelatedUserId()) > 0) {
			throw new ServiceException("你已经添加该用户了!");
		}

		if (repo.isExistsFriend(relatedUser.getRelatedUserId(), userId) > 0) {
			throw new ServiceException("对方已经添加你了,快去同意吧!");
		}

		UserSimpleBase related = repo.getRelatedUser(userId, 1L).size() > 0 ? repo.getRelatedUser(userId, 1L).get(0)
				: null;
		if (related != null && related.getStatus() == 1) {
			throw new ServiceException("一个用户只能存在一个监督者");
		}

		relatedUser.setUserId(userId);
		repo.delRelatedUser(userId, 2L);
		return repo.addFriend(relatedUser);

	}

	@Override
	public Page<UserSimpleBase> findFriend(UserCriteria criteria) {

		criteria.setUserId(UserUtils.getCurrentUserId());

		setPage(criteria);
		return PageAdapter.adpater(new PageInfo<UserSimpleBase>(repo.findFriend(criteria)));
	}

	@Override
	public int agreeApply(Long status, Long id) throws ServiceException {
		Long userId = UserUtils.getCurrentUserId();

		UserSimpleBase related = repo.getRelatedUser(userId, 1L).size() > 0 ? repo.getRelatedUser(userId, 1L).get(0)
				: null;

		// 这里的id是关联表里面的relatedUserId
		if (related != null && userId == related.getId()) {
			throw new ServiceException("不能操作自己的请求!");
		}

		if (related != null && status == 1L) {
			throw new ServiceException("你的监督者已经关联了!");
		}

		return repo.agreeApply(userId, status, id);
	}

	@Override
	public List<UserSimpleBase> getRelatedUser(Long status) {
		Long userId = UserUtils.getCurrentUserId();

		return repo.getRelatedUser(userId, status);
	}

	@Override
	public int delRelatedUser(Long id) throws ServiceException {
		Long userId = UserUtils.getCurrentUserId();
		return repo.delRelatedUser(userId, id);
	}

	@Override
	public void initUserSimpleInfo() {
		Long userId = UserUtils.getCurrentUserId();
		if (userId != null && userId > 0) {
			UserSimpleBase user = repo.getMyInfo(userId);
			if (user == null) {
				repo.addUserSimpleInfo(userId);
			}
		}
	}

	@Override
	public List<UserSimpleBase> getRelatedApply(Long status) {

		Long userId = UserUtils.getCurrentUserId();
		return repo.getRelatedApply(userId, status);
	}

	@Override
	public UserSimpleBase getMyInfo() {
		return repo.getMyInfo(UserUtils.getCurrentUserId());
	}

	@Override
	public UserSimpleBase getSuperviseInfo() {
		Long userId = UserUtils.getCurrentUserId();
		// 两个方法分别对应userId和relatedId
		List<UserSimpleBase> u = repo.getRelatedApply(userId, 1L);
		if (ListUtils.isNotBlank(u)) {
			return u.get(0);
		} else {
			u = repo.getRelatedUser(userId, 1L);
			if (ListUtils.isNotBlank(u)) {
				return u.get(0);
			}
		}
		return null;
	}

}
