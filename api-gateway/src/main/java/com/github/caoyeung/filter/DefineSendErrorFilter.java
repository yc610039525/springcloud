package com.github.caoyeung.filter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
@Component
public class DefineSendErrorFilter extends SendErrorFilter {
	private static final Log log = LogFactory.getLog(SendErrorFilter.class);
	@Value("${error.path:/error}")
	private String errorPath;
	@Override
	public Object run() {
		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			ZuulException exception = findZuulException(ctx.getThrowable());
			HttpServletRequest request = ctx.getRequest();

			request.setAttribute("javax.servlet.error.status_code", exception.nStatusCode);

			log.warn("Error during filtering", exception);
			request.setAttribute("javax.servlet.error.exception", exception);

			if (StringUtils.hasText(exception.errorCause)) {
				request.setAttribute("javax.servlet.error.message", exception.errorCause);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(
					this.errorPath);
			if (dispatcher != null) {
				ctx.set(SEND_ERROR_FILTER_RAN, true);
				if (!ctx.getResponse().isCommitted()) {
					dispatcher.forward(request, ctx.getResponse());
				}
			}
		}
		catch (Exception ex) {
			ReflectionUtils.rethrowRuntimeException(ex);
		}
		return null;
	}
	ZuulException findZuulException(Throwable throwable) {
		if (throwable.getCause() instanceof ZuulRuntimeException) {
			// this was a failure initiated by one of the local filters
			return (ZuulException) throwable.getCause().getCause();
		}

		if (throwable.getCause() instanceof ZuulException) {
			// wrapped zuul exception
			return (ZuulException) throwable.getCause();
		}

		if (throwable instanceof ZuulException) {
			// exception thrown by zuul lifecycle
			return (ZuulException) throwable;
		}

		// fallback, should never get here
		return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
	}
}
