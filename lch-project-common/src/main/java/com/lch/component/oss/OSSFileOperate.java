package com.lch.component.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.lch.common.constant.OSS;
import com.lch.utils.FileUtils;
import com.lch.utils.StringUtils;

/**
 * 阿里云OSS文件操作
 *
 * @author Jun
 * @date 2018年11月14日 上午9:40:41
 */
public class OSSFileOperate {

	/**
	 * 根据文件的URL上传文件到OSS
	 *
	 * @param url
	 * @param fileFolderName
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String fileUploadByUrl(String url, String fileFolderName) throws Exception {
		if(StringUtils.isNotBlank(url) && url.startsWith("http:")) {
			url = url.replaceFirst("http", "https");
		}
		return uploadInputStream(new URL(url).openStream(), fileFolderName, FileUtils.getNameWithExtension(url));
	}

	/**
	 * 直接将文件上传到OSS
	 *
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static String fileUploadByFile(File file, String fileFolderName) throws Exception {
		return uploadInputStream(new FileInputStream(file), fileFolderName, file.getName());
	}

	/**
	 * 直接将文件上传到OSS
	 *
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static String fileUpload(File file, String key) throws Exception {
		return uploadInputStream(new FileInputStream(file), key);
	}

	/**
	 * 上传文件流到oss
	 *
	 * @param is
	 * @param fileFolderName
	 * @param fileName 文件名带后缀 如"xxx.jpg"
	 * @return
	 * @throws IOException
	 */
	public static String uploadInputStream(InputStream inputStream, String fileFolderName, String fileName) {
		return uploadInputStream(inputStream, OSSFileUtils.getFileKey(fileFolderName, fileName));
	}

	/**
	 * 上传文件流到oss 不改文件名
	 *
	 * @param is
	 * @param fileFolderName
	 * @param fileName 文件名带后缀 如"xxx.jpg"
	 * @return
	 * @throws IOException
	 */
	public static String uploadInputStreamWithOriginFileName(InputStream inputStream, String fileFolderName, String fileName) {
		return uploadInputStream(inputStream, OSSFileUtils.getFileKeyWithOriginName(fileFolderName, fileName));
	}

	/**
	 * 上传文件流到oss
	 * @param inputStream
	 * @param key
	 * @return
	 */
	public static String uploadInputStream(InputStream inputStream, String key) {
		// 创建OSSClient实例
		OSSClient client = new OSSClient(OSS.ENDPOINT, OSS.ACCESS_ID, OSS.ACCESS_SECRET);
		try {
			client.putObject(OSS.BUCKET_NAME, key, inputStream);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			client.shutdown();
		}
		return key;
	}

	/**
	 * 从OSS下载文件
	 *
	 * @param response
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String downloadFile(HttpServletResponse response, String key) throws IOException {
		InputStream in = null;
		OutputStream os = null;
		OSSClient client = null;
		try {
			if (key.indexOf("?") != -1) key = StringUtils.substringBefore(key, "?");
			if (key.indexOf("/") == 0) key = StringUtils.substringAfter(key, "/");
			// 创建OSSClient实例
			client = new OSSClient(OSS.ENDPOINT, OSS.ACCESS_ID, OSS.ACCESS_SECRET);
			OSSObject ossObject = client.getObject(OSS.BUCKET_NAME, key);
			// 读Object内容
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + key.substring(key.lastIndexOf(".")));
			in = ossObject.getObjectContent();
			os = response.getOutputStream();
			int n = 0;
			byte[] buffer = new byte[1024];
			while ((n = in.read(buffer)) != -1) {
				os.write(buffer, 0, n);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (in != null) {
				in.close();
			}
			if (os != null) {
				os.close();
			}
			if (client != null) {
				client.shutdown();
			}
		}
		return key;
	}

}
