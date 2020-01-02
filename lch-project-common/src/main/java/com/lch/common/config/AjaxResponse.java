package com.lch.common.config;

import com.lch.common.enums.ResponseCode;


/**
 * @ClassName: AjaxResponse
 * @Description: 统一响应组装对象类
 * @date  @date 2019年12月10日 下午3:23:56
 */
public class AjaxResponse {

	//@ApiModelProperty(example = "响应的具体内容", value = "响应的具体内容", required = true)
	private Object data;

	//@ApiModelProperty(example = "响应的编码", value = "响应的编码", required = true)
	private int code;

	//@ApiModelProperty(example = "响应的附加消息", value = "响应的附加消息", required = true)
	private String msg;

	private AjaxResponse(int code, Object data, String msg) {
		this.code = code;
		this.data = data==null?"":data;
		this.msg = msg==null?"":msg;

	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static AjaxResponse toReturn(ResponseCode requestCode, Object data, String msg) {
		return new AjaxResponse(requestCode.getCode(), data, msg);
	}
	
	public static AjaxResponse toReturn(ResponseCode requestCode, String msg) {
		return new AjaxResponse(requestCode.getCode(), null, msg);
	}
	
	public static AjaxResponse toReturn(ResponseCode requestCode, Object data) {
		return new AjaxResponse(requestCode.getCode(), data, null);
	}
	
	public static AjaxResponse toReturn(ResponseCode requestCode) {
		return new AjaxResponse(requestCode.getCode(), null, null);
	}
}
 
