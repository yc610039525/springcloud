package com.github.caoyeung.controller.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 声明式服务调用客户端
 */
@FeignClient("eureka-provider2")
public interface UserFeginClient {
	@GetMapping("/UserController/getDefaultUser")
	String getDefaultUser();
	@RequestMapping(value = "/UserController/updateDefaultUser/{name}", method = RequestMethod.GET)
	public String updateDefaultUser(@PathVariable("name") String name);
}
