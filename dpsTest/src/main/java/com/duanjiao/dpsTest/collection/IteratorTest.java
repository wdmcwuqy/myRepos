package com.duanjiao.dpsTest.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class IteratorTest {
	
	public static List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	
	
	static {
		Map<String,String> map =new HashMap<String,String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		map.put("key4", "value4");
		map.put("key5", "value5");
		
		list.add(map);
	}
	@Test
	public void  checkChange(){
		
		 Iterator<Map<String, String>> iterator = list.iterator();
		 while(iterator.hasNext()) {
			
			 Map<String,String> map =iterator.next();
			 map.put("key5", "value55");
			 
			
		 }
		 
		 System.out.println(list);
	}
}
