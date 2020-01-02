package com.lch.common.base;

import com.lch.common.config.AjaxResponse;
import com.lch.common.enums.ResponseCode;

/**
 * 返回信息对象
 *  @date 2019年12月10日 下午3:23:56
 */
public abstract class ReturnInfo {
	
	// 响应成功
	protected AjaxResponse succees(Object obj, String msg) {
		return AjaxResponse.toReturn(ResponseCode.SUCCEED, obj, msg);
	}
	
	// 响应成功
	protected AjaxResponse succees(Object obj) {
		return AjaxResponse.toReturn(ResponseCode.SUCCEED, obj);
	}

	// 响应成功
	protected AjaxResponse succees() {
		return AjaxResponse.toReturn(ResponseCode.SUCCEED);
	}
	
	// 响应失败
	protected AjaxResponse fail(Object obj, String msg) {
		return AjaxResponse.toReturn(ResponseCode.FAILED, obj, msg);
	}
	
	// 响应失败
	protected AjaxResponse fail(String msg) {
		return AjaxResponse.toReturn(ResponseCode.FAILED, null, msg);
	}
	
	// 响应失败
	protected AjaxResponse fail() {
		return AjaxResponse.toReturn(ResponseCode.FAILED);
	}
	
}
