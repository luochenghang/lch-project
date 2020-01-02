package com.lch.component.page;

import java.util.List;

import com.github.pagehelper.PageInfo;

import lombok.Data;

/**
 * PageInfo封装类，由于PageInfo返回的参数过多，通过封装减少不必要的参数响应
 * @author Jun
 * @date 2018年10月30日 下午3:59:28
 * @param <T>
 */
@Data
public class Page<T> {

	//@ApiModelProperty(example = "当前页", value = "当前页", required = true)
	private int curretnPage;

	//@ApiModelProperty(example = "页大小", value = "页大小", required = true)
	private long pageCount;

	//@ApiModelProperty(example = "总条数", value = "总条数", required = true)
	private long totalCount;

	//@ApiModelProperty(example = "总页数", value = "总页数", required = true)
	private int totalPage;

	//@ApiModelProperty(example = "返回的内容", value = "返回的内容", required = true)
	private List<T> list;

	public Page() {
		super();
	}

	public Page(PageInfo<T> page) {
		if(page != null) {
			this.curretnPage = page.getPageNum();
			this.pageCount = page.getPageSize();
			this.totalCount = page.getTotal();
			this.totalPage = page.getPages();
			this.list = page.getList();
		}
	}


}
