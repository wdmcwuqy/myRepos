package com.duanjiao.dpsTest.reflex;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

import com.duanjiao.dpsTest.ChildClass;

public class ReflexFiledTest {


	public static void main(String[] args) {
		
		ChildClass child = new ChildClass();
		
		Field  coreAttrMapField =ReflectionUtils.getDeclaredField(child, "coreAttrMap");
		if(coreAttrMapField != null) {
			coreAttrMapField.setAccessible(true);
			try {
				Map<String, Serializable> coreAttrMap =(Map<String, Serializable>) coreAttrMapField.get(child);
				coreAttrMap.put("1", 22);
			} catch (Exception err) {
			} 
		}
		
		System.out.println(child);
	}
}


