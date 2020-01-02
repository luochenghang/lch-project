package com.lch.service.lamp.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.Criteria;
import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.lamp.Goods;
import com.lch.repo.lamp.GoodsRepo;
import com.lch.service.lamp.GoodsService;
import com.lch.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public class GoodsServiceImpl extends DataService<GoodsRepo, Goods> implements GoodsService {

	@Override
	public Page<Goods> getAllGoods(Criteria criteria) {
		setPage(criteria);

		return PageAdapter.adpater(new PageInfo<Goods>(repo.getAllGoods(criteria)));
	}

	@Override
	public Goods getGoodsById(Long id) {
		return repo.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer delGoods(String ids) throws ServiceException {
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
			return repo.batchDelete(idList);
		}
		throw new ServiceException("没有选择id");
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updGoods(Goods goods) throws ServiceException {
		return repo.update(goods);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updGoodsStatus(Long status, Long id) throws ServiceException {
		return repo.updGoodsStatus(status, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer addGoods(Goods goods) throws ServiceException {
		return repo.insert(goods);
	}

}
