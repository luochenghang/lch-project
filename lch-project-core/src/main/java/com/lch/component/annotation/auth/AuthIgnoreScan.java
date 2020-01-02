package com.lch.component.annotation.auth;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.lch.utils.ClassUtils;



/**
 * AuthIgnore注解扫描工具
 *
 * @Author Jun
 * @date 2019年5月14日 下午4:42:30
 */
public class AuthIgnoreScan {

	/**
	 * class类名
	 */
	static List<String> classPathList = Lists.newArrayList();

	/**
	 * 无需验权的url
	 */
	static List<String> noAuthUris = Lists.newArrayList();

	/**
	 * 无需登录的url
	 */
	static List<String> noLoginUris = Lists.newArrayList();

	/**
	 * 初始化
	 */
	static {
		// 扫描指定路径下的class文件
		scanClass();
		// 使用所有类命名字符串来初始化容器
		initContainer();
	}

	private static AuthIgnoreScan instance;

	private AuthIgnoreScan(){}

	public static AuthIgnoreScan getInstance() {
		if(instance == null) {
			synchronized (AuthIgnoreScan.class) {
				if(instance == null) {
					instance = new AuthIgnoreScan();
				}
			}
		}
		return instance;
	}

	/**
	 * 扫描 Class文件
	 */
	private static void scanClass() {
		classPathList = ClassUtils.getClasses("com.lch.controller")
						.stream()
						.filter(clz -> clz.getSimpleName()
						.endsWith("Controller"))
						.map(Class::getName)
						.collect(Collectors.toList());
	}

	/**
	 * 使用 所有类全命名来初始化容器
	 *
	 * @throws Exception
	 */
	private static void initContainer() {
		// 获取 所有类的类全名
		classPathList.forEach(className -> {
			Class<?> forName = null;
			try {
				forName = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// 初始化限制，初始化的文件类型必须是 class文件
			if (!forName.isAnnotation() && !forName.isEnum() && !forName.isInterface()) {
				String uriPrefix = "";
				if (forName.isAnnotationPresent(RequestMapping.class)) {
					uriPrefix = forName.getAnnotation(RequestMapping.class).value()[0];
				}
				// 只初始化 实现了AuthIgnore注解的类中的方法
				Method[] methodArray = forName.getDeclaredMethods();
				for (Method method : methodArray) {
					// 初始化 实现了AuthIgnore注解的方法
					if (method.isAnnotationPresent(AuthIgnore.class)) {
						// 获取RequestMapping的属性
						String postfix = getMappingValue(method);
						// 完整的uri
						String uri = uriPrefix + postfix;
						// 保存无需登录的uri
						boolean isLogin = method.getAnnotation(AuthIgnore.class).login();
						if (!isLogin && !noLoginUris.contains(uri)) {
							noLoginUris.add(uri);
						}
						// 保存无需权限验证的uri
						if (!noAuthUris.contains(uri)) {
							if (uri.contains("{")) {
								uri = uri.substring(0, uri.indexOf("{"));
								String[] values = method.getAnnotation(AuthIgnore.class).value();
								if (values != null && values.length > 0) {
									for (String value : values) {
										noAuthUris.add(uri + value);
									}
								} else {
									noAuthUris.add(uri);
								}
							} else {
								noAuthUris.add(uri);
							}
						}
					}
				}
			}
		});
	}

	/**
	 * 获取Mapping的value属性
	 * @param method
	 * @return
	 */
	private static String getMappingValue(Method method) {
		RequestMapping reqMaping = method.getAnnotation(RequestMapping.class);
		if(reqMaping != null) {
			return reqMaping.value()[0];
		}
		GetMapping getMaping = method.getAnnotation(GetMapping.class);
		if(getMaping != null) {
			return getMaping.value()[0];
		}
		PostMapping postMaping = method.getAnnotation(PostMapping.class);
		if(postMaping != null) {
			return postMaping.value()[0];
		}
		PutMapping putMaping = method.getAnnotation(PutMapping.class);
		if(putMaping != null) {
			return putMaping.value()[0];
		}
		DeleteMapping delMaping = method.getAnnotation(DeleteMapping.class);
		if(delMaping != null) {
			return delMaping.value()[0];
		}
		return null;
	}

	/**
	 * 获取 使用不需要权限拦截的uri
	 *
	 * @return
	 */
	public List<String> getNoAuthUris() {

		return noAuthUris;
	}

	/**
	 * 获取 使用不需要登录拦截的uri
	 *
	 * @return
	 */
	public List<String> getNoLoginUris() {

		return noLoginUris;
	}

}
