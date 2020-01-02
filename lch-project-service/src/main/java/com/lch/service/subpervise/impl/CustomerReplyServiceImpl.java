package com.lch.service.subpervise.impl;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lch.cache.redis.RedisUtil;
import com.lch.common.constant.App;
import com.lch.component.wechat.wxpublic.WeixinUtil;
import com.lch.component.wechat.wxpublic.entity.Image;
import com.lch.component.wechat.wxpublic.entity.ImageMessage;
import com.lch.component.wechat.wxpublic.entity.TextMessage;
import com.lch.service.common.WeChatService;
import com.lch.service.subpervise.CustomerReplyService;
import com.lch.utils.ConfigUtils;
import com.lch.utils.MapUtils;
import com.lch.utils.StringUtils;

import com.lch.service.wechat.WxService;

@Service
public class CustomerReplyServiceImpl implements CustomerReplyService {

	@Autowired
	private WeChatService weChatService;

	@Autowired
	private WxService wxService;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public String replyMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取监督appid
		String access_token = weChatService.getAccessToken(App.SUPERVISE_APPID);

		Map<String, String> map = WeixinUtil.xmlToMap(request);

		// 用于回复而封装的消息
		TextMessage message = new TextMessage();

		message.setFromUserName(MapUtils.getString(map, "ToUserName"));// 公众账号
		message.setToUserName(MapUtils.getString(map, "FromUserName"));// 发送方账号

		String content = "您好,欢迎进入监督者世界,加入社群一起开车吧!";
		message.setContent(content);
		message.setMsgType("text");// 消息类型
		message.setCreateTime(new Date().getTime() + "");

		// 消息类型为image
		String type = WeixinUtil.REQ_MESSAGE_TYPE_IMAGE;
		// 获得缓存里面的media_id
		String media_id = (String) redisUtil.get("lch_supervise_kf_media_id");
		if (StringUtils.isEmpty(media_id)) {
			// 本地文件/服务器文件路径
			String picUrl = ConfigUtils.getInstance().getConfigValue("lch_supervise_kf_pic");
			// 阿里云获取客服图片地址，将其转化为file文件类型
			File file = WeixinUtil.getFileByUrl(picUrl);
			// 获取微信小程序的media_id
			media_id = WeixinUtil.upload(file, access_token, type);
			// 设置缓存有效时间，不到3天（官方有效期默认3天）
			redisUtil.set("lch_supervise_kf_media_id", media_id, 86400);
		}

		Image image = new Image();
		image.setMediaId(media_id);
		// 用于图片回复而封装的消息
		ImageMessage imageMessage = new ImageMessage(MapUtils.getString(map, "FromUserName"),
				MapUtils.getString(map, "ToUserName"), new Date().getTime() + "", type, image);

		String MsgType = MapUtils.getString(map, "MsgType");
		String result = "";
		if (MsgType.equals(WeixinUtil.REQ_MESSAGE_TYPE_TEXT) && MapUtils.getString(map, "Content").equals("888")) {
			// 发送图片
			boolean flag = wxService.sendImage(imageMessage, access_token);
			if (flag) {// 发送文本
				result = wxService.sendKfMessage(message.getToUserName(), message.getContent(), access_token);
			}
		}

		return result;
	}

}
