package com.lch.service.lamp.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.Goods;
import com.lch.entity.lamp.vo.PayRecordVo;
import com.lch.repo.lamp.PayRecordRepo;
import com.lch.repo.lamp.GoodsRepo;
import com.lch.service.lamp.PayRecordService;

@Service
@Transactional(readOnly = false)
public class PayRecordServiceImpl extends DataService<PayRecordRepo, PayRecordVo> implements PayRecordService {

	@Autowired
	private GoodsRepo goodsRepo;
	
	@Override
	public List<PayRecordVo> getAllPayRecord() {
		return repo.getAllPayRecord();
	}

	@Override
	public PayRecordVo getPayRecordById(Long id) {
		return repo.get(id);
	}

	@Override
	public Integer delPayRecord(Long id) throws ServiceException {
		return repo.delete(id);
	}

	@Override
	public Integer updPayRecord(PayRecordVo payRecord) throws ServiceException {
		return repo.update(payRecord);
	}

	@Override
	public Integer addPayRecord(PayRecordVo payRecord) throws ServiceException {
		Goods goods = goodsRepo.get(payRecord.getGoodsId());
		
		if(goods!= null) {
			Double earnPrice = payRecord.getRealPrice() - goods.getCostPrice();
			payRecord.setEarnPrice(earnPrice);
			return repo.insert(payRecord);
		}
		return null;
		
	}

}
