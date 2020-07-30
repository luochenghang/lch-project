package com.lch.entity.lamp;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.common.base.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ShopInfo extends DataEntity<ShopInfo> {

	private static final long serialVersionUID = 1L;

	private String address;

	private String phone;

	private String shopName;// 创建时间

	private String email;// 跳转路径

	private String contactName;// 联系人

	private double lng; //经度

	private double lat; //纬度


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; // 更新时间
	
	//主页数据
	private int shopUserNum;
	private int OrderNum;
	private int sellAllGoodsNum;
	private int earnAllPrice;
	
	private int onShopNum;
	private int offShopNum;
	private int allShopNum;
	private int wechatUserNum;
	private int offlineUserNum;
	private int allUserNum;
	private int userNoReadCollectNum;
	
	
}
