package com.github.caoyeung.controller;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codingapi.tx.annotation.TxTransaction;
import com.github.caoyeung.base.BaseController;
import com.github.caoyeung.controller.fegin.UserFeginClient;
import com.github.caoyeung.util.Mail_163Util;


@Controller
public class EmailController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	@Autowired
	UserFeginClient userFeginClient;
	public static final String API_PREFIX = "/EmailController";
	
	@TxTransaction(isStart=true)
	@RequestMapping(value=API_PREFIX+"/getUserAndsendSystemEmail",
	method = RequestMethod.GET)
	@ResponseBody
	public String sendSystemEmail(){
		String defaultUser = userFeginClient.getDefaultUser();
		logger.info(defaultUser);
		Random random = new Random();
		int nextInt = random.nextInt(10);
		if(nextInt%2==0){
			logger.info("Create Exception ....");
			throw new RuntimeException("获取数据失败"+(new Date()).getSeconds());
//			TimeUnit.SECONDS.sleep(5);
		}
		String result ="SUCESS";
//		       result = Mail_163Util.sendEmail("cyang198906@163.com", 
//				new String[]{
////				"610039525@qq.com",
//				"2406352526@qq.com",
////				"yangcao@boco.com.cn",
////				"1938027689@qq.com"
//				}, 
//				"yc535689", 
//				"【Jmail_163邮箱发送测试】",
//				"Jmail_163邮箱发送测试......");
		return result;
	}
	
}
