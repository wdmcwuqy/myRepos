package com.duanjiao.dpsTest.generate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpWards {
	
	public static void main(String[] args) {
		
		LinkedHashMap<String,String> linkMap =new LinkedHashMap<String,String>();
		
		linkMap.put("AAA", "AAA");
		linkMap.put("BBB", "BBB");
		linkMap.put("CCC", "CCC");
		linkMap.put("EEE", "EEE");
		Map<String,String> map =new HashMap<String,String>();
		map.putAll(linkMap);
		
		System.out.println(map instanceof HashMap);
		System.out.println(map instanceof LinkedHashMap);
		
		
		
	}

}
