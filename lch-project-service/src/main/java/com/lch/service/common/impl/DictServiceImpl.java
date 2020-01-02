package com.lch.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lch.cache.redis.RedisUtil;
import com.lch.common.constant.Redis;
import com.lch.entity.common.Dict;
import com.lch.repo.common.DictRepo;
import com.lch.service.common.DictService;
import com.lch.utils.ListUtils;
import com.lch.utils.StringUtils;


/**
 * 数据字典业务逻辑处理类
 */
@Service
@Transactional(readOnly = true)
public class DictServiceImpl implements DictService {

	@Autowired
	private DictRepo dictRepo;

	@Autowired
	private RedisUtil redis;

	@Override
	public List<Dict> findList(String type) {
		@SuppressWarnings("unchecked")
		List<Dict> list = (List<Dict>) redis.hget(Redis.SYS_DICT, type);
		if(ListUtils.isBlank(list)) {
			list = dictRepo.findList(type);
			if(ListUtils.isNotBlank(list)) {
				redis.hset(Redis.SYS_DICT, type, list);
			}
		}
		return list;
	}

	@Override
	public String getValue(String type) {
		String val = (String) redis.hget(Redis.SYS_DICT, type);
		if(StringUtils.isBlank(val)) {
			val = dictRepo.getValue(type);
			if(StringUtils.isNotBlank(val)) {
				redis.hset(Redis.SYS_DICT, type, val);
			}
		}
		return val;
	}

}
