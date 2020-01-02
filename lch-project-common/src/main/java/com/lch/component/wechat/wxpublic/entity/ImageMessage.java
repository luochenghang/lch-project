package com.lch.component.wechat.wxpublic.entity;

import lombok.Data;

@Data
public class ImageMessage {

	private String ToUserName;

	private String FromUserName;

	private String CreateTime;

	private String MsgType;

	private Image Image;

	public ImageMessage(String toUserName, String fromUserName, String createTime, String msgType,
			Image image) {
		super();
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
		Image = image;
	}

	public ImageMessage() {
		super();
	}
	
	

}
