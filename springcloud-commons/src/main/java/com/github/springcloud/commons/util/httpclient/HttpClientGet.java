package com.github.springcloud.commons.util.httpclient;

import java.util.Map;

public class HttpClientGet {
	
	/**
	 * 
	 * @param url
	 * @param nameValuePairs 参数列表
	 * @param requestMethod
	 * @return
	 */
	public static String request(String url, Map<String, String> nameValuePairs) {
		return HttpUtil.request(url, nameValuePairs, "GET", null);
	}
}
