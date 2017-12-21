package com.example.demo.bean;

import java.io.Serializable;

public class SelectCondition implements Serializable {

	private static final long serialVersionUID = -1615064653013084687L;

	private Long id;
	private String content;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
