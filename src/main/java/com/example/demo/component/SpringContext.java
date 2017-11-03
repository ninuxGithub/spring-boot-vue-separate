package com.example.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(SpringContext.class);

	private static ApplicationContext springContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		springContext = applicationContext;
		logger.info("init SpringContext...");

	}

	public ApplicationContext getSpringContext() {
		return springContext;
	}

}
