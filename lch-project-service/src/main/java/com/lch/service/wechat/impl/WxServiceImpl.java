package com.lch.service.wechat.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lch.component.wechat.wxpublic.entity.ImageMessage;
import com.lch.service.wechat.WxService;

@Service
public class WxServiceImpl implements WxService {

	// 用于区别小程序客服按钮的特殊参数
	public static final String USER_ENTER_TEMPSESSION = "USER_ENTER_TEMPSESSION";

	// 客服消息推送地址
	public final static String kf_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";


	@Override
	public String sendKfMessage(String openid, String text, String access_token) throws Exception {
		Map<String, Object> map_content = new HashMap<>();
		map_content.put("content", text);
		Map<String, Object> map = new HashMap<>();
		map.put("touser", openid);
		map.put("msgtype", "text");
		map.put("text", map_content);
		String content = JSONUtils.toJSONString(map);
		JSONObject json = JSONObject.parseObject(content);
		JSONObject resultJson = customSend(json, access_token);
		String str = resultJson.getString("errmsg");
		return str;
	}

	@Override
	public boolean sendImage(ImageMessage imageMessage, String access_token) {
		Map<String, Object> map_image = new HashMap<>();
		map_image.put("media_id", imageMessage.getImage().getMediaId());
		Map<String, Object> image_message = new HashMap<>();
		image_message.put("touser", imageMessage.getToUserName());
		image_message.put("image", map_image);
		image_message.put("msgtype", imageMessage.getMsgType());
		String content = JSONUtils.toJSONString(image_message);
		JSONObject json = JSONObject.parseObject(content);
		customSend(json, access_token);
		return true;
	}

	@Override
	public JSONObject customSend(JSONObject json, String accessToken) {
		JSONObject jsonObject = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(json.toString());
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			jsonObject = JSON.parseObject(result.toString());
			System.out.println("客服消息result：" + jsonObject);
		} catch (Exception e) {
			System.out.println("向客服发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return jsonObject;
	}

}
