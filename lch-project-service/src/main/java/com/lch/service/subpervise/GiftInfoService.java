 package com.lch.service.subpervise;

import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.entity.supervise.GiftInfo;
import com.lch.entity.supervise.Criteria.GiftCriteria;

public interface GiftInfoService{

	
	/**
	 * 添加礼物(给别人设计以及给自己设计)
	 * 
	 * @param gift
	 * @return
	 */
	int addGift(GiftInfo gift) throws ServiceException;
	
	/**
	 * 修改礼物
	 * 
	 * @param gift
	 * @return
	 */
	int updGift(GiftInfo gift) throws ServiceException;

	/**
	 * 删除礼物
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	int delGift(Long id) throws ServiceException;

	/**
	 * 查询所有的礼物,tabbar显示
	 * @param criteria
	 * @return
	 */
	Page<GiftInfo> getAllGift(GiftCriteria criteria);

	/**
	 * 审核礼物
	 * 
	 * @param id
	 * @param auditStatus 0审核中, 1 审核成功 2审核失败
	 * @param reason      失败原因
	 * @return
	 */
	int auditGift(Long id, Long auditStatus, String reason) throws ServiceException;

	/**
	 * 根据id查询礼物详情
	 * 
	 * @param id
	 * @return
	 */
	GiftInfo getGiftById(Long id);
	
	/**
	 * 通过用户查询礼物信息
	 * @param criteria
	 * @param isMe 1查询自己 0查询监督者
	 * @return
	 */
	Page<GiftInfo> getGiftByUser(GiftCriteria criteria,Long isMe);
	
	
	/**
	 * 点赞或者取消点赞
	 * @param userId
	 * @param giftId
	 * @return
	 */
	int addGiftPraise(Long giftId);
}
