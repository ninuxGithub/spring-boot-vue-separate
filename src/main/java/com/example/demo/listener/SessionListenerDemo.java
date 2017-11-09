package com.example.demo.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListenerDemo implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.err.println("3.session listener..");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.err.println("session 被销毁");

	}

}
