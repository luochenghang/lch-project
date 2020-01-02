package com.lch.component.annotation.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 无需进行权限过滤的自定义注解
 * @Author Jun
 * @date 2019年5月14日 下午3:08:51
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthIgnore {

	/** 描述业务操作 **/
	String desc() default "";
	
	/** 业务路径 **/
	String[] value() default {};
	
	/** 是否要登录 **/
	boolean login() default true;
	
}
