package com.github.i24x.springcloud.commons.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.i24x.springcloud.commons.ojdbc.OracleConnectionUtil;

public class MapUtil {
	private static Logger logger = LoggerFactory.getLogger(OracleConnectionUtil.class);

	public static void savePropertiesToFile(String filePath, Properties p,
			String comments) {
		try {
			if (p != null && !p.isEmpty()) {
				Writer writer = new FileWriter(filePath);
				p.store(writer, comments);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties loadFileToProperties(String filePath) {
		Properties prop = new Properties();
		try {
			InputStream resourceStream = OracleConnectionUtil.class.getClassLoader()
					.getResourceAsStream(filePath);
			prop.load(resourceStream);
		} catch (Exception e) {
			logger.info("加载数据库配置文件失败", e);
			e.printStackTrace();
		}
		return prop;
	}
	public static String getPropStringValue(Properties prop,String key) {
		return (String)prop.get(key);
	}
}
