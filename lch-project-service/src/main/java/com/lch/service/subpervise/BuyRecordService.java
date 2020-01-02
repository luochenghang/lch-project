 package com.lch.service.subpervise;

import org.apache.ibatis.annotations.Delete;

import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.entity.supervise.BuyRecord;
import com.lch.entity.supervise.Criteria.BuyRecordCriteria;

public interface BuyRecordService{

	
	/**
	 * 添加兑换记录 购买的时候添加
	 * 
	 * @param record
	 * @return
	 * @throws ServiceException 
	 */
	int addBuyRecord(BuyRecord record) throws ServiceException;

	/**
	 * 更新兑换记录 (购买相同的礼物时将会执行,增加购买数量,以及更新状态)
	 * 
	 * @param record
	 * @return
	 * @throws ServiceException 
	 */
	int updBuyRecord(BuyRecord record) throws ServiceException;

	/**
	 * 删除兑换记录
	 * 
	 * @param id
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 */
	@Delete("delete from t_buy_record where id = #{id} and userId = #{userId}")
	int delBuyRecord(Long id) throws ServiceException;

	/**
	 * 根据状态或者用户来查询兑换记录
	 * 
	 * @return
	 */
	Page<BuyRecord> getAllBuyRecord(BuyRecordCriteria criteria);

}
