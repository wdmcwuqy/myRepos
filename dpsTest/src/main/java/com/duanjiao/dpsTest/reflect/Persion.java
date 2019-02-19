package com.duanjiao.dpsTest.reflect;

import java.util.HashMap;
import java.util.Map;

public class Persion {
	
	private String name ="duanjiao";
	
	private Map<String,String> map = new HashMap<String,String>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	{
		map.put("key", "TestValue");
	}
	
	
}
