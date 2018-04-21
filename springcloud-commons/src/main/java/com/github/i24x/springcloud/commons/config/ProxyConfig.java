package com.github.i24x.springcloud.commons.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//@Configuration
public class ProxyConfig implements EnvironmentAware {
	
	private static String regexp = "execution (* com.github.caoyeung.service..*Impl.*(..))";
    public Environment environment;
	@Override
    public void setEnvironment(Environment env) {
		environment = env;
    }
 
   /* @Bean(name = "txManager")
    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier("druidDataSourceTnms")DataSource ds) throws SQLException {
        return new DataSourceTransactionManager(ds);
    }
    
    @Bean(name = "txInterceptor")
    @Primary
    public TransactionInterceptor transactionInterceptor(@Qualifier("txManager")PlatformTransactionManager transactionManager) throws SQLException {
        Properties props = new Properties();
        props.setProperty("select*", "PROPAGATION_SUPPORTS,-Throwable,readOnly");
        props.setProperty("insert*", "PROPAGATION_REQUIRED,-java.lang.Exception");
        props.setProperty("update*", "PROPAGATION_REQUIRED,-java.lang.Exception");
        props.setProperty("delete*", "PROPAGATION_REQUIRED,-java.lang.Exception");
        props.setProperty("*", "PROPAGATION_REQUIRED");
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, props);
        return txAdvice;
    }*/
    /*@Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(@Qualifier("txInterceptor")TransactionInterceptor txInterceptor){
      AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
      pointcutAdvisor.setAdvice(txInterceptor);
      pointcutAdvisor.setExpression(regexp);
      return pointcutAdvisor;
    }*/
    
    /*@Bean  
    public BeanNameAutoProxyCreator transProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setProxyTargetClass(true);
        creator.setBeanNames("*Impl","*Service","*BOImpl","*BO");
        creator.setInterceptorNames("txInterceptor");
        return creator;
 
    }*/
	
	
	/*ProxyFactoryBean factory = new ProxyFactoryBean();
    factory.setTarget(new Person());
    //声明一个aspectj切点
    AspectJExpressionPointcut cut = new AspectJExpressionPointcut();
    //设置需要拦截的方法-用切点语言来写
    cut.setExpression("execution( int cn.hncu.xmlImpl.aspectj.Person.run() )");//拦截:空参返回值为int的run方法
    Advice advice = new MethodInterceptor() {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("放行前拦截...");
            Object obj = invocation.proceed();//放行
            System.out.println("放行后拦截...");
            return obj;
        }
    };

    //切面=切点+通知
    Advisor advisor = new DefaultPointcutAdvisor(cut,advice);
    factory.addAdvisor(advisor);
    Person p = (Person) factory.getObject();
    p.run();
    p.run(10);
    p.say();
    p.sayHi("Jack");
    p.say("Tom", 666);*/
    
}
