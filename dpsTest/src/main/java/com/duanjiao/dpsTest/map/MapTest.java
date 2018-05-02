package com.duanjiao.dpsTest.map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * java 1.8 map key is null 
 * map Node[] table init is null
 * 
* @ClassName: MapTest
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午6:00:03
*
 */
public class MapTest {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	static {
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value1");
	}
	
	
	@Test
	@SuppressWarnings("rawtypes")
	public void nullMapTest() {
		try {
			Class<HashMap> hashMap = (Class<HashMap>) Class.forName("java.util.HashMap");
			
			Field tableField =hashMap.getDeclaredField("table");
			
			tableField.setAccessible(true);
			
			map.put(null, "null Value");
			
			Object  node = tableField.get(map);
			
			System.out.println("this is step");
				
			
			System.out.println(Runtime.getRuntime().totalMemory());
			System.out.println(Runtime.getRuntime().freeMemory());
			System.out.println(Runtime.getRuntime().maxMemory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
