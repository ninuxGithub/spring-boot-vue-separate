package com.example.demo.commandline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
	public static void main(String[] args) {
		Map<String,String> map = new ConcurrentHashMap<>(2);
		map.put("aaa", "aa");
		map.put("aaa1", "aa");
		map.put("aaa2", "aa");
		map.put("aaa3", "aa");
		map.put("aaa4", "aa");
		map.put("aaa5", "aa");
		map.put("aaa6", "aa");
		
		System.out.println(map);
	}

}
