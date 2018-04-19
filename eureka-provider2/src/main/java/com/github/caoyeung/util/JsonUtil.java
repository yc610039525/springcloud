package com.github.caoyeung.util;

import java.io.InputStream;
import java.net.URL;

import com.alibaba.fastjson.JSON;


public final class JsonUtil {

	/**
	 * 将对象转换成 JSON 字符串
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * Action中动态解析URL返回Json
	 * 只支持GET请求
	 */
	public static String getBackJson(String url){
		StringBuffer htmlBuffer = new StringBuffer();
		String returnStr = null;
		try {
			InputStream inputSource = new URL(url).openStream();
			int ch;
			while ((ch = inputSource.read()) > -1) {
				htmlBuffer.append((char) ch);
			}
			inputSource.close();
			returnStr = new String(htmlBuffer);
			returnStr = new String(returnStr.getBytes("ISO8859_1"),"UTF-8");
		} catch (Exception e) {
			System.out.println("error>>>for getBackJson.>>>");
			e.printStackTrace();
		}
		return returnStr;
	}



}
