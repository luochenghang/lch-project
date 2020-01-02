package com.lch.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: ArrayUtils
 * @Description: 数组的交集、差集、并集、补集运算
 * @date 2018年11月15日 上午10:59:33
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils{

	// 求两个数组的差集 arr1-arr2
	public static String[] subStractString(String[] arr1, String[] arr2) {
		LinkedList<String> list = new LinkedList<String>();
		for (String str : arr1) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : arr2) {
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		String[] result = {};
		return list.toArray(result);
	}


	/**
	 *
	* @Title: removeDuplicates
	* @Description: 去除重复
	* @param @param list1 被过滤的集合
	* @param @param list2 需要过滤的内容
	* @param @return    设定文件
	* @return List<Object>    返回类型
	* @throws
	 */
	public static List<String> removeDuplicates(List<String> list1,List<String> list2){

		List<String> newList = new ArrayList<>();
		for (String object : list1) {
			for (Object object2 : list2) {
				if (object.equals(object2)) {
					newList.add(object);
					break;
				}
			}
		}
		list1.removeAll(newList);
		return list1;
	}
}
