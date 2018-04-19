package com.github.caoyeung.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail_163Util {
	public static String sendEmail(final String account,final String[] destnations,final String authPassWord,String title,String content){
		Properties props = new Properties();  
        props.setProperty("mail.smtp.auth", "true");  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.host", "smtp.163.com");  
        Session session = Session.getInstance(props, new Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() { 
            	//第三方客户端授权密码和登录密码不一样
            	final String account_prefix = account.split("@")[0];
                return new PasswordAuthentication(account_prefix, authPassWord);  
            }  
        });  
        session.setDebug(true);  
        try {
        	Message msg = new MimeMessage(session);
        	msg.setFrom(new InternetAddress(account)); 
			msg.setSubject(title);
			msg.setContent("<span style='color:red;margin:0 auto'>"+content+"</span>", "text/html;charset=utf-8");
			if(destnations!=null&&destnations.length==1){
				msg.setRecipient(RecipientType.TO, new InternetAddress(destnations[0])); 
			}else if(destnations!=null&&destnations.length>1){
				msg.setRecipients(RecipientType.TO, InternetAddress.parse(Mail_163Util.join(destnations))); 
			}else{
				throw new IllegalArgumentException("目标邮件地址不可为空");
			}
	        Transport.send(msg); 
	        return MailStatus.SUCCESS;
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}  
        return MailStatus.FAILED;
	}
	static class MailStatus {
		public static final String SUCCESS ="SUCCESS";
		public static final String FAILED ="FAILED";
		public static final String Email_suffix ="@163.com";
	}
	static String join(String[] arr){
		if(arr==null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<arr.length;i++){
			if (i ==0) {
				sb.append(arr[0]);
			} else {
				sb.append(",").append(arr[i]);
			}
			
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws MessagingException {
		String result = Mail_163Util.sendEmail("cyang198906@163.com", 
				new String[]{
				"610039525@qq.com",
				"2406352526@qq.com",
				"yangcao@boco.com.cn",
				"1938027689@qq.com"
				}, 
				"yc535689", 
				"【Jmail_163邮箱发送测试】",
				"Jmail_163邮箱发送测试......");
		System.out.println("邮件发送结果："+result);
	}
}