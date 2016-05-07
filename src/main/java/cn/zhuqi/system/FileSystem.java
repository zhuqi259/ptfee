package cn.zhuqi.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件服务器操作工具类 负责与文件服务器通信，并进行上传、下载等操作
 * 
 * @author zhuqi
 * 
 */
public class FileSystem {

	// 加载文件服务器相关配置信息

	private static InputStream in = FileSystem.class.getClassLoader()
			.getResourceAsStream("fileSystem.properties");
	private static Properties p = new Properties();

	// 服务器的url前缀(ip与端口)
	// private static String path = "http://localhost:"+
	// ServletActionContext.getRequest().getLocalPort();
	private static String path = getDomainName();

	// 获取文件服务器域名

	private static String getDomainName() {
		String root = null;
		try {
			p.load(in);
			// 获取配置里的文件服务器域名
			root = p.getProperty("file_domain_name");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 当配置文件没有配置域名信息时，动态获取当前容器的域名
		if (StringUtils.isEmpty(root)) {
			root = "http://localhost:"
					+ ServletActionContext.getRequest().getLocalPort();
		}
		return root;
	}

	// 获取文件服务器文件存放的路径
	public static String getFileSystemFileRootPath() throws Exception {
		p.load(in);
		String root = path + p.getProperty("file_root_path");
		return root;
	}

	// 获取文件服务器文件上传servlet的路径

	public static String getFileSystemUploadServletPath() throws Exception {
		p.load(in);
		String root = path + p.getProperty("upload_servlet");
		return root;
	}

	// 获取文件服务器文件下载servlet的路径

	public static String getFileSystemDownloadServletPath() throws Exception {
		p.load(in);
		String root = path + p.getProperty("download_servlet");
		return root;
	}

	/**
	 * 获取文件后缀
	 * 
	 * 从文件名中获取文件后缀，获取后的后缀形式形如：“.txt”、“.jpg”等，并且后缀全部转换为小写字母。<br/>
	 * 另外：如果文件没有后缀名，则返回一个空字符串；如果文件名为空，则返回null。
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * 
	 * @return postfix 文件后缀
	 */
	public static String getFilePostfix(String fileName) {
		String postfix = null;
		if (StringUtils.isEmpty(fileName)) {
			return postfix;
		}
		fileName = fileName.trim();
		int pos = fileName.lastIndexOf(".");
		if (pos == -1) {
			postfix = "";
			return "";
		} else {
			postfix = fileName.substring(pos).toLowerCase();
		}
		return postfix;
	}

	// 生成指定长度的随机字符串
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
				'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
				'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt())
					% randomChar.length]);
		}
		return stringBuffer.toString();
	}

	// 重命名并返回Map(上传文件的完整相对路径和url字符串)
	private static Map<String, String> reNameAndReturnFilePath(String fileType)
			throws Exception {

		// 上传文件的子文件夹，默认为当前日期的子文件夹
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String savePath = "/" + sdf.format(new Date());

		// 重命名上传的文件
		String newFileName = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS")
				.format(new Date()) + "_" + getRandomString(5) + fileType;

		// 上传文件的完整相对路径

		String filePath = savePath + "/" + newFileName;

		String url = getFileSystemUploadServletPath() + "?savePath=" + savePath
				+ "&newFileName=" + newFileName;

		Map<String, String> map = new HashMap<String, String>();
		map.put("filePath", filePath);
		map.put("url", url);

		return map;
	}

	// 文件服务器通信协议
	private static HttpURLConnection getUrlConnection(String url)
			throws Exception {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(10000);
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setChunkedStreamingMode(64);
		conn.connect();

		return conn;
	}

	// 关闭资源
	private static void close(InputStream is, OutputStream os,
			HttpURLConnection conn) throws Exception {
		if (null != is) {
			is.close();
		}
		if (null != os) {
			os.close();
		}
		if (null != conn) {
			conn.disconnect();
		}
	}

	// 调用文件服务器进行上传文件操作成功，则返回文件的相对路径
	private static String getResult(HttpURLConnection conn, String filePath)
			throws Exception {
		if (conn.getResponseCode() == HttpServletResponse.SC_OK) {
			return filePath;
		} else {
			return null;
		}
	}

	/**
	 * 文件上传 已封装文件重命名逻辑
	 * 
	 * @param upload
	 *            要上传的文件
	 * @param fileType
	 *            文件类型，如 .txt .jpg
	 * @return 上传成功则返回文件保存的相对路径，否则为null
	 * @throws Exception
	 */
	public static String uploadFile(File upload, String fileType)
			throws Exception {
		InputStream is = null;
		OutputStream os = null;
		HttpURLConnection conn = null;
		Map<String, String> map = null;
		try {
			map = reNameAndReturnFilePath(fileType);
			// *********与文件服务器通信，并传递数据********//
			conn = getUrlConnection(map.get("url"));
			os = conn.getOutputStream();
			is = new BufferedInputStream(new FileInputStream(upload));
			// 设置缓存
			byte[] buffer = new byte[8192];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
			conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 关闭资源
			close(is, os, conn);
		}
		// 返回结果
		return getResult(conn, map.get("filePath"));
	}

	/**
	 * 文件上传 已封装文件重命名逻辑
	 * 
	 * @param bytes
	 *            要上传的文件的二进制流
	 * 
	 * 
	 * @param fileType
	 *            文件类型，如 .txt .jpg
	 * @return 上传成功则返回文件保存的相对路径，否则为null
	 * @throws Exception
	 */
	public static String uploadFile(byte[] bytes, String fileType)
			throws Exception {
		OutputStream os = null;
		HttpURLConnection conn = null;
		Map<String, String> map = null;
		try {
			map = reNameAndReturnFilePath(fileType);
			// *********与文件服务器通信，并传递数据********//
			conn = getUrlConnection(map.get("url"));
			os = conn.getOutputStream();
			os.write(bytes);
			os.flush();
			conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 关闭资源
			close(null, os, conn);
		}
		// 返回结果
		return getResult(conn, map.get("filePath"));
	}

	/**
	 * 压缩图片上传 已封装文件重命名逻辑
	 * 
	 * @param bi
	 *            要上传的压缩图片
	 * @param fileType
	 *            文件类型，如 .txt .jpg
	 * @return 上传成功则返回文件保存的相对路径，否则为null
	 * @throws Exception
	 */
	public static String uploadFile(BufferedImage bi, String fileType)
			throws Exception {
		OutputStream os = null;
		HttpURLConnection conn = null;
		Map<String, String> map = null;
		try {
			map = reNameAndReturnFilePath(fileType);
			// *********与文件服务器通信，并传递数据********//
			conn = getUrlConnection(map.get("url"));
			os = conn.getOutputStream();
			ImageIO.write(bi, "jpg", os);
			os.flush();
			conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 关闭资源
			close(null, os, conn);
		}
		// 返回结果
		return getResult(conn, map.get("filePath"));
	}

	/**
	 * 下载文件
	 * 
	 * @param filePath
	 *            文件存储的相对路径
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public static InputStream downloadFile(String filePath) throws Exception {

		HttpURLConnection conn = null;

		String url = getFileSystemDownloadServletPath() + "?filePath="
				+ filePath;

		// *********与文件服务器通信********//
		conn = getUrlConnection(url);
		BufferedInputStream is = new BufferedInputStream(conn.getInputStream());

		// 调用文件服务器进行上传文件操作成功，则返回文件的流

		if (conn.getResponseCode() == HttpServletResponse.SC_OK) {
			return is;
		} else {
			return null;
		}
	}
}