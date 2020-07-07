package com.lch.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.lch.common.constant.App;

/**
 * 文件操作工具类
 * 
 * @date 2019年6月4日 下午3:00:03
 */
public class FileUtils {

	private static final String[] VIDEO_SUFFIX = { "WMV", "ASF", "ASX", "RM", "RMVB", "MP4", "3GP", "MOV", "M4V", "AVI",
			"DAT", "MKV", "FIV", "VOB" };

	private static final String[] PIC_SUFFIX = { "BMP", "GIF", "JPEG", "TIFF", "PNG", "SVG", "PCX", "WMF", "EMF", "LIC",
			"EPS", "TGA", "JPG" };

	/**
	 * 获取网络文件大小，单位：k
	 * 
	 * @param fileUrl
	 * @return
	 * @throws IOException
	 */
	public static double getNetFileSize(String path) {
		double size = 0D;
		try {
			URL url = new URL(path);
			URLConnection conn = url.openConnection();
			size = (double) conn.getContentLength();
			conn.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			return size;
		}
		return size / 1024;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 */
	public static void delFile(String path) {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 获取带扩展名的文件名称
	 * 
	 * @param path
	 * @return
	 */
	public static String getNameWithExtension(String path) {
		return Files.getNameWithoutExtension(path) + '.' + getFileExtension(path);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileExtension(String path) {
		String ext = Files.getFileExtension(path);
		int i = ext.indexOf("?");
		if (i > -1) {
			ext = ext.substring(0, ext.indexOf("?"));
		}
		return ext;
	}

	/**
	 * 判断是否是视频
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isVideo(String path) {
		return StringUtils.isNotBlank(path)
				&& Lists.newArrayList(VIDEO_SUFFIX).contains(getFileExtension(path).toUpperCase());
	}

	/**
	 * 判断是否是图片
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isPicture(String path) {
		return StringUtils.isNotBlank(path)
				&& Lists.newArrayList(PIC_SUFFIX).contains(getFileExtension(path).toUpperCase());
	}

	/**
	 * 文件保存
	 * 
	 * @param file
	 * @param path
	 */
	public static String save(MultipartFile file, String floder, String fileName) {
		String filePath = getFilePath(floder, fileName);
		File newFile = new File(App.BASE_PATH + filePath);
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	/**
	 * 获取文件保存路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePath(String floder, String fileName) {
		File file = new File(App.BASE_PATH + floder);
		if (!file.exists()) {
			file.mkdirs();
		}
		return floder + "/" + IdGen.uuid() + "." + getFileExtension(fileName);
	}

}
