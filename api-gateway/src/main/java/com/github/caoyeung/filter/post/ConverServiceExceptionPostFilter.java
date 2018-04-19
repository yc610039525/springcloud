package com.github.caoyeung.filter.post;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpResponse;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.caoyeung.model.Result;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ConverServiceExceptionPostFilter extends ZuulFilter  {

    private static Logger log = LoggerFactory.getLogger(ConverServiceExceptionPostFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
    	RequestContext ctx = RequestContext.getCurrentContext();
  	  	RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestURL = request.getRequestURI();
        String proxy = (String)ctx.get("proxy");
        int responseStatusCode = context.getResponseStatusCode();
        String invokeMeta = "requestUri:["+requestURL+"]"+",proxyServerId:["+proxy+"],time:"+System.currentTimeMillis();
        if(responseStatusCode != HttpStatus.OK.value()){
      	  Result r = new Result();
      	  r.setCode(responseStatusCode);
      	  r.setDesc(invokeMeta);
    		  ctx.setSendZuulResponse(false); 
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody(JSON.toJSONString(r));
        }
        log.info(invokeMeta);
        return null;
    }

}
