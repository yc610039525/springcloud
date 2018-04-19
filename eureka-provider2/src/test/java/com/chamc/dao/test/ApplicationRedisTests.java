package com.chamc.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.caoyeung.application.Provider2_StartupApplication;
import com.github.caoyeung.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Provider2_StartupApplication.class)
public class ApplicationRedisTests {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Test
	public void test() throws Exception {
		// 保存对象
		User user = new User("liming", "6100@163.com");
		user.setId(12345);
		user.setPhone(1234567);
		user.setAge(15);
		redisTemplate.opsForValue().set(user.getName(), user);
		User u = (User)redisTemplate.opsForValue().get("liming");
		Assert.assertEquals("6100@163.com", u.getEmail());
		System.out.println(u);
	}
}