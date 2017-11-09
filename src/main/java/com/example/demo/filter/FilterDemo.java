package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = "/*", filterName = "apiFilter")
public class FilterDemo implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(FilterDemo.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.err.println("过滤器FilterDemo初始化");
		logger.info("我是过滤器");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.err.println("1.经过过滤器");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
