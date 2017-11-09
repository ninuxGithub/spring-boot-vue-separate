package com.example.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerDemo implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.err.println("-1.ServletContextListener init");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.err.println("ServletContextListener destroy");

	}

}
