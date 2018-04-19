package com.github.caoyeung.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {
    private static MimeMessage message;
    private static Session session;
    private static Transport transport;
    private static Properties properties = new Properties();
    private static final Logger log = LoggerFactory.getLogger(MailUtil.class);

    
    /**
     * 发送邮件
     * 
     * @param subject
     *            邮件主题
     * @param sendHtml
     *            邮件内容
     * @param receiveUser
     *            收件人地址
     * @param attachment
     *            附件
     */
    public static  boolean sendHtmlEmail(String subject, String sendHtml, String receiveUserEmail, File attachment,String host,String username,String password) {
        try {
        	
        	properties.put("mail.smtp.host",host);
        	properties.put("mail.smtp.auth",true);
        	properties.put("mail.sender.username",username);
        	properties.put("mail.sender.password",password);
            session = Session.getInstance(properties);
            message = new MimeMessage(session);
        	
            // 发件人
            InternetAddress from = new InternetAddress(username);
            message.setFrom(from);

            // 收件人
            InternetAddress to = new InternetAddress(receiveUserEmail);
            message.setRecipient(Message.RecipientType.TO, to);

            // 邮件主题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            
            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);
            
            // 添加附件的内容
            if (attachment != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                
                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
                
                //MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                multipart.addBodyPart(attachmentBodyPart);
            }
            
            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            
            transport = session.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(host, username,password);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return true;
    }
    
    
    /**
	 * 邮件内容
	 * @package com.util.promotion
	 * @author long.tang
	 * @date 2017-2-9
	 * @method EdmNurtureTools.getContent()
	 * @project h3c_dbs 
	 * @param userName
	 * @return
	 */
	public static String getContent(String userName){
		String content="";
		content+="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		content+="<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>";
		content+="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
		content+="<title>Demystifying Email Design</title>";
		content+="<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />";
		content+="<meta name=\"viewport\" content=\"initial-scale=1,maximum-scale=1,minimum-scale=1\" /><meta name=\"viewport\" content=\"initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5\" /></head>";
		content+="<body style=\"margin: 0; padding: 0;\">";
		content+="<table style=\"border-collapse: collapse; border-spacing: 0; margin: 30px auto; border-color: #18b288;\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">";
		content+="<tr style=\"display: block; text-align: center; width: 100%;\">";
		content+="<td style=\"display: block; text-align: center; width: 100%;\" valign=\"middle\">";
		content+="<table class=\"flexible\" style=\"border: 3px solid #18b288; border-color: #18b288 #e4001a #e4001a #18b288; border-collapse: collapse; border-spacing: 0;\" width=\"778\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><tr><td style=\"padding: 30px 20px 0px 20px;\">";
		content+="<table style=\"display: block; width: 50%; float: left;\"><tr>";
		content+="<td><a style=\"text-decoration: none;\" title=\"DMDlink 1\" href=\"http://unis-huashan.h3c.com/cn/?smt_cp=h3c_2016&amp;smt_pl=header&amp;smt_md=EDM&amp;smt_ct=header\" target=\"_blank\"><img style=\"border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/logo.png\" alt=\"\" /></a></td></tr></table>";
		content+="<table style=\"display: block; width: 50%; float: right; text-align: right; height: 41px; vertical-align: bottom; padding-top: 23px;\" align=\"right\">";
		content+="<tr style=\"display: block; text-align: right; width: 100%;\" align=\"right\">";
		content+="<td style=\"display: block; text-align: right; width: 100%; min-width: 300px;\" align=\"right\"><a style=\"font-size: 12px; line-height: 18px; float: right; display: block; text-decoration: none; color: #282828;\" title=\"DMDlink 2\" href=\"#\" target=\"_blank\">如果您无法正常查看此邮件，请点击此处。</a></td></tr></table></td></tr><tr><td style=\"position: relative;\"><table><tr>";
		content+="<td><a style=\"text-decoration: none;\" title=\"DMDlink 3\" href=\"http://unis-huashan.h3c.com/cn/?smt_cp=h3c_2016&amp;smt_pl=content&amp;smt_md=EDM&amp;smt_ct=kv\" target=\"_blank\"><img style=\"width: 100%; border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/bannerall.jpg\" alt=\"\" /></a></td></tr></table></td></tr><tr>";
		content+="<td style=\"padding: 30px 20px 30px 20px;\">";
		content+="<table style=\";float: left; font-size: 16px; line-height: 26px;\">";
		content+="<tr style=\"display: block; text-align: left; width: 100%;\" align=\"left\">";
		content+="<td style=\"display: block; text-align: left; width: 100%;\" align=\"left\">尊敬的"+userName+"：";
		content+="<p style=\"padding: 0; margin: 0;\">当您收到此邮件，表示您已成为我们的会员，如果您不想再收到此类邮件， <a style=\"text-decoration: none; color: #18b288;\" title=\"DMDlink 4\" href=\"#\">请点击退订。</a></p>";
		content+="<p style=\"padding: 0; margin: 0;\">如果您愿意长期接受此邮件，请将 <a style=\"background: #18b288; color: #fff;\" href=\"#\">h3c@edm.h3c.com</a> 添加至您的联系人列表中。</p>";
		content+="<p>如需了解产品及服务的详细信息，请点击<a style=\"text-decoration: none; color: #18b288;\" title=\"DMDlink 5\" href=\"http://unis-huashan.h3c.com/cn/support/resource_center.html?smt_cp=h3c_2016&amp;smt_pl=content&amp;smt_md=EDM&amp;smt_ct=20160719001\">资源中心</a></p></td></tr></table></td></tr><tr>";
		content+="<td style=\"color: #000; padding: 23px 20px; background: #eeeeee; font-size: 16px; line-height: 20px; text-align: center; vertical-align: middle; font-weight: 500;\" align=\"center\" valign=\"middle\">您可以通过以下方式联系我们：</td></tr><tr>";
		content+="<td style=\"padding: 0px 20px 25px 20px; text-align: center; background: #eeeeee; height: 40px; border: none;\">";
		content+="<table class=\"flexible2\" style=\"background-color: #fff; border: 1px solid #18b288;\" width=\"100%\" cellspacing=\"0\"><tr>";
		content+="<td style=\"padding: 10px 0px; border-bottom: 1px solid #18b288; border-right: 1px solid #18b288;\" align=\"center\" width=\"25%\"><img style=\"display: block; margin: 0 auto; border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/pc.png\" alt=\"\" /></td>";
		content+="<td style=\"padding: 10px 0px; border-bottom: 1px solid #18b288; border-right: 1px solid #18b288;\" align=\"center\" width=\"25%\"><img style=\"border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/weibo.png\" alt=\"\" /></td>";
		content+="<td style=\"padding: 10px 0px; border-bottom: 1px solid #18b288; border-right: 1px solid #18b288;\" align=\"center\" width=\"25%\"><img style=\"display: block; margin: 0 auto; border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/tel.png\" alt=\"\" /></td>";
		content+="<td style=\"padding: 10px 0px; border-bottom: 1px solid #18b288;\" align=\"center\" width=\"25%\"><img style=\"border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/email.png\" alt=\"\" /></td></tr>";
		content+="<tr style=\"border-color: #18b288;\">";
		content+="<td style=\"padding: 10px 0px; border-right: 1px solid #18b288;\" align=\"center\" width=\"25%\"><a style=\"font-size: 14px; line-height: 35px; text-decoration: none; color: #282828;\" title=\"DMDlink 6\" href=\"http://unis-huashan.h3c.com/cn/?smt_cp=h3c_2016&amp;smt_pl=content&amp;smt_md=EDM&amp;smt_ct=20160719002\" target=\"_blank\">unis-huashan.h3c</a></td>";
		content+="<td style=\"padding: 10px 0px; border-right: 1px solid #18b288;\" align=\"center\" width=\"25%\"><a style=\"font-size: 14px; line-height: 35px; color: #282828; text-decoration: none;\" title=\"DMDlink 7\" href=\"http://weibo.com/hpeg\" target=\"_blank\">http://weibo/hpeg</a></td>";
		content+="<td style=\"padding: 10px 0px; border-right: 1px solid #18b288;\" align=\"center\" width=\"25%\"><a style=\"font-size: 14px; line-height: 35px; color: #282828;\" href=\"tel:4006820507\" target=\"_blank\">400-682-0507</a></td>";
		content+="<td style=\"padding: 10px 0px;\" align=\"center\" width=\"25%\"><a style=\"font-size: 14px; line-height: 35px; text-decoration: none; color: #282828;\" title=\"DMDlink 8\" href=\"http://unis-huashan.h3c.com/cn/contact/?smt_cp=h3c_2016&amp;smt_pl=content&amp;smt_md=EDM&amp;smt_ct=20160719003\" target=\"_blank\">邮件咨询</a></td></tr></table></td></tr><tr>";
		content+="<td style=\"padding: 0px 20px 25px 20px; text-align: center; background: #eeeeee; height: 40px;\"><table class=\"flexible2\" width=\"100%\" border=\"0\"><tr>";
		content+="<td style=\"padding: 10px 0px;\" align=\"left\" valign=\"top\" width=\"33%\">";
		content+="<p style=\"padding-left: 40px; font-weight: bold; font-size: 16px; line-height: 48px; margin: 0;\">产品</p>";
		content+="<p style=\"padding: 0 0 0 32px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 9\" href=\"http://unis-huashan.h3c.com/cn/products/servers/mission-critical.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_1\" target=\"_blank\"> <strong style=\"color: #18b288;\">●</strong> 关键业务服务器</a></p>";
		content+="<p style=\"padding: 0 0 0 32px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 10\" href=\"http://unis-huashan.h3c.com/cn/products/servers/industry-standard.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_2\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 工业标准服务器</a></p>";
		content+="<p style=\"padding: 0 0 0 32px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 11\" href=\"http://unis-huashan.h3c.com/cn/products/storage/index.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_3\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 存储</a></p>";
		content+="<p style=\"padding: 0 0 0 32px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 12\" href=\"http://unis-huashan.h3c.com/cn/products/integrated-system/index.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_4\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 集成系统</a></p>";
		content+="<p style=\"padding: 0 0 0 32px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 13\" href=\"http://unis-huashan.h3c.com/cn/products/software/index.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_5\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 软件</a></p></td>";
		content+="<td style=\"padding: 10px 0px;\" align=\"left\" valign=\"top\" width=\"33%\">";
		content+="<p style=\"padding-left: 60px; font-weight: bold; font-size: 16px; line-height: 48px; margin: 0;\">服务</p>";
		content+="<p style=\"padding: 0 0 0 52px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 14\" href=\"http://unis-huashan.h3c.com/cn/service/technology-service-consulting.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_6\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 技术咨询服务</a></p>";
		content+="<p style=\"padding: 0 0 0 52px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 15\" href=\"http://unis-huashan.h3c.com/cn/service/technology-service-support.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_7\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 技术支持服务</a></p>";
		content+="<p style=\"padding: 0 0 0 52px; margin: 0;\"><a style=\"font-size: 14px; line-height: 24px; text-decoration: none; color: #707277;\" title=\"DMDlink 16\" href=\"http://unis-huashan.h3c.com/cn/service/financing-capacity.html?smt_cp=h3c_2016&amp;smt_pl=footer&amp;smt_md=EDM&amp;smt_ct=footer_8\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 弹性容量服务</a></p>";
		content+="<p style=\"padding: 0 0 0 52px; margin: 0;\"><a style=\"color: #707277; font-size: 14px; line-height: 24px; text-decoration: none;\" title=\"DMDlink 17\" href=\"http://www.hpe-online.com/hpe/index.html\" target=\"_blank\"><strong style=\"color: #18b288;\">●</strong> 惠普大学</a></p></td>";
		content+="<td style=\"padding: 10px 0px;\" align=\"center\" valign=\"top\" width=\"33%\"><img style=\"padding: 20px 0 0 40px; margin: 0; border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/ewm.png\" alt=\"惠普微信\" /></td></tr></table></td></tr><tr>";
		content+="<td style=\"padding: 10px 20px 10px 20px;\"><img src=\"https://h3c.webpower.asia/mailings/6/185/images/text.png\" alt=\"\" /></td></tr><tr>";
		content+="<td style=\"padding: 0px 20px 15px 20px;\" align=\"right\">";
		content+="<table style=\";border-top: 1px solid #9b9a9a; text-align: right; padding-top: 15px; width: 100%;\">";
		content+="<tr style=\"display: block; text-align: right; width: 100%; float: right;\" align=\"right\">";
		content+="<img height=\"1\" width=\"1\" src=\"http://tracking.h3c.com:8085/tracking/?u=11092@h3c.com&amp;msg=42B96F07.BFD0.496F.A68E.A8411B35C564.0000.20170119.FAMRDGEHIBUSMHSO@h3c-email.com\">";
		content+="<td style=\"display: block; text-align: right; width: 100%; min-width: 300px;\" align=\"right\"><img style=\"border: 0;\" src=\"https://h3c.webpower.asia/mailings/6/185/images/logob.png\" alt=\"\" /></td>";
		content+="</tr></table></td></tr></table></td></tr></table><p style=\"display: none;\">&nbsp;</p></body></html>";
		return content;
	}
    

    public static void main(String[] args) {
        try {
			sendHtmlEmail("邮件主题", getContent("CaoYeung"),"610039525@qq.com", null,"smtp.163.com","cyang198906@163.com","yc535689");//
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
    }
}