package com.github.i24x.springcloud.commons.aop.advice;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;
@Component("OrderAdvice")
public class OrderAdvice implements MethodBeforeAdvice, AfterReturningAdvice{
	public static Logger logger = LoggerFactory.getLogger(OrderAdvice.class);
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		logger.info("记录订单日志");

	}
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		logger.info("处理订单调用情况");
	}
}