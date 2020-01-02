package com.lch.repo.supervise;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.supervise.BuyRecord;
import com.lch.entity.supervise.Criteria.BuyRecordCriteria;

@Mapper
public interface BuyRecordRepo extends BaseRepo<BuyRecord> {

	/**
	 * 添加兑换记录 购买的时候添加
	 * 
	 * @param record
	 * @return
	 */
	int addBuyRecord(BuyRecord record);

	/**
	 * 更新兑换记录 (购买相同的礼物时将会执行,增加购买数量,以及更新状态)
	 * 
	 * @param record
	 * @return
	 */
	int updBuyRecord(BuyRecord record);

	/**
	 * 删除兑换记录
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	@Delete("delete from t_buy_record where id = #{id} and userId = #{userId}")
	int delBuyRecord(Long id, Long userId);

	/**
	 * 根据状态或者用户来查询兑换记录
	 * 
	 * @return
	 */
	List<BuyRecord> getAllBuyRecord(BuyRecordCriteria criteria);
	
	@Update("update t_user_simple_info set totalCoins= totalCoins + #{totalCoins} where userId=#{userId}")
	int updUserTotalCoins(Long userId, Long totalCoins);

}
