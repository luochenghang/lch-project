package com.lch.common.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lch.common.config.AjaxResponse;
import com.lch.common.enums.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public AjaxResponse Handle(Exception e) {
		if (e instanceof ServiceRuntimeException) {
//			log.error("[自定义运行时异常]##################", e);
			return AjaxResponse.toReturn(ResponseCode.FAILED, "出现运行时异常,请联系管理员");

		} else if (e instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) e;
//			log.error("[自定义异常]##################", e);
			return AjaxResponse.toReturn(ResponseCode.FAILED, serviceException.getMessage());
		} else {
			e.printStackTrace();
			log.error("[系统异常]##################", e);
			return AjaxResponse.toReturn(ResponseCode.FAILED, "请求发生错误,请重试");
		}
	}

}