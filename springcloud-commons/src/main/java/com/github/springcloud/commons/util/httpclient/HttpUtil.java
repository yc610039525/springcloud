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
import java.net.URLDecoder;
import java.net.URLEncoder;
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
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	public static final String REQUEST_METHOD_POST = "POST";
	public static final String REQUEST_METHOD_GET = "GET";
	public static final String REQUEST_CHARSET_UTF8 = "UTF-8";
	public static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";
	public static final String CONTENT_TYPE_DEFAULT = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT = "text/html";

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
		return request(url, nameValuePairs, requestMethod, null);
	}
	/**
	 * 参数为 xxxx?xxx=xxx&xx=xxx
	 */
	public static String request(String url, Map<String, String> nameValuePairs, String requestMethod, 
			String contentType) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "URL[" + url + "]请求失败";
		try {
			if (requestMethod == null||!requestMethod.toUpperCase().equals(HttpUtil.REQUEST_METHOD_POST)) {
				requestMethod = HttpUtil.REQUEST_METHOD_GET;
			} else {
				requestMethod = HttpUtil.REQUEST_METHOD_POST;
			}
			String reqParams = null;
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				Set<Entry<String, String>> nameValuePairSet = nameValuePairs.entrySet();
				for (Entry<String, String> nameValue : nameValuePairSet) {
					String nameValuePair = nameValue.getKey() + "=" + URLEncoder.encode(nameValue.getValue(), HttpUtil.REQUEST_CHARSET_UTF8);
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
			if (contentType == null) {
				//可以不设置
				if (HttpUtil.REQUEST_METHOD_POST.equals(requestMethod)) {
					//POST 此种方式不可设置为JSON
//					conn.setRequestProperty("Content-Type", HttpUtil.CONTENT_TYPE_JSON);
				} else {
					conn.setRequestProperty("Content-Type", HttpUtil.CONTENT_TYPE_DEFAULT);
				}
			} else {
				conn.setRequestProperty("Content-Type", contentType);
			}
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
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
				out.print(reqParams);//url参数部分
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
			close(out, in);
		}
		return result;
	}
	/**
	 * POST对象 提交JSON对象
	 */
	public static <T> String submit(String url, T t) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "URL[" + url + "]请求失败";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type", HttpUtil.CONTENT_TYPE_JSON);
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			String body = JSON.toJSONString(t);
			out = new PrintWriter(conn.getOutputStream());
			out.print(body);
			out.flush();
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
			conn.disconnect();
		} catch (Exception e) {
			logger.debug("发送 HTTP请求出现异常！" + e.getMessage());
			e.printStackTrace();
		} finally {
			close(out, in);
		}
		return result;
	}
	/**
	 * POST 提交不同类型请求参数 JSON+xxxx?xxx=xxx&xx=xxx
	 */
	public static <T> String submitMulti(String url, Map<String, String> nameValuePairs,T t) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "URL[" + url + "]请求失败";
		try {
			String reqParams = null;
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				Set<Entry<String, String>> nameValuePairSet = nameValuePairs.entrySet();
				for (Entry<String, String> nameValue : nameValuePairSet) {
					String nameValuePair = nameValue.getKey() + "=" + URLEncoder.encode(nameValue.getValue(), HttpUtil.REQUEST_CHARSET_UTF8);
					if (reqParams == null) {
						reqParams = nameValuePair;
					} else {
						reqParams = reqParams + "&" + nameValuePair;
					}
				}
			}
			if (reqParams != null) {
				url = url + "?" + reqParams;
			}
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type", HttpUtil.CONTENT_TYPE_JSON);//类型必须设置为JSON
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			String bodyData = JSON.toJSONString(t);
			out = new PrintWriter(conn.getOutputStream());
			out.print(bodyData);
			out.flush();
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
//			conn.disconnect();
		} catch (Exception e) {
			logger.debug("发送 HTTP请求出现异常！" + e.getMessage());
			e.printStackTrace();
		} finally {
			close(out, in);
		}
		return result;
	}
	
	private static void close(PrintWriter out, BufferedReader in) {
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

	public static String post(String url, Map<String, String> nameValuePairs) throws IOException {
		String resultErrorDesc = "URL[" + url + "]请求失败";
		String result = "URL[" + url + "]请求失败";
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
		String result = "URL[" + url + "]请求失败";
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
		String result = "测试开始......";
		System.out.println(result);
		Email email = new Email();
		email.setCuid("123456");
		email.setDestnation("610039525@qq.com");
		Map<String, String> nameValuePairs = new HashMap<String, String>();
		nameValuePairs.put("email", JSON.toJSONString(email));
		//URL参数
//		result = HttpUtil.request(ROOT_URL + "/EmailController/sendPrizeEmailStr", nameValuePairs, "GET");
		result = HttpUtil.request(ROOT_URL + "/EmailController/sendPrizeEmailStr", nameValuePairs, "POST");
		System.out.println("1=>"+result);		
		//JSON对象
//		result = HttpUtil.submit(ROOT_URL + "/EmailController/sendPrizeEmail", email);
		System.out.println("2=>"+result);
		//复合参数
		Map<String, String> urlNameValuePairs = new HashMap<String, String>();
		urlNameValuePairs.put("phone", "18502817833");
//		result = HttpUtil.submitMulti(ROOT_URL + "/EmailController/sendPrizeEmailMulti", urlNameValuePairs, email);
		System.out.println("3=>"+result);
	}
}
