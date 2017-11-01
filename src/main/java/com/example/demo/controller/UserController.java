package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.PageNumber;
import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/userinfo", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<User> getUsers(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "per_page", required = false) Integer perPage, 
			@RequestParam("name") final String name, 
			@RequestParam("phone") final String phone, 
			@RequestParam("username") final String username,
			@RequestParam("sorts") String sortType) {
		logger.info("user info...");
		if (page == null) {
			page = 1;
		}
		Sort sort =null;
		Direction direction = Direction.ASC;
		if(StringUtils.isBlank(sortType)) {
			sort = new Sort(direction, "id");
		}else {
			if(sortType.startsWith("-")) {
				direction = Direction.DESC;
				sortType = sortType.substring(1);
			}else{
				direction = Direction.ASC;
			}
			sort = new Sort(direction, sortType);
		}
		logger.info("sort field :{}", sortType);
		Pageable pageable = new PageRequest(page - 1, maxPerPage, sort);
		Page<User> pagination = null;
		pagination = queryPagination(name, phone, username, pageable);
		return new PageNumber<>(pagination.getContent(), pageable, pagination.getTotalElements());

	}
	@RequestMapping(value = "/userinfo", method = {RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveUser(
			@RequestParam(value = "checkpass",required=false) final String checkpass,
			@RequestParam(value="email",required=false) final String email,
			@RequestParam(value="id",required=false) final String id,
			@RequestParam(value="is_active",required=false) final boolean is_active,
			@RequestParam(value="name",required=false) final String name, 
			@RequestParam("phone") final String phone, 
			@RequestParam("username") final String username,
			@RequestParam("password") final String password,
			@RequestParam(value="create_time",required=false)  Date create_time ) {
		logger.info("save user...");
		if(null == create_time) {
			create_time = new Date();
		}
		User user = new User(id, username, username, phone, email, create_time, is_active, checkpass);
		userRepository.save(user);
	}
	@RequestMapping(value = "/userinfo", method = {RequestMethod.PATCH }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(
			@RequestParam(value = "checkpass",required=false) final String checkpass,
			@RequestParam(value="email",required=false) final String email,
			@RequestParam(value="id",required=false) final String id,
			@RequestParam(value="is_active",required=false) final boolean is_active,
			@RequestParam(value="name",required=false) final String name, 
			@RequestParam(value="phone",required=false) final String phone, 
			@RequestParam(value="username",required=false) final String username,
			@RequestParam(value="password",required=false) final String password,
			@RequestParam(value="create_time",required=false) final Date create_time ) {
		logger.info("update user...");
		User user = userRepository.findOne(id);
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(email);
		userRepository.save(user);
	}
	@RequestMapping(value = "/userinfo/{id}", method = {RequestMethod.DELETE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id") final String id) {
		logger.info("delete user...");
		userRepository.delete(id);
	}
	@RequestMapping(value = "/userinfo", method = {RequestMethod.DELETE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete2(@RequestParam("ids") final String[] ids) {
		logger.info("批量删除 user...");
		System.out.println(ids.length);
		for (String id : ids) {
			userRepository.delete(id);
		}
	}

	private Page<User> queryPagination(final String name, final String phone, final String username,
			Pageable pageable) {
		Page<User> pagination;
		pagination = userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicatList = new ArrayList<>();
				if (StringUtils.isNotBlank(name)) {
					predicatList.add(cb.like(root.get("name"), "%" + name + "%"));
				}
				if (StringUtils.isNotBlank(phone)) {
					predicatList.add(cb.like(root.get("phone"), "%" + phone + "%"));
				}
				if (StringUtils.isNotBlank(username)) {
					predicatList.add(cb.like(root.get("username"), "%" + username + "%"));
				}

				Predicate[] arrayPredicates = new Predicate[predicatList.size()];
				return cb.and(predicatList.toArray(arrayPredicates));
			}
		}, pageable);
		return pagination;
	}
}
