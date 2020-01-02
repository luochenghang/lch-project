package com.lch.utils;

import java.security.MessageDigest;

/**
 * MD5/SHA1加密工具类
 * @date 2019年11月20日 上午11:29:54
 */
public class HexUtils {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	/*
	 * MD5加密
	 */
	public static String MD5(String str) {
		try {
			byte[] strTemp = str.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			return byteArrayToHexString(mdTemp.digest());
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * SHA-1加密
	 */
	public static String SHA1(String str) {
		try {
			byte[] strTemp = str.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("SHA-1");
			mdTemp.update(strTemp);
			return byteArrayToHexString(mdTemp.digest());
		} catch (Exception e) {
			return null;
		}
	}
	
}
