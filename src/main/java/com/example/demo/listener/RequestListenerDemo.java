package com.example.demo.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListenerDemo implements ServletRequestListener{

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		System.err.println("request destroy");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		System.err.println("request init");
		
	}

}
