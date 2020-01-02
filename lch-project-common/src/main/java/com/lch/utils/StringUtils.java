package com.lch.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.base.Splitter;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
@SuppressWarnings("deprecation")
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";
	private static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

	/**
	 * 转换为字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 字节数组转换为字符串
	 *
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 如果对象为空，则使用defaultVal值 see: ObjectUtils.toString(obj, defaultVal)
	 *
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static String toString(final Object obj, final String defaultVal) {
		return obj == null ? defaultVal : obj.toString();
	}

	/**
	 * 是否包含字符串
	 *
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 *
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 *
	 * @param objectString 对象串 例如：row.user.id
	 *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 *
	 * @param str    目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return "";
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
	 * 生成指定长度的随机字符串
	 *
	 * @param length
	 * @return
	 */
	public static String generateString(int length) {
		char[] text = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
		}
		return new String(text);
	}

	/**
	 * 对输入参数进行正则验证
	 *
	 * @param parameter
	 * @param patterns  : 匹配的正则表达式
	 * @return true:合法参数；false:非法参数
	 */
	public static boolean isLegalParameter(String parameter, String... patterns) {
		if (patterns != null && patterns.length > 0) {
			for (String pattern : patterns) {
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(parameter);
				if (!m.matches())
					return false;
			}
		}
		return true;
	}

	/**
	 * 统计字符串中的汉字、数字、单词、标点出现的次数
	 *
	 * @param context
	 * @param isPunctuate 是否统计标点 ：true是， false否
	 * @return
	 */
	public static int wordCount(String context, boolean isPunctuate) {
		int count = 0;
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]{1}|[a-zA-Z]+|\\d+|(\\b[a-zA-Z]+\\b)");
		Matcher matcher = pattern.matcher(context);
		while (matcher.find())
			count++;
		if (isPunctuate) {
			pattern = Pattern.compile("[\\p{P}&&[^-_]]");
			matcher = pattern.matcher(context);
			while (matcher.find())
				count++;
		}
		return count;
	}

	/**
	 * 截取0到指定平匹配符之间的字符串
	 *
	 * @param param
	 * @param pattern
	 * @return
	 */
	public static String interceptString(String param, String pattern) {
		String val = "";
		if (isNotBlank(param) && isNotBlank(pattern))
			val = param.substring(0, param.indexOf(pattern));
		return val;
	}

	/**
	 * 判断字符串是否在指定长度之间
	 *
	 * @param text
	 * @param sl   最小长度
	 * @param bl   最大长度
	 * @return
	 */
	public static Boolean isAppointLength(String text, int sl, int bl) {
		return text.length() >= sl && text.length() <= bl;
	}

	/**
	 * 是否包含指定参数
	 *
	 * @param val
	 * @param ints
	 * @return
	 */
	public static boolean isAppointParam(Object val, Object... objs) {
		if (val == null) {
			return false;
		}
		if (objs != null && objs.length > 0) {
			int f = 0;
			for (int i = 0; i < objs.length; i++) {
				if (val.equals(objs[i])) {
					++f;
				}
			}
			if (f == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 提取URL中的参数的值
	 *
	 * @param url
	 * @param name
	 * @return
	 */
	public static Map<String, String> getUrlParam(String url) {
		String params = url.substring(url.indexOf("?") + 1, url.length());
		return Splitter.on("&").withKeyValueSeparator("=").split(params);
	}

	/**
	 * 获取去掉参数的url
	 *
	 * @param url
	 * @return
	 */
	public static String getUrlWithoutParam(String url) {
		return url.substring(0, url.indexOf("?"));
	}

	/**
	 * 首字母转小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

}
