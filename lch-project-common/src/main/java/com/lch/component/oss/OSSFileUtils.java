package com.lch.component.oss;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lch.common.constant.OSS;
import com.lch.utils.DateUtils;
import com.lch.utils.FileUtils;
import com.lch.utils.IdGen;
import com.lch.utils.StringUtils;

import net.sf.json.JSONObject;

public class OSSFileUtils {

	/**
	 * 批量获取文件访问url
	 *
	 * @param keys
	 * @return
	 */
	public static String batchFileUrl(String... keys) {

		return Stream.of(keys).map(key -> getFileUrl(key)).filter(x -> StringUtils.isNotBlank(x)).collect(Collectors.joining(","));
	}

	/**
	 * 批量获取视频截帧url
	 *
	 * @param keys
	 * @return
	 */
	public static String batchInterceptionUrl(String... keys) {

		return Stream.of(keys).map(key -> getFileInterceptionUrl(key)).filter(x -> StringUtils.isNotBlank(x)).collect(Collectors.joining(","));
	}

	/**
	 * 根据key获取文件访问url
	 * @param key
	 * @return
	 */
	public static String getFileUrl(String key) {

		return StringUtils.isNotBlank(key) ? (OSS.PREFIX + key) : null;
	}

	/**
	 * 获取视频第一帧截图url
	 * @param key
	 * @return
	 */
	public static String getFileInterceptionUrl(String key) {

		return FileUtils.isVideo(key) ? (getFileUrl(key) + OSS.VIDEO_SCREENSHOT_PARAMS) : null;
	}

	/**
	 *  文件上传前批量获取OSS的签名
	 * @param folderName
	 * @param fileNames 文件名 多个用逗号隔开
	 * @return
	 */
	public static List<JSONObject> batchSignatureForUpload(String folderName, String fileNames){
		List<JSONObject> list = Lists.newArrayList();
		if(StringUtils.isNotBlank(fileNames)) {
			Stream.of(fileNames.split(",")).forEach(name -> list.add(getSignatureForUpload(folderName, name)));
		}
		return list;
	}

	/**
	 * 文件上传前获取OSS的签名
	 *
	 * @param folderName
	 * @param fileName
	 * @return
	 */
	public static JSONObject getSignatureForUpload(String folderName, String fileName) {
		OSSClient client = new OSSClient(OSS.ENDPOINT, OSS.ACCESS_ID, OSS.ACCESS_SECRET);
		Map<String, String> respMap = Maps.newLinkedHashMap();
		try {
			Long expireEndTime = System.currentTimeMillis() + OSS.EXPIRE;// 过期时间
			fileName = URLDecoder.decode(fileName, "UTF-8");// 文件名编码
			String key = getFileKey(folderName, fileName); // 上传到OSS的key

			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, key);

			String postPolicy = client.generatePostPolicy(new Date(expireEndTime), policyConds);

			String encodedPolicy = BinaryUtil.toBase64String(postPolicy.getBytes("utf-8"));
			String postSignature = client.calculatePostSignature(postPolicy);

			respMap.put("OSSAccessKeyId", OSS.ACCESS_ID);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("key", key);
			respMap.put("host", OSS.PREFIX);
			respMap.put("showKey", key);
			respMap.put("url", OSS.PREFIX + key);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));
			respMap.put("success_action_status", "200");// 让服务端返回200,不然，默认会返回204
			respMap.put("fileName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.shutdown();
		}
		return JSONObject.fromObject(respMap);
	}

	/**
	 * 文件是否存在
	 *
	 * @param filePath   文件完整路径 如:myfile/log/mylog.log
	 * @return true(存在),false(不存在)
	 */
	public static boolean isExists(String filePath) {
		OSSClient ossClient = new OSSClient(OSS.ENDPOINT, OSS.ACCESS_ID, OSS.ACCESS_SECRET);
		boolean found = false;
		try {
			found = ossClient.doesObjectExist(OSS.BUCKET_NAME, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		return found;
	}

	/**
	 * 根据key删除OSS文件
	 *
	 * @param key
	 * @throws IOException
	 */
	public static void deleteFile(String key) {
		OSSClient client = new OSSClient(OSS.ENDPOINT, OSS.ACCESS_ID, OSS.ACCESS_SECRET);
		try {
			client.deleteObject(OSS.BUCKET_NAME, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.shutdown();
		}
	}

	/**
	 * 生成文件key(重置文件名)
	 *
	 * @param folderName 	上传到oss文件夹名称
	 * @param fileName     	文件名称
	 * @return
	 */
	public static String getFileKey(String folderName, String fileName) {
		return folderName + "/" + DateUtils.formatDate(new Date(), "yyyy/MM/dd")+ "/" + IdGen.uuid() + "." + FileUtils.getFileExtension(fileName);
	}

	/**
	 * 生成文件key(原文件名)
	 *
	 * @param fileFolderName 上传到oss文件夹名称
	 * @param fileName       文件名称
	 * @return
	 */
	public static String getFileKeyWithOriginName(String fileFolderName, String fileName) {
		return fileFolderName + "/" + DateUtils.formatDate(new Date(), "yyyy/MM/dd") + "/" + fileName;
	}

}
