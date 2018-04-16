package com.github.springcloud.commons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.springcloud.commons.aware.SpringContextUtil;
import com.github.springcloud.commons.service.IOrderService;
@Controller
@RequestMapping("/api")
public class BaseController {
//	@Autowired
//	IOrderService bean;
	@RequestMapping(value = { "/getOrder/{name}" }, method = { RequestMethod.GET })
	public @ResponseBody String getOrder(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("name") String name) {
		IOrderService bean = SpringContextUtil.getBean("OrderService", IOrderService.class);
//		IOrderService bean = SpringContextUtil.getBean("OrderService", IOrderService.class);
		bean.updateOrder();
		return name;
	}
}
