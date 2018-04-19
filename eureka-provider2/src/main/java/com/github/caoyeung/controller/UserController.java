package com.github.caoyeung.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.codingapi.tx.annotation.TxTransaction;
import com.github.caoyeung.model.User;
import com.github.caoyeung.service.UserService;
@Api(value = "接口测试",description ="UserController")
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	public static final String API_PREFIX = "/UserController";
	@Autowired
	private UserService UserService;
	
	@ApiOperation(value="updateDefaultUser", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "name", 
            		value = "name", required = true, paramType = "path")
    })
	@RequestMapping(value=API_PREFIX+"/updateDefaultUser/{name}",
	method = RequestMethod.GET/*,
	produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
	consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}*/)
	@ResponseBody
	public String updateDefaultUser(@PathVariable String name){
		User user = UserService.updateDefaultUser(name);
//		Gson gson =new Gson();
//		String userStr = gson.toJson(user);
		String userStr = JSON.toJSONString(user);
		return "User:"+userStr;
	}
	@TxTransaction
	@ApiOperation(value="getDefaultUser", notes="")
	@RequestMapping(value=API_PREFIX+"/getDefaultUser",
			method = RequestMethod.GET)
	@ResponseBody
	public User getDefaultUser() throws Exception{
		Random random = new Random();
		int nextInt = random.nextInt(10);
		if(nextInt%2==0){
			logger.info("Create Exception ....");
//			throw new RuntimeException("获取数据失败"+(new Date()).getSeconds());
//			TimeUnit.SECONDS.sleep(5);
		}
		User user = UserService.getDefaultUser();
        return user;
	}
	/**
	 * 页面跳转传值
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
    public String doView(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		User user = new User();
		user.setName("XXXX");
		map.addAttribute("user",user);
		return "index";
    }
	/**
	 * 页面跳转并传递对象
	 * @return
	 */
	@RequestMapping(value={"/doit"}, method = {RequestMethod.GET})
	public ModelAndView doit(HttpServletRequest request,HttpServletResponse response)
	{
		//构建ModelAndView实例，并设置跳转地址
		ModelAndView view = new ModelAndView("/index");
		//将数据放置到ModelAndView对象view中,第二个参数可以是任何java类型
		User user = new User();
		user.setName("XXXXX");
		view.addObject("user",user);
		//返回ModelAndView对象view
		return view;
	}
	public static void main(String[] args) {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		System.out.println(nextInt+"");
	}
	
}
