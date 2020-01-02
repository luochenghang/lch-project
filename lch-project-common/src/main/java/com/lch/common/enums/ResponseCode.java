package com.lch.common.enums;

/**
 * @ClassName: RequestCode
 * @Description: 状态码接口
 * @date 2019年12月10日 下午4:46:06
 */
public enum ResponseCode {

	/**
	 * 1000 请求结果成功
	 */
	SUCCEED(1000, "请求成功"),

	/**
	 * 2000 请求结果失败
	 */
	FAILED(2000, "请求失败"),

	/**
	 * 3000 未登录
	 */
	NOLOGIN(3000, "未登录"),

	/**
	 * 4000 无操作权限
	 */
	NOAUTHORITY(4000, "无操作权限"),
	/**
	 * 4001 无操作权限
	 */
	NOACCESS(4001, "无访问权限"),

	OTHER_ERROR(5000, "其他错误");

	private int code;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	private ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
