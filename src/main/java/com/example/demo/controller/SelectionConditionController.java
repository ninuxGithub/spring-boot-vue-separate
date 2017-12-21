package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.SelectCondition;

@Controller
@RequestMapping("/api")
public class SelectionConditionController {

	
	public List<SelectCondition> loadConditions(){
		List<SelectCondition> list= new LinkedList<>();
		
		return list;
	}
}
