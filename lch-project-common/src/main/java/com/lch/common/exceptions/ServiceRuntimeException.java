package com.lch.common.exceptions;

import com.lch.common.enums.ResponseCode;

/**
 * 自定义运行时异常处理类
 * @ClassName: ServiceRuntimeException 
 * @author Jun
 * @date 2018年10月25日 下午3:33:06 
 *
 */
public class ServiceRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -1708015121235851228L;

	private Integer code;

	public ServiceRuntimeException(String message) {
		super(message);
	}

	public ServiceRuntimeException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.code = responseCode.getCode();
	}
	
	public ServiceRuntimeException(String message, Integer code) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
