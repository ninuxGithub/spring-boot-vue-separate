package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Controller;

import com.example.demo.bean.PageNumber;
import com.example.demo.utils.SpringUtil;

@Controller
public class BaseController<T> {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/** 分页默认的每页页数 */
	@Value(("${paginatio.max-per-page}"))
	protected Integer maxPerPage;

	@Autowired
	private SpringUtil springUtil;

	/**
	 * @param page
	 *            第几页
	 * @param perPage
	 *            每页的记录条数
	 * @param sortType
	 *            排序的字段
	 * @param params
	 *            排序的map
	 * @param beanClass
	 *            bean对应的repository
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public Page<T> queryPagination(Integer page, Integer perPage, String sortType, Map<String, Object> params, Class<?> beanClass) {
		if(beanClass ==null) {
			throw new RuntimeException("[queryPagination] beanClass is required!");
		}
		if (page == null) {
			page = 1;
		}
		Sort sort ;
		Direction direction = Direction.ASC;
		if (StringUtils.isBlank(sortType)) {
			sort = new Sort(direction, "id");
		} else {
			if (sortType.startsWith("-")) {
				direction = Direction.DESC;
				sortType = sortType.substring(1);
			}
			sort = new Sort(direction, sortType);
		}
		// 创建pageable对象
		Pageable pageable = new PageRequest(page - 1, perPage == null ? maxPerPage : perPage, sort);
		// 开始获取分页的JpaSpecificationExecutor
		JpaSpecificationExecutor<T> bean = (JpaSpecificationExecutor<T>) springUtil.getBean(beanClass);
		logger.info("sort field :{} queryPagination run...", sortType);
		// 开始分页
		//Lambda
		Page<T> pagination = bean.findAll((Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)->{
			List<Predicate> predicatList = new ArrayList<>();
			for (String field : params.keySet()) {
				Object value = params.get(field);
				if (null != value && StringUtils.isNotBlank(value.toString())) {
					//有些字段是不可以采用like的 例如Double ...
					predicatList.add(cb.like(root.get(field), "%" + value + "%"));
				}
			}
			Predicate[] arrayPredicates = new Predicate[predicatList.size()];
			return cb.and(predicatList.toArray(arrayPredicates));
		},pageable);


		/*
		Page<T> pagination = bean.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicatList = new ArrayList<>();
				for (String field : params.keySet()) {
					Object value = params.get(field);
					if (null != value && StringUtils.isNotBlank(value.toString())) {
						//有些字段是不可以采用like的 例如Double ...
						predicatList.add(cb.like(root.get(field), "%" + value + "%"));
					}
				}
				Predicate[] arrayPredicates = new Predicate[predicatList.size()];
				return cb.and(predicatList.toArray(arrayPredicates));
			}
		}, pageable);
		 */
		// 对分页的PageNumber 进行调整
		return new PageNumber<>(pagination.getContent(), pageable, pagination.getTotalElements());
	}
}
