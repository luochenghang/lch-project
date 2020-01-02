package com.lch.service.subpervise;

import java.util.List;

import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.entity.supervise.RelatedUser;
import com.lch.entity.supervise.UserSimpleBase;
import com.lch.entity.supervise.Criteria.UserCriteria;

public interface RelatedUserService {

	/**
	 * 建立互相监督关联关系
	 * 
	 * @param relatedUser
	 * @return
	 */
	int addFriend(RelatedUser relatedUser) throws ServiceException;

	/**
	 * 查找用户 关键字查询
	 * 
	 * @param criteria
	 * @return
	 */
	Page<UserSimpleBase> findFriend(UserCriteria criteria);

	/**
	 * 是否同意关联关系
	 * 
	 * @param status 0表示未关联 1表示已关联 2拒绝关联
	 * @return
	 */
	int agreeApply(Long status, Long id) throws ServiceException;

	/**
	 * 获取有关联人的信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserSimpleBase> getRelatedUser(Long status);

	/**
	 * 删除关联的人
	 * 
	 * @param userId
	 * @return
	 */
	int delRelatedUser(Long id) throws ServiceException;

	/**
	 * 初始化用户信息
	 * 
	 * @param userId
	 * @return
	 */
	void initUserSimpleInfo();
	
	/**
	 * 获取好友申请列表
	 * @param userId
	 * @param status
	 * @return
	 */
	List<UserSimpleBase> getRelatedApply(Long status);
	
	/**
	 * 获取自己的信息
	 * 
	 * @param userId
	 * @return
	 */
	UserSimpleBase getMyInfo();
	
	/**
	 * 获取监督者的信息
	 * @return
	 */
	UserSimpleBase getSuperviseInfo();
	
	
}
