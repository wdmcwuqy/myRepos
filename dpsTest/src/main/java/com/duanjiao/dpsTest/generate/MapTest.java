package com.duanjiao.dpsTest.generate;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class MapTest {
	
	public static Map<String,String> map = new HashMap<String,String>();
	
	static {
		map.put("key", "value");
	}
	
	@Test
	public void testMapKey(){
		
		String testValue = map.get("Test");
		
		System.out.println(testValue);
		
		
		String keyValue = map.get("key");
		
		System.out.println(keyValue);
		
		Boolean isTest = map.containsKey("Test");
		System.out.println(isTest);
		Boolean isKey = map.containsKey("key");
		System.out.println(isKey);
	}
}
