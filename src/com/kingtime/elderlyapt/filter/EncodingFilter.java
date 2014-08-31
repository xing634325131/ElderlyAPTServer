package com.kingtime.elderlyapt.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if ("GET".equals(req.getMethod())) {
			EncodingRequestWrapper wrapper = new EncodingRequestWrapper(req);
			chain.doFilter(wrapper, response);
		} else {
			//System.out.println("cahrset");
			req.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
