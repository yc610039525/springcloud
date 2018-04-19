package com.github.caoyeung.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class TestThrowExceptionPreFilter extends ZuulFilter  {
    private static Logger log = LoggerFactory.getLogger(TestThrowExceptionPreFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            doSomething();
        } catch (Exception e) {
            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception", e);
            ctx.set("error.message", "有一些错误发生");
        }
        return null;
    }

    private void doSomething() {
        throw new RuntimeException("Exist some Pre errors...");
    }

}
