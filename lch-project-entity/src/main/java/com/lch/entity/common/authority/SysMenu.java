package com.lch.entity.common.authority;

import java.util.List;

import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends DataEntity<SysMenu> {

	private static final long serialVersionUID = 1330842297103651794L;

	//(example = "菜单标题", value = "菜单标题", required = true)
	private String name;

	//(example = "图标", value = "图标", required = true)
	private String icon;

	//(example = "父菜单(0为根目录)", value = "父菜单(0为根目录)", required = true)
	private Short pid;

	//(example = "排序", value = "排序", required = true)
	private Short sort;

	//(example = "状态(1:启用 0:禁用)", value = "状态(1:启用 0:禁用)", required = true)
	private Short status;

	//(example = "菜单类型(0按钮权限 1查看权限 2按钮权限+查看权限)", value = "菜单类型(0按钮权限 1查看权限 2按钮权限+查看权限)", required = true)
	private int menuType;

	//(example = "路由根路径(前端用)", value = "路由根路径(前端用)", required = true)
	private String path;

	//(example = "菜单名(前端用)", value = "菜单名(前端用)", required = true)
	private String menuName;

	//(example = "组件路径(前端用)", value = "组件路径(前端用)", required = true)
	private String component;
	
	private List<SysMenu> menus;
}