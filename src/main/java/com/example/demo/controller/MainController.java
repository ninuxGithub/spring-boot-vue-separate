package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.PageNumber;
import com.example.demo.bean.Persons;
import com.example.demo.repository.PersonsRepository;

@RestController
@RequestMapping("/api/persons")
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private PersonsRepository personsRepository;

	@Value(("${paginatio.max-per-page}"))
	Integer maxPerPage;

	@RequestMapping(value = "/sex", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSexAll() {
		logger.info("getSexAll");
		ArrayList<Map<String, String>> results = new ArrayList<>();

		for (Object value : personsRepository.findSex()) {

			Map<String, String> sex = new HashMap<>();

			sex.put("label", value.toString());

			sex.put("value", value.toString());

			results.add(sex);
		}

		ResponseEntity<ArrayList<Map<String, String>>> responseEntity = new ResponseEntity<>(results, HttpStatus.OK);

		return responseEntity;
	}


	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Persons> jpaSearch(HttpSession session,@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "sex",required=false) final String sex, @RequestParam(value="email",required=false) final String email) {
		logger.info("jpaSearch");
		if (page == null) {
			page = 1;
		}
		Sort sort = new Sort(Direction.ASC, "id");
		
		Pageable pageable = new PageRequest(page - 1, maxPerPage, sort);
		
		Page<Persons> pagination =  personsRepository.findAll(new Specification<Persons>() {
			@Override
			public Predicate toPredicate(Root<Persons> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicatList = new ArrayList<>();
				if (StringUtils.isNotBlank(sex)) {
					predicatList.add(cb.like(root.get("sex"), "%" + sex + "%"));
				}
				if (StringUtils.isNotBlank(email)) {
					predicatList.add(cb.like(root.get("email"), "%" + email + "%"));
				}
				
				Predicate[] arrayPredicates = new Predicate[predicatList.size()];
				return cb.and(predicatList.toArray(arrayPredicates));
			}
		}, pageable);
		
//		System.out.println("num:" + pagination.getNumber());
//		System.out.println("size:" + pagination.getSize());
//		System.out.println("content:" + pagination.getContent());
//		System.out.println("total page:" + pagination.getTotalPages());
//		System.out.println("total Elements:" + pagination.getTotalElements());
//		System.out.println("hasNext:" + pagination.hasNext());
		
		return new PageNumber<>(pagination.getContent(), pageable, pagination.getTotalElements());
		
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persons> getUserDetail(@PathVariable Long id) {

		Persons user = personsRepository.findById(id);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Persons updateUser(@PathVariable Long id, @RequestBody Persons data) {

		Persons user = personsRepository.findById(id);

		user.setPhone(data.getPhone());

		user.setZone(data.getZone());

		return personsRepository.save(user);
	}

}