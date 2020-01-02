package com.lch.component.wechat.wxpublic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lch.component.wechat.wxpublic.entity.ImageMessage;
import com.lch.component.wechat.wxpublic.entity.TextMessage;
import com.lch.utils.ConfigUtils;
import com.thoughtworks.xstream.XStream;

/**
 * 类名: WeixinUtil </br>
 * 包名： com.souvc.weixin.util 描述: 公众平台通用接口工具类 </br>
 * 开发人员： souvc </br>
 * 创建时间： 2015-12-1 </br>
 * 发布版本：V1.0 </br>
 */
public class WeixinUtil {

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 微信JSSDK的ticket请求URL地址——jsapi_ticket
	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 获取用户基本信息（包括UnionID机制）
	public static String get_user_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	// 生成带参数的二维码(扫码充电功能)
	public static String qr_scene = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	private static String appId = ConfigUtils.getInstance().getConfigValue("official.accounts.bpb_gg.app_id");

	private static String appSecret = ConfigUtils.getInstance().getConfigValue("official.accounts.app_secret");

	// 用于小程序上传临时图片素材到微信服务端所请求的url地址
	public static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	// 用于微信公众号上传图文永久素材到微信服务器所请求的url地址
	public static final String PUBLIC_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

	// 公众号回复小程序
	public static final String PUSH_MINIPROGRAM = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	// 微信公众号删除永久素材media
	public static final String DELETE_PUBLIC_MEDIAID = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：扫码
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";



	/**
	 * 获取access_token 目前access_token的有效期是7200秒之内的值
	 *
	 * @return
	 */
	public static String getAccessToken() {
		String tokenURL = access_token_url.replace("APPID", appId).replaceAll("APPSECRET", appSecret);
		String result = getHttpsResponse(tokenURL, "GET", null);
		JSONObject json = JSON.parseObject(result);
		if (json.containsKey("errcode")) {
			log.error("获取公众号access_token无效，errcode：{}，errmsg：{}", json.getString("errcode"), json.getString("errmsg"));
			return null;
		}
		return json.getString("access_token");
	}

	/**
	 * 调用微信JS接口的临时票据
	 *
	 * @param access_token 接口访问凭证
	 * @return api_ticket 是用于调用微信卡券JS API的临时票据，有效期为7200 秒，通过access_token 来获取
	 */
	public static String getJsApiTicket(String access_token) {
		String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
		// 发起GET请求获取凭证
		String tokenURL = getHttpsResponse(requestUrl, "GET", null);
		JSONObject json = JSON.parseObject(tokenURL);
		if (null != json.get("ticket")) {
			return json.get("ticket").toString();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static String getHttpsResponse(String hsUrl, String requestMethod, String str) {
		URL url;
		InputStream is = null;
		String resultData = "";
		try {
			url = new URL(hsUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			TrustManager[] tm = { xtm };

			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);

			con.setSSLSocketFactory(ctx.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});

			con.setDoInput(true); // 允许输入流，即允许下载

			// 在android中必须将此项设置为false
			con.setDoOutput(true); // 允许输出流，即允许上传
			con.setUseCaches(true); // 不使用缓冲

			// 设置请求方式（GET/POST）
			con.setRequestMethod(requestMethod);
			con.setConnectTimeout(3000);
			con.setReadTimeout(3000);
			con.connect();

			is = con.getInputStream(); // 获取输入流，此时才真正建立链接
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine = "";

			// 当有数据需要提交时
			if (null != str) {
				OutputStream outputStream = con.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(str.getBytes("UTF-8"));
				outputStream.close();
			}

			while ((inputLine = bufferReader.readLine()) != null) {
				resultData += inputLine + "\n";
			}
			System.out.println(resultData);
			Certificate[] certs = con.getServerCertificates();
			for (Certificate cert : certs) {
				X509Certificate xcert = (X509Certificate) cert;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}


	static X509TrustManager xtm = new X509TrustManager() {

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}
	};

	/**
	 * xml转换为map
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = null;
		try {
			ins = request.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Document doc = null;
		try {
			doc = reader.read(ins);
			Element root = doc.getRootElement();

			List<Element> list = root.elements();

			for (Element e : list) {
				map.put(e.getName(), e.getText());
			}

			return map;
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} finally {
			ins.close();
		}

		return null;
	}

	/**
	 * 文本消息对象转换成xml
	 *
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * @Title: imageMessageToXml @Description: image消息对象转换成xml @param @param
	 *         imageMessage @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {

		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);

	}

	/**
	 * @Title: sign @Description: 获得js页面授权 @param @param jsapi_ticket @param @param
	 *         url @param @return 设定文件 @return Map<String,String> 返回类型 @throws
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("appId", ConfigUtils.getInstance().getConfigValue("official.accounts.bpb_gg.app_id"));
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 获取OSS文件转换为File地址
	 *
	 * @param fileUrl
	 * @return
	 */
	public static File getFileByUrl(String fileUrl) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		BufferedOutputStream stream = null;
		InputStream inputStream = null;
		File file = null;
		String suffix = "";
		try {
			suffix = fileUrl.split("\\.")[1];
			URL imageUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			inputStream = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			file = File.createTempFile("pattern", "." + suffix + ".png");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fileOutputStream);
			stream.write(outStream.toByteArray());
		} catch (Exception e) {
			System.out.println("异常:" + e);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (stream != null)
					stream.close();
				outStream.close();
			} catch (Exception e) {
				System.out.println("关闭流异常" + e);
			}
		}
		return file;
	}

	/**
	 * 小程序上传本地文件到微信获取mediaId
	 */
	public static String upload(File file, String accessToken, String type) throws Exception {
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URL urlobj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);

		// 设置头信息
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNFARY = "----------" + System.currentTimeMillis();
		conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNFARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNFARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:from-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/actet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(conn.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件以流的方式 推入到url
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNFARY + "--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		if (result == null) {
			result = buffer.toString();
		}
		if (result != null) {
			reader.close();
		}
		JSONObject jsonObject = JSONObject.parseObject(result);
		System.out.println(jsonObject);
		String typeName = "media_id";
		if (!"image".equals(type) && !"voice".equals(type) && !"video".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaid = jsonObject.getString(typeName);
		return mediaid;
	}

	/**
	 * 微信公众号上传本地文件到微信获取mediaId
	 */
	public static String publicPicUpload(File file, String accessToken, String type) throws Exception {
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		String url = PUBLIC_UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URL urlobj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);

		// 设置头信息
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNFARY = "----------" + System.currentTimeMillis();
		conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNFARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNFARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:from-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(conn.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件以流的方式 推入到url
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNFARY + "--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		if (result == null) {
			result = buffer.toString();
		}
		if (result != null) {
			reader.close();
		}
		JSONObject jsonObject = JSONObject.parseObject(result);
		log.error(jsonObject.toJSONString());
		String typeName = "media_id";
		if (!"image".equals(type) && !"voice".equals(type) && !"video".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaid = jsonObject.getString(typeName);
		return mediaid;
	}
}