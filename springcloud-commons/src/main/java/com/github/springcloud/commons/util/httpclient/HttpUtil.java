package com.github.springcloud.commons.util.httpclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.springcloud.commons.model.Email;

public class HttpUtil {
	/**
	 * URLConnection 原始方式发送Http请求
	 */
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	public static final String REQUEST_METHOD_POST = "POST";
	public static final String REQUEST_METHOD_GET = "GET";

	public static boolean downLoadFile(String attachUrl, String localfilepath, String localFileName) {
		try {
			URL url = new URL(attachUrl);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setUseCaches(false);
			urlconnection.setConnectTimeout(20000);
			urlconnection.connect();
			byte[] buffers = new byte[4096];
			FileOutputStream fos = null;
			int size = 0;
			InputStream is = urlconnection.getInputStream();
			BufferedInputStream bufferedinputstream = new BufferedInputStream(is);
			// long filelength = bufferedinputstream.available();
			if (bufferedinputstream != null) {
				fos = new FileOutputStream(localfilepath + File.separator + localFileName);
				logger.debug("正在获取链接[" + url + "]的内容...\n将其保存为文件[" + localFileName + "]");
				while ((size = bufferedinputstream.read(buffers)) != -1) {
					fos.write(buffers, 0, size);
				}
				fos.close();
				bufferedinputstream.close();
				urlconnection.disconnect();
				return true;
			}
		} catch (MalformedURLException expt) {
			logger.error("下载附件错误", expt);
		} catch (IOException eio) {
			logger.error("下载附件错误", eio);
		}
		return false;
	}

	public static String request(String url, Map<String, String> nameValuePairs, String requestMethod) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "ERROR";
		try {
			if (requestMethod == null
					|| (!requestMethod.toUpperCase().equals(HttpUtil.REQUEST_METHOD_POST) && !requestMethod
							.toUpperCase().equals(HttpUtil.REQUEST_METHOD_GET))) {
				requestMethod = HttpUtil.REQUEST_METHOD_GET;
			} else {
				requestMethod = HttpUtil.REQUEST_METHOD_POST;
			}
			String reqParams = null;
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				Set<Entry<String, String>> nameValuePairSet = nameValuePairs.entrySet();
				for (Entry<String, String> nameValue : nameValuePairSet) {
					String nameValuePair = nameValue.getKey() + "=" + nameValue.getValue();
					if (reqParams == null) {
						reqParams = nameValuePair;
					} else {
						reqParams = reqParams + "&" + nameValuePair;
					}
				}
			}
			if (reqParams != null && HttpUtil.REQUEST_METHOD_GET.equals(requestMethod)) {
				url = url + "?" + reqParams;
			}
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setConnectTimeout(10000);// 连接的超时时间
			conn.setReadTimeout(5000);// 读数据的超时时间
			// conn.setRequestProperty("Content-Length", 100);//设置请求消息头 可以设置多个
			conn.setRequestMethod(requestMethod);
			if (HttpUtil.REQUEST_METHOD_POST.equals(requestMethod)) {
				conn.setDoInput(true);
				conn.setDoOutput(true);
			} else {
				conn.setDoInput(true);// 对应返回输入
				conn.setDoOutput(false);// 对应输入
			}
			if (HttpUtil.REQUEST_METHOD_POST.equals(requestMethod)) {
				out = new PrintWriter(conn.getOutputStream());
				out.print(reqParams);
				out.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				if ("".equals(result)) {
					result = "OK";
				}
			}
		} catch (Exception e) {
			logger.debug("发送 HTTP请求出现异常！" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.debug("发送 HTTP请求出现异常！" + e.getMessage());
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String post(String url, Map<String, String> nameValuePairs) throws IOException {
		String resultErrorDesc = "URL[" + url + "]请求失败";
		String result = "ERROR";
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			// 创建一个post对象 //包装成一个Entity对象 //设置请求的内容 //执行post请求
			HttpPost post = new HttpPost(url);
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				List<NameValuePair> kvList = new ArrayList<NameValuePair>();
				Set<Entry<String, String>> nameValuePairSet = nameValuePairs.entrySet();
				for (Entry<String, String> nameValue : nameValuePairSet) {
					kvList.add(new BasicNameValuePair(nameValue.getKey(), nameValue.getValue()));
				}
				StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
				post.setEntity(entity);
			}
			response = httpClient.execute(post);
			result = "";
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			logger.error(resultErrorDesc + "," + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				logger.error(resultErrorDesc + "," + e.getMessage());
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String get(String url, Map<String, String> nameValuePairs) {
		String resultErrorDesc = "URL[" + url + "]请求失败";
		String result = "ERROR";
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			URIBuilder uriBuilder = new URIBuilder(url);
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				Set<Entry<String, String>> nameValuePairSet = nameValuePairs.entrySet();
				for (Entry<String, String> nameValue : nameValuePairSet) {
					uriBuilder.addParameter(nameValue.getKey(), nameValue.getValue());
				}
			}
			HttpGet get = new HttpGet(uriBuilder.build());
			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			result = "";
			result = EntityUtils.toString(entity, "utf-8");

		} catch (Exception e) {
			logger.error(resultErrorDesc + "," + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				logger.error(resultErrorDesc + "," + e.getMessage());
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		String ROOT_URL = "http://localhost:8762";
		String result = "";
		// result = HttpUtil.get(ROOT_URL
		// + "/EmailController/getUserAndsendSystemEmail", null);
		// System.out.println(result);

		result = HttpUtil.request(ROOT_URL + "/EmailController/getUserAndsendSystemEmail", null, null);
		Email email = new Email();
		email.setCuid("123456");
		email.setDestnation("610039525@qq.com");
		Map<String, String> nameValuePairs = new HashMap<String, String>();
		nameValuePairs.put("email", JSON.toJSONString(email));
		result = HttpUtil.request(ROOT_URL + "/EmailController/sendPrizeEmail", nameValuePairs, "POST");
		// result = HttpUtil.post(ROOT_URL
		// + "/EmailController/sendPrizeEmail", nameValuePairs);
		System.out.println(result);

	}
}
