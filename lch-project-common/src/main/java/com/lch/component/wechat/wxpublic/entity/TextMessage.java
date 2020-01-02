package com.lch.component.wechat.wxpublic.entity;

import lombok.Data;

@Data
public class TextMessage {

	private String Content;

	private String ToUserName;

	private String FromUserName;

	private String CreateTime;

	private String MsgType;
}
