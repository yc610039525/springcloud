package com.github.caoyeung.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

public class MapUtil{
	public static void main(String[] args) throws IOException {
		 // 创建集合对象
        Properties prop = new Properties();
        prop.setProperty("蛋蛋", "27");
        prop.setProperty("皮皮", "30");
        prop.setProperty("圈圈", "18");
        String filePath ="text.properties";
        savePropertiesToFile(filePath,prop,null);
        Properties p = loadFileToProperties(filePath);
        System.out.println(p);
        
        
	}
	public static void savePropertiesToFile(String filePath,Properties p,String comments){
		try {
			if(p!=null&&!p.isEmpty()){
				Writer writer = new FileWriter(filePath);
				p.store(writer, comments);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Properties loadFileToProperties(String filePath){
		Properties prop = new Properties();
		
		try {
			Reader r = new FileReader(filePath);
			prop.load(r);
		    r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}
