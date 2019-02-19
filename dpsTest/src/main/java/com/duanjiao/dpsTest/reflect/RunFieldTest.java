package com.duanjiao.dpsTest.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RunFieldTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void runTimeGet(){
	
		
		
		try {
			 Persion sampleObject = new Persion();
		      
		      Field field = Persion.class.getDeclaredField("map");
		      field.setAccessible(true);
		      Map<String,String>  map =(Map<String, String>) field.get(sampleObject);
		      
		      System.out.println(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
