package com.lch.service.lamp.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.lamp.GoodsType;
import com.lch.repo.lamp.GoodsTypeRepo;
import com.lch.service.lamp.GoodsTypeService;

@Service
@Transactional(readOnly = false)
public class GoodsTypeServiceImpl extends DataService<GoodsTypeRepo, GoodsType> implements GoodsTypeService {

	
	@Override
	public List<GoodsType> getAllGoodsType(Long status) {
		return repo.getAllGoodsType(status);
	}

	@Override
	public GoodsType getGoodsTypeById(Long id) {
		return repo.get(id);
	}

	@Override
	public Integer delGoodsType(List<String> idList) throws ServiceException {
		return repo.batchDelete(idList);
	}

	@Override
	public Integer updGoodsType(GoodsType goodsType) throws ServiceException {
		GoodsType g = repo.getGoodsTypeByName(goodsType.getName());
		if(g != null) {
			if(repo.get(goodsType.getId()).getName() != goodsType.getName()) {
				throw new ServiceException("类型名称已经存在，取个其他的名称吧！"); 
			}
		}
		return repo.update(goodsType);
	}

	@Override
	public Integer addGoodsType(GoodsType goodsType) throws ServiceException {
		return repo.insert(goodsType);
	}

	@Override
	public Integer updGoodsTypeStatus(Long id, Long status) {
		return repo.updStatus(id, status);
	}

	@Override
	public List<GoodsType> getOrderCountGroupByType() {
		return repo.getOrderCountGroupByType();
	}

}
