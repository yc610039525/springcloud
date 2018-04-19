package com.chamc.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.github.caoyeung.application.Provider1_StartupApplication;
import com.github.caoyeung.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Provider1_StartupApplication.class)
public class ApplicationTest {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
//	@Test
	public void test() throws Exception {
		User user = new User("liming", "610039525@163.com");
		user.setId(12345);
		user.setPhone(1234567);
		user.setAge(15);
		redisTemplate.opsForValue().set(user.getName(), user);
		User u = (User)redisTemplate.opsForValue().get("liming");
		Assert.assertEquals("610039525@163.com", u.getEmail());
		logger.info(u+"");
	}
	@Autowired
	LoadBalancerClient loadBalancerClient;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	DiscoveryClient discoveryClient;
	/**
	 * LoadBalancerClient 获取服务实例
	 * ServiceInstance 获取调用URI
	 * DiscoveryClient 发现所有客户端
	 */
//	@Test
	public void customer(){
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider2");
		logger.info("*********************************"+serviceInstance.getUri());
		String url = serviceInstance.getUri()+"/UserController/getDefaultUser";
        String r = restTemplate.getForObject(url, String.class);
        logger.info("*********************************"+r);
        List<String> services = discoveryClient.getServices();
        logger.info("*********************************"+services);
	}
	/**
	 * 使用Ribbon调用服务，不再使用负载均衡器获取服务实例
	 */
//	@Test
	public void customerRibbon(){
		String url = "http://eureka-provider2"+"/UserController/getDefaultUser";
        String r = restTemplate.getForObject(url, String.class);
        logger.info(r);
	}
}