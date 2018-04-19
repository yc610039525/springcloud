package com.github.caoyeung.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IoUtil {
	
	
	

	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 */
	public static String getFileContent(String path) {
		String result="";
		try {
			FileReader fr = new FileReader(path);// 需要读取的文件路径
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			result=str;
			while (str != null)// 如果当前行不为空
			{
				System.out.println(str);// 打印当前行
				str = br.readLine();// 读取下一行
				if(str!=null){
				  result+=str;
				}
			}
			br.close();// 关闭BufferReader流
			fr.close(); // 关闭文件流
		} catch (IOException e)// 捕捉异常
		{
			System.out.println("指定文件不存在");// 处理异常
		}
		return result;
	}
	

}
