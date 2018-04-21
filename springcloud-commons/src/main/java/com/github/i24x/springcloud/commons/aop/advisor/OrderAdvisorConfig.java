package com.github.i24x.springcloud.commons.aop.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class OrderAdvisorConfig {
	
//	@Bean("OrderAdvisor")
//	public DefaultPointcutAdvisor initOrderAdvisor(
//			@Qualifier("OrderAdvicePointCut") Pointcut pointcut,
//			@Qualifier("OrderAdvice") Advice advice) {
//		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
//		advisor.setAdvice(advice);
//		advisor.setPointcut(pointcut);
//		return advisor;
//	}
	
    @Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(@Qualifier("OrderAdvice") Advice advice){
      AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
      pointcutAdvisor.setAdvice(advice);
      pointcutAdvisor.setExpression("execution (* com.github.springcloud.commons.service..*Impl.*(..))");
//      Pointcut pointcut = pointcutAdvisor.getPointcut();      
      return pointcutAdvisor;
    }
}
