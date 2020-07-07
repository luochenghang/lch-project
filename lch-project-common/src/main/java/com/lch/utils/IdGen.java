package com.lch.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @date 2019年6月17日 下午2:32:46
 */
@Service
@Lazy(false)
public class IdGen {

	private static final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
	private static final CountDownLatch latch = new CountDownLatch(1);

	/** 每毫秒生成订单号数量最大值，约定取整百，整千 **/
	public static final int maxPerMSECSize = 100;

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}


	/**
	 * 生成任意长度的随机数字字符串
	 *
	 * @param len
	 * @return
	 */
	public static String randomString(int len) {
		String str = "";
		str += (int) (Math.random() * 9 + 1);
		for (int i = 0; i < len - 1; i++) {
			str += (int) (Math.random() * 10);
		}
		return str;
	}

	/**
	 * 生成非重复订单号，基于队列，能解决高并发问题
	 */
	public static String uniqueNum() {
		long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		String number = maxPerMSECSize + poll() + "";
		return nowLong + number.substring(1);
	}
	
	/**
	 * 获取十六进制的非重复订单号
	 * @return
	 */
	public static String uniqueHexNum() {
		return Long.toHexString(Long.valueOf(uniqueNum())).toUpperCase();
	}

	// 初始化队列
	private static void init() {
		for (int i = 0; i < maxPerMSECSize; i++) {
			queue.offer(i);
		}
		latch.countDown();
	}

	// 从队列中获取
	public static Integer poll() {
		try {
			if (latch.getCount() > 0) {
				init();
				latch.await(1, TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer i = queue.poll();
		queue.offer(i);
		return i;
	}

}
