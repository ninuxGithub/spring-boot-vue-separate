package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class BaseAction {

	/** 配置常量查询action */
	protected static final String QUERY_ACTION = "query";

	/** 配置常量创建action */
	protected static final String CREATE_ACTION = "create";

	/** 配置常量修改action */
	protected static final String UPDATE_ACTION = "update";

	/** 配置常量删除action */
	protected static final String DELETE_ACTION = "delete";

	/** 分页默认的每页页数 */
	@Value(("${paginatio.max-per-page}"))
	Integer maxPerPage;

}
