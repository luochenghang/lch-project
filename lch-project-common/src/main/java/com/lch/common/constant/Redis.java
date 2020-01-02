package com.lch.common.constant;

/**
 * redis缓存相关常量
 *
 * @date 2019年12月10日 下午4:46:06
 */
public class Redis {

	/** redis缓存过期时间 5分钟 **/
	public static final Long FIVE_MINUTES = 300L;

	/** redis缓存过期时间 半小时 **/
	public static final Long HALF_HOUR = 1800L;

	/** redis缓存过期时间 1小时 **/
	public static final Long ONE_HOUR = 3600L;

	/** redis缓存过期时间 8小时 **/
	public static final Long EIGHT_HOUR = 3600 * 8L;

	/** redis缓存过期时间 24小时 **/
	public static final Long ONE_DAY = 3600 * 24L;

	/** redis缓存过期时间 3天 **/
	public static final Long THREE_DAY = 3600 * 24 * 3L;

	/** redis缓存过期时间 10天 **/
	public static final Long TEN_DAY = 3600 * 24 * 10L;

	/** redis缓存过期时间 30天 **/
	public static final Long THIRTY_DAY = 3600 * 24 * 30L;

	/** redis缓存USERID前缀 **/
	public static final String REDIS_UID_PREFIX = "uid:";

	/** redis缓存用户位置前缀 **/
	public static final String LOCATION = "location:";
	
	
	/** redis缓存数据字典前缀 **/
	public static final String SYS_DICT = "sys_dict";

}
