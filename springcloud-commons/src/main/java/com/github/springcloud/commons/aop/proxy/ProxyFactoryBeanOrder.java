package com.github.springcloud.commons.aop.proxy;

//@Component
public class ProxyFactoryBeanOrder {
	
	/*@Bean("proxyOrderService")
	public BeanNameAutoProxyCreator initProxyBean(@Qualifier("OrderService") OrderService orderService) {
//			ProxyFactoryBean proxyBean = new ProxyFactoryBean();
//			proxyBean.setTarget(orderService);
//			proxyBean.setInterceptorNames("OrderAdvisor");
//			proxyBean.setInterfaces(IOrderService.class);
			
			BeanNameAutoProxyCreator nameProxy = new BeanNameAutoProxyCreator();
			nameProxy.setBeanNames("\\*");
		return nameProxy;
	}*/
	
	
	/*@Bean("DefaultAdvisorAutoProxyCreator")
	public DefaultAdvisorAutoProxyCreator initAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		return autoProxyCreator;
	}*/
}
