package com.github.caoyeung.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.caoyeung.model.User;
import com.github.caoyeung.service.UserService;
//@Api(value = "接口测试",description ="UserController")
@Controller
public class UserController {
	
	
	public static final String API_PREFIX = "/UserController";
	@Autowired
	private UserService UserService;
	
//	@ApiOperation(value="updateDefaultUser", notes="")
//    @ApiImplicitParams({
//            @ApiImplicitParam(dataType = "java.lang.String", name = "name", 
//            		value = "name", required = true, paramType = "path")
//    })
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
	@RequestMapping(value=API_PREFIX+"/getDefaultUser",
			method = RequestMethod.GET)
	@ResponseBody
	public User getDefaultUser(){
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
	/**
	 * 测试服务降级
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { API_PREFIX+"/testHystrix" }, method = { RequestMethod.GET })
	public @ResponseBody User testHystrix(HttpServletRequest request,
			HttpServletResponse response) {
		User u = UserService.getUser();
		return u;
	}
}
