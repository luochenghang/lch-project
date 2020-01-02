package com.lch.component.page;

import com.github.pagehelper.PageInfo;

/**
 * 分页适配器
 * @Author Jun
 * @date 2019年8月8日 下午3:55:13
 */
public class PageAdapter {

	/**
	 * 适配方法
	 * @param page
	 * @return
	 */
	public static <T> Page<T> adpater(PageInfo<T> pi){

		Page<T> page = new Page<>();

		page.setCurretnPage(pi.getPageNum());
		page.setPageCount(pi.getPageSize());
		page.setTotalCount(pi.getTotal());
		page.setTotalPage(pi.getPages());
		page.setList(pi.getList());

		return page;
	}

}
