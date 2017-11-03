package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.Student;
import com.example.demo.repository.StudentRepository;

@RestController
public class StudentController extends BaseController<Student> {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private StudentRepository studentRepository;

	@SuppressWarnings("serial")
	@RequestMapping(value = "/studentinfo", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Student> getStudents(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "per_page", required = false) Integer perPage,
			@RequestParam(value = "sorts", required = false) String sortType,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "score", required = false) Double score) {
		return queryPagination(page, perPage, sortType, new HashMap<String, Object>() {
			{
				put("name", name);
				put("number", number);
				put("score", score);

			}
		}, StudentRepository.class);
	}
	
	@RequestMapping(value = "/studentinfo", method = {RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveStudent(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "score", required = false) Double score,
			@RequestParam(value = "createTime", required = false) Date createTime,
			@RequestParam(value = "hobby", required = false) String hobby,
			@RequestParam(value = "active", required = false) Integer active) {
		logger.info("save Student...");
		System.out.println(active);
		System.out.println(createTime);
		studentRepository.save(new Student(id, name, number, score,createTime,hobby,active));
	}
	@RequestMapping(value = "/studentinfo", method = {RequestMethod.PATCH }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void modifyStudent(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "score", required = false) Double score,
			@RequestParam(value = "createTime", required = false) Date createTime,
			@RequestParam(value = "hobby", required = false) String hobby,
			@RequestParam(value = "active", required = false) Integer active) {
		logger.info("update Student...");
		Student stu = studentRepository.findOne(id);
		stu.setName(name);
		stu.setNumber(number);
		stu.setScore(score);
		stu.setCreateTime(createTime);
		stu.setHobby(hobby);
		stu.setActive(active);
		studentRepository.save(stu);
	}
	
	@RequestMapping(value = "/studentinfo/{id}", method = {RequestMethod.DELETE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteStudent(@PathVariable("id") String id) {
		logger.info("delete Student...");
		if(StringUtils.isNotBlank(id)) {
			studentRepository.delete(id);
		}
	}
	@RequestMapping(value = "/studentinfo", method = {RequestMethod.DELETE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteStudents(@RequestParam(value="ids",required=true) String[] ids) {
		logger.info("delete Students...");
		for (String id : ids) {
			studentRepository.delete(id);
		}
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
