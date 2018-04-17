package com.github.springcloud.commons.test;

public class Test01 {
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();  
        //添加form属性  
        sb.append("--");  
        sb.append("KKKKKKKKKKKKXXXXXXXXXXXXX");  
        sb.append("\r\n");  
        sb.append("Content-Disposition: form-data; name=\"username\"");  
        sb.append("\r\n\r\n");  
        sb.append("LiMing");
        System.out.println(sb.toString());
        sb.append("ASSSSSSSAAA");
        System.out.println(sb.toString());
	}

}
