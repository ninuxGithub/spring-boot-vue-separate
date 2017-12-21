package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/api")
public class TestVuex {

	@Autowired
	private UserRepository userRepository;

	@ResponseBody
	@RequestMapping(value = "/getUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> testVuex(@RequestParam("value") Integer value) {
		System.out.println(value);
		Map<String, Object> map = new HashMap<>();
		map.put("code", "200");
		List<User> list = userRepository.findAll();
		map.put("result", list);
		return map;
	}

}
