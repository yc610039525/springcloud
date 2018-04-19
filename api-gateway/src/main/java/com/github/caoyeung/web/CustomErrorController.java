package com.github.caoyeung.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;

import com.alibaba.fastjson.JSON;

@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements  ErrorController {

	private static final String PATH = "/500.error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = "/404.error")
//    @RequestMapping(value = PATH,  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody String error404(HttpServletRequest request, HttpServletResponse response) {
            return JSON.toJSONString(getErrorAttributes(request,false));
    }
    @RequestMapping(value = "/500.error")
//  @RequestMapping(value = PATH,  produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody String error500(HttpServletRequest request, HttpServletResponse response) {
          return JSON.toJSONString(getErrorAttributes(request,false));
  }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}