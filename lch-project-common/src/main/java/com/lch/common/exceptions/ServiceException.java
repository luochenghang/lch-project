package com.lch.common.exceptions;

/**
 * 自定义异常处理类
 * @ClassName: ServiceException 
 * @author Jun
 * @date 2018年10月25日 下午3:32:47 
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -1708015121235851228L;

	private String code;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
