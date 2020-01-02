package com.lch.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import com.google.common.collect.Maps;

/**
 * Map工具类，继承自org.apache.commons.collections.MapUtils
 * @date 2019年6月26日 下午3:22:18
 */
@SuppressWarnings("unchecked")
public class MapUtils extends org.apache.commons.collections.MapUtils {

	/**
	 * 将Map<Object, Object>转为Map<String, Object>
	 *
	 * @param map
	 * @return
	 */
	public static Map<String, Object> convertMap(Map<Object, Object> map) {
		Map<String, Object> m = Maps.newHashMap();
		if (isNotEmpty(map)) {
			map.forEach((k, v) -> {
				if (k != null) {
					m.put(String.valueOf(k), v);
				}
			});
		}
		return m;
	}

	/**
	 * 将对象转换成成Map
	 *
	 * @param src
	 * @return
	 */
	public static <K, V> Map<K, V> toMap(Object src) {
		return BeanMap.create(src);
	}

	/**
	 * 将Map转换成bean对象
	 *
	 * @param beanMap
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map<String, Object> beanMap, Class<T> clazz) {
		// 对象实例化
		T bean = BeanUtils.instantiateClass(clazz);
		for (PropertyDescriptor propertyDescriptor : BeanUtils.getPropertyDescriptors(clazz)) {
			String properName = propertyDescriptor.getName();
			// 过滤class属性
			if ("class".equals(properName)) {
				continue;
			}
			if (beanMap.containsKey(properName)) {
				Method writeMethod = propertyDescriptor.getWriteMethod();
				if (null == writeMethod) {
					continue;
				}
				Object value = beanMap.get(properName);
				if (!writeMethod.isAccessible()) {
					writeMethod.setAccessible(true);
				}
				try {
					writeMethod.invoke(bean, value);
				} catch (Throwable throwable) {
					throw new RuntimeException("Could not set property '" + properName + " ' to bean" + throwable);
				}
			}
		}
		return bean;
	}

	/**
	 * 移除map中空key或者空value的元素
	 *
	 * @param map
	 */
	public static <K, V> void removeNullEntry(Map<K, V> map) {
		removeNullKey(map);
		removeNullValue(map);
	}

	/**
	 * 移除键为空的元素
	 *
	 * @param map
	 * @return
	 */
	public static <K, V> void removeNullKey(Map<K, V> map) {
		Set<K> set = map.keySet();
		for (Iterator<K> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			remove(obj, iterator);
		}
	}

	/**
	 * 移除值为空的元素
	 *
	 * @param map
	 */
	public static <K, V> void removeNullValue(Map<K, V> map) {
		Set<K> set = map.keySet();
		for (Iterator<K> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			Object value = (Object) map.get(obj);
			remove(value, iterator);
		}
	}

	/**
	 * Iterator移除元素
	 *
	 * @param obj
	 * @param iterator
	 */
	private static <K> void remove(Object obj, Iterator<K> iterator) {
		if (obj instanceof String) {
			String str = (String) obj;
			if (str == null || str.trim().isEmpty()) {
				iterator.remove();
			}
		} else if (obj instanceof Collection) {
			Collection<K> col = (Collection<K>) obj;
			if (col == null || col.isEmpty()) {
				iterator.remove();
			}
		} else if (obj instanceof Map) {
			Map<?, ?> temp = (Map<?, ?>) obj;
			if (temp == null || temp.isEmpty()) {
				iterator.remove();
			}
		} else if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array == null || array.length <= 0) {
				iterator.remove();
			}
		} else {
			if (obj == null) {
				iterator.remove();
			}
		}
	}

	/**
	 * Map分割
	 * @param map
	 * @param limit
	 * @return
	 */
	public static <K, V> List<Map<K, V>> splitMap(Map<K, V> map, int limit) {
		return map.entrySet().stream().collect(splitMap(limit));
	}

	/**
	 * Map分割
	 * @param limit
	 * @return
	 */
	public static <K, V> Collector<Map.Entry<K, V>, ?, List<Map<K, V>>> splitMap(int limit) {
	    return Collector.of(ArrayList::new,
	    		(l, e) -> {
	    			if (l.isEmpty() || l.get(l.size() - 1).size() == limit) {
	    				l.add(new HashMap<>());
    				}
	    			l.get(l.size() - 1).put(e.getKey(), e.getValue());
    			},
	    		(l1, l2) -> {
	    			if (l1.isEmpty()) {
	    				return l2;
    				}
	    			if (l2.isEmpty()) {
	    				return l1;
	    			}
	    			if (l1.get(l1.size() - 1).size() < limit) {
	    				Map<K, V> map = l1.get(l1.size() - 1);
	    				ListIterator<Map<K, V>> mapsIte = l2.listIterator(l2.size());
	    				while (mapsIte.hasPrevious() && map.size() < limit) {
	    					Iterator<Map.Entry<K, V>> ite = mapsIte.previous().entrySet().iterator();
	    					while (ite.hasNext() && map.size() < limit) {
	    						Map.Entry<K, V> entry = ite.next();
	    						map.put(entry.getKey(), entry.getValue());
	    						ite.remove();
	    					}
	    					if (!ite.hasNext()) {
	    						mapsIte.remove();
	    					}
	    				}
	    			}
	    			l1.addAll(l2);
	    			return l1;
	    		}
	    );
	}

	/**
	 * 将Map转为json字符串
	 * @param map
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String map2JsonString(Map<String, Object> map) {
		return JsonUtils.getInstance().toJsonString(map);
	}

}
