package com.lch.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * @author Jun
 * @date 2018年10月30日 上午10:52:33
 */
public class CookieUtils {

	/**
	 * 设置 Cookie（生成时间为1天）
	 * @param name 名称
	 * @param value 值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, 60*60*24);
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 * @param uri 路径
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		setCookie(response, name, value, path, 60*60*24);
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 * @param uri 路径
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, "/", maxAge);
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 * @param uri 路径
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 获得指定Cookie的值
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}
	/**
	 * 获得指定Cookie的值，并删除。
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookie(request, response, name, true);
	}
	/**
	 * 获得指定Cookie的值
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param name 名字
	 * @param isRemove 是否移除
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemove) {
						cookie.setMaxAge(0);
						cookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}
	
	/**
	 * 将ID添加到cookie中,并返回最终的cookie字符串
	 * @param cookieName
	 * @param ep_id
	 * @param request
	 * @return
	 */
	public static String buildCookie(String cookieName, String ep_id, HttpServletRequest request) { 
		 StringBuilder sb = new StringBuilder(); 
		 String recodeValue = getCookie(request,cookieName);
		 if(recodeValue!=null){
			 //装换成LinkedList很有必要,普通的ArrayList要实现最近浏览的数据移动到最前面比较困难,不如LinkedList提供的方法来的直接
			 LinkedList<String> s = new LinkedList<String>(Arrays.asList(recodeValue.split(",")));
			 //判断ep_id是否为空,如果为空,则为首页显示
			 //如果不为空,则判断LinkedList中是否包含此ID,如果包含,则移除掉,然后将此ID放置到列表中的第一个位置
			 if(ep_id!=null){
				 if(s.contains(ep_id)){
					 s.remove(ep_id);
				 }
				 //显示10条数据
				 if(s.size()>10){
					 s.removeLast();
				 }
				 s.addFirst(ep_id);
			 }
			 for(String bid : s){
				 sb.append(bid+",");
			 }
			 return sb.deleteCharAt(sb.length()-1).toString();
		 }
		 return ep_id;
	 }
	/**
	 * 修改指定Cookie的值
	 */
	public static void updatePointValue(HttpServletRequest request, HttpServletResponse response, String name,String val) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		String str = ""; //针对只有一个值的情况，默认就清空了
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8");
						String[] split = value.split(",");
						if(split!=null && split.length>1){
							//有多个actorId，并且需要被清除的的位置是第一个
							if(value.indexOf(val)==0){
								str = value.replace(val+",",""); //后置的逗号也要去除
							}else{//位置不是第一个
								str = value.replace(","+val,""); //前置的逗号要去除，这样可以处理位置在最后一个的情况
							}
						}
						cookie.setValue(str); //修改后的cookie的值
						response.addCookie(cookie);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
