package com.github.springcloud.commons.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
	public static ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

	public ApplicationContext getCtx() {
		return ctx;
	}
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException{
		T bean = ctx.getBean(name, requiredType);
		return bean;
	}
}
