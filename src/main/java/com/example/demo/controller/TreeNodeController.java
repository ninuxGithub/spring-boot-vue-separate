package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.TreeNode;
import com.example.demo.repository.TreeNodeRepository;

@Controller
@RequestMapping(value="/api")
public class TreeNodeController {

	@Autowired
	private TreeNodeRepository treeNodeRepository;
	
	
	@RequestMapping(value="/loadTree")
	@ResponseBody
	public List<TreeNode> loadTree() {
//		List<TreeNode> treeNodeList = treeNodeRepository.findAll();
//		return treeNodeList;
		return null;
	}
}
