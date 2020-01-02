package com.lch.repo.supervise;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lch.common.base.BaseRepo;
import com.lch.entity.supervise.GiftInfo;
import com.lch.entity.supervise.GiftPraise;
import com.lch.entity.supervise.Criteria.GiftCriteria;

@Mapper
public interface GiftInfoRepo extends BaseRepo<GiftInfo> {

	/**
	 * 添加礼物(给别人设计以及给自己设计)
	 * 
	 * @param gift
	 * @return
	 */
	int addGift(GiftInfo gift);

	/**
	 * 修改礼物
	 * 
	 * @param gift
	 * @return
	 */
	int updGift(GiftInfo gift);

	/**
	 * 删除礼物
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	@Delete("delete from t_gift where id = #{id} and createUser = #{userId}")
	int delGift(Long id, Long userId);

	/**
	 * 查询所有的礼物,tabbar显示
	 * 
	 * @return
	 */
	List<GiftInfo> getAllGift(GiftCriteria criteria);

	/**
	 * 审核礼物
	 * 
	 * @param id
	 * @param auditStatus 0审核中, 1 审核成功 2审核失败
	 * @param reason      失败原因
	 * @return
	 */
	int auditGift(Long id, Long auditStatus, String reason, Long relatedUser);

	/**
	 * 根据id查询礼物详情
	 * 
	 * @param id
	 * @return
	 */
	GiftInfo getGiftById(Long id,Long userId);

	/**
	 * 通过用户查询自己的礼物
	 * 
	 * @param userId
	 * @return
	 */
	List<GiftInfo> getGiftByUser(GiftCriteria criteria);

	/**
	 * 获取关联的用户id
	 * 
	 * @param userId
	 * @return
	 */
	@Select("SELECT CASE when userId=#{userId} then relatedUserId else userId end from t_related_user where (userId = #{userId} or relatedUserId = #{userId}) and `status`=1")
	Long getRelatedUser(Long userId);

	/**
	 * 点赞或者取消点赞
	 * 
	 * @param userId
	 * @param giftId
	 * @return
	 */
	int addGiftPraise(Long userId, Long giftId);

	/**
	 * 获取点赞表信息
	 * 
	 * @param userId
	 * @param giftId
	 * @return
	 */
	@Select("select id,userId, giftId, status, createDate, updateDate from t_gift_praise where userId =#{userId} and giftId=#{giftId}")
	GiftPraise getGiftPraise(Long userId, Long giftId);

	/**
	 * 更新点数总数以及点赞表记录
	 * 
	 * @param userId
	 * @param giftId
	 * @param praiseNum 1或者-1
	 * @param status    1点赞 0取消点赞
	 * @return
	 */
	int updPriase(Long userId, Long giftId, Long praiseNum, Long status);
	
	@Select("SELECT count(1) FROM t_gift WHERE userId =#{userId} and title = #{title} and coverImg= #{coverImg} and content=#{content}  and createUser = #{createUser}")
	int isExistGift(GiftInfo gift);
}
