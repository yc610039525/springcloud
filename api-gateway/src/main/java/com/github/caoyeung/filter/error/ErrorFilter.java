package com.github.caoyeung.filter.error;

import com.alibaba.fastjson.JSON;
import com.github.caoyeung.model.Result;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ErrorFilter extends ZuulFilter {

	Logger log = LoggerFactory.getLogger(ErrorFilter.class);

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return 20;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		// ctx.set("error.status_code",
		// HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		// ctx.set("error.exception", throwable.getCause());

		RequestContext ctx = RequestContext.getCurrentContext();
		RequestContext context = RequestContext.getCurrentContext();
		Throwable throwable = RequestContext.getCurrentContext().getThrowable();
		HttpServletRequest request = context.getRequest();
		String requestURL = request.getRequestURI();
		String proxy = (String) ctx.get("proxy");
		int responseStatusCode = context.getResponseStatusCode();
		String invokeMeta = "requestUri:[" + requestURL + "]"
				+ ",proxyServerId:[" + proxy + "],time:"
				+ System.currentTimeMillis();
		if (responseStatusCode != HttpStatus.OK.value()) {
			Result r = new Result();
			r.setCode(responseStatusCode);
			r.setDesc(invokeMeta);
			r.setMessage(throwable.getCause().getMessage());
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(400);
			ctx.setResponseBody(JSON.toJSONString(r));
		}
		log.info(invokeMeta);
		return null;
	}

}
