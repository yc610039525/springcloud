package com.github.i24x.springcloud.commons.aop.pointcut;

import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class JdkPointcutConfig {
//	@Bean("OrderAdvicePointCut")
	public JdkRegexpMethodPointcut initJdkPointCut() {
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		pointcut.setPatterns("\\.\\*update\\*");
		return pointcut;
	}
}
