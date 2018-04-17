package com.github.springcloud.commons.util.httpclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpClientPost {
	private static String enter = "\r\n";//换行
	private static String twoHyphens = "--";//连字符
	private static String boundary = "************************";//分界线
	private static String charSet ="UTF-8";
	/**
	 * @param actionUrl 
	 * @param valueNamePairs 额外参数
	 * @param filePathRoutes 文件映射
	 * @return
	 */
	public static String upload(String actionUrl,Map<String,String> valueNamePairs,Map<String,String> filePathRoutes) {
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", charSet);
			conn.setChunkedStreamingMode(10240);
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			StringBuilder sb = new StringBuilder();
			// 添加form属性
			Set<Entry<String, String>> entrySet = valueNamePairs.entrySet();
			for(Entry<String, String> valueNamePair : entrySet){
				sb.append(twoHyphens+boundary+enter);
				sb.append("Content-Disposition: form-data; name=\""+valueNamePair.getKey()+"\"");
				sb.append(enter+enter);
				sb.append(valueNamePair.getValue());
				sb.append(enter);
			}
			out.write(sb.toString().getBytes(charSet));
			Set<Entry<String, String>> filePaths = filePathRoutes.entrySet();
			int i = 0;
			for(Entry<String, String> filePathRoute : filePaths){
				String filePath = filePathRoute.getValue();
				File file = new File(filePath);
				String filename = file.getName();
				//头信息
				out.writeBytes(twoHyphens + boundary + enter);
				out.writeBytes("Content-Disposition: form-data;name=\"file" + i + "\";filename=\"" + filename + "\"");
				out.writeBytes(enter+enter);
				i++;
				//正文
				FileInputStream fStream = new FileInputStream(file);
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				int length = -1;
				while ((length = fStream.read(buffer)) != -1) {
					out.write(buffer, 0, length);
				}
				//结束信息
				out.writeBytes(enter);
				fStream.close();
			}
			
			//结束 --*********************--\r\n
			out.writeBytes(twoHyphens + boundary + twoHyphens + enter); 
			out.flush();
			InputStream is = conn.getInputStream();
			InputStreamReader in = new InputStreamReader(is, charSet);;
			StringBuffer b = new StringBuffer();
			BufferedReader buffer = new BufferedReader(in);
			String lineText = null;
			while ((lineText = buffer.readLine()) != null) {
				b.append(lineText);
			}
			String urlStr = b.toString();
			System.out.println(urlStr);
			out.close();
			return urlStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "文件上传失败";
		}
		
	}
	/**
	 * 混合提交
	 * @param url
	 * @param nameValuePairs GET参数列表
	 * @param 提交对象 后台通过@RequestBody 自动转化
	 * @return
	 */
	public static <T> String submitMulti(String url, Map<String, String> nameValuePairs,T t) {
		return HttpUtil.submitMulti(url, nameValuePairs, t);
	}
	/**
	 * 
	 * @param url
	 * @param t 提交对象 后台通过@RequestBody 自动转化
	 * @return
	 */
	public static <T> String submit(String url, T t) {
		return HttpUtil.submit(url, t);
	}
	
	public static void main(String[] args) throws URISyntaxException {
//		String url = "http://localhost:8762/import/upload";
		String url = "http://localhost:8762/import/uploadmulti";
		Map<String,String>  valueNamePairs = new HashMap<String,String>(); 
		valueNamePairs.put("username", "github.com");
		valueNamePairs.put("password", "github.com");
		Map<String,String>  filePathRoutes = new HashMap<String,String>(); 
		filePathRoutes.put("file2", "E:/temp/file2.txt");
		filePathRoutes.put("file1", "E:/temp/file1.txt");
		HttpClientPost.upload(url,valueNamePairs,filePathRoutes);

	}
}
