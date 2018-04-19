package com.github.caoyeung.filter;

public class AccessTokenFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		return false;
	}

	@Override
	public Object run() {
		return null;
	}

	@Override
	public String filterType() {
		return null;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
