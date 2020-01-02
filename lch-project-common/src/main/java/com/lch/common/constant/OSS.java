package com.lch.common.constant;

import com.lch.utils.ConfigUtils;

/**
 * OSS文件操作常量
 * @date 2019年12月10日 下午4:46:06
 */
public final class OSS {

	private static ConfigUtils loader;

	static {
		loader = ConfigUtils.getInstance();
	}

	public final static String ACCESS_ID = loader.getConfigValue("oss.access.id");

	public final static String ACCESS_SECRET = loader.getConfigValue("oss.access.secret");

	public final static String ENDPOINT = loader.getConfigValue("oss.endpoint");

	public final static String BUCKET_NAME = loader.getConfigValue("oss.bucket.name");

	public final static String PREFIX = loader.getConfigValue("oss.prefix");// 下载时用的固定的前缀

	public static final long EXPIRE = 10800000L; // url签名默认过期时长3小时(单位：毫秒)

	/**
	 * 视频截图参数
	 */
	public static final String VIDEO_SCREENSHOT_PARAMS = "?x-oss-process=video/snapshot,t_0,w_0,h_0";

}
