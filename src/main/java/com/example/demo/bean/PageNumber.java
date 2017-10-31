package com.example.demo.bean;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageNumber<T> extends PageImpl<T> implements Page<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4457024920612570312L;

	/**
	 * 前台传参1为起始页修改为spring data jpa的0为起始页
	 * 
	 * @param pageable
	 * @return
	 */
	public static Pageable pageRequest(Pageable pageable) {
		return new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
	}

	/**
	 * Constructor of {@code PageImpl}.
	 *
	 * @param content
	 *            the content of this page, must not be {@literal null}.
	 * @param pageable
	 *            the paging information, can be {@literal null}.
	 * @param total
	 *            the total amount of items available. The total might be adapted
	 *            considering the length of the content
	 */
	public PageNumber(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	/**
	 * 重写当前页，将当前页加1返回前台，spring data jpa起始页0加1后返回前台
	 * 
	 * @return
	 */
	@Override
	public int getNumber() {
		return super.getNumber() + 1;
	}

}
