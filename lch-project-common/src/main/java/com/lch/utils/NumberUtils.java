package com.lch.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * BigDecimal工具类
 * @date 2018年10月30日 上午10:09:05
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, 10);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 格式化双精度，保留两个小数
	 * @return
	 */
	public static String formatDouble(Double b) {
		BigDecimal bg = new BigDecimal(b);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 百分比计算
	 * @return
	 */
	public static String formatScale(double one, long total) {
		BigDecimal bg = new BigDecimal(one * 100 / total);
		return bg.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 格式化数值类型
	 * @param data
	 * @param pattern
	 */
	public static String formatNumber(Object data, String pattern) {
		DecimalFormat df = null;
		if (pattern == null) {
			df = new DecimalFormat();
		} else {
			df = new DecimalFormat(pattern);
		}
		return df.format(data);
	}

	/**
	 * 是否包含指定数字
	 * @param val
	 * @param ints
	 * @return
	 */
	public static boolean isAppointNum(Integer val, Integer ... ints) {
		if(val == null) {
			return false;
		}
		if(ints != null && ints.length > 0) {
			int f = 0;
			for(int i=0;i<ints.length;i++) {
				if(val == ints[i]) {
					++f;
				}
			}
			if(f == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 提供精确的小数位四舍五入处理
	 *
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v1, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale不能小于0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v1));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 格式化double，四舍五入保留两位小数
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		// 保留两位小数
		nf.setMaximumFractionDigits(2);
		// 如果不需要四舍五入，可以使用RoundingMode.DOWN
		nf.setRoundingMode(RoundingMode.UP);

		return nf.format(d);
	}

}
