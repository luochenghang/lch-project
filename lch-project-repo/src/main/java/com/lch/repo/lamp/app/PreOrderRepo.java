package com.lch.repo.lamp.app;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lch.common.base.BaseRepo;
import com.lch.entity.lamp.PreOrder;

@Mapper
public interface PreOrderRepo extends BaseRepo<PreOrder>{

	
	/**
	 * 查询全部
	 * @return
	 */
	List<PreOrder> getAllPreOrder(Long status, Date createDate);

	@Update("update t_pre_order set status = #{status} where id = #{id}")
	Integer updPreOrderStatus(Long id, Long status);
	
	@Update("update t_pre_order set isCollect = #{isCollect} where id = #{id} and userId = #{userId}")
	Integer updPreOrderIsCollect(Long id,Long userId, Long isCollect);

	@Select("select * from t_pre_order where userId = #{userId} and goodsId = #{goodsId}")
	PreOrder getPreOrderIsExist(Long userId, Long goodsId);
	
	//获取每种状态的数量
	PreOrder getPreOrderCountGroupByStatus();


    List<PreOrder> getPreOrderByUserId(Long userId);
}