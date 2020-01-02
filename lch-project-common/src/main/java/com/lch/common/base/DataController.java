package com.lch.common.base;

import org.springframework.beans.factory.annotation.Autowired;

public class DataController<T> extends BaseController {

	@Autowired
	protected T service;

}
