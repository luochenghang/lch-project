package com.lch.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.lch.cache.redis.RedisUtil;
import com.lch.common.constant.Redis;

/**
 * 缓存顶级类
 * @date 2019年12月10日 下午3:23:56
 */
public abstract class Cache extends Redis{

	/** 默认使用redis实现 **/
	@Autowired
	protected RedisUtil cache;

}
