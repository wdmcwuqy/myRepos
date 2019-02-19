package com.duanjiao.dpsTest.generate;

import java.util.HashMap;
import java.util.Map;

public class StaticWordTest {
	
	private String  a;
	
	private final String  b;
	
	private static String c;
	
	private static final String d;
	
	private static Map<String,String> map;
	
	
	public StaticWordTest(String m){
		System.out.println(" ���캯�� ���� ---------");
		
		map =new HashMap<String,String>();
		map.put("AAA", "AAA");
		
	}
	
	{
		System.out.println(" ��ͨ �� ���� ---------");
		a = "1";
		
		b ="2";
		//c ="3";
		
	}
	static {
		System.out.println(" ��̬ �� ���� ---------");
		//a ="11";
		//b ="22";
		c ="33";
		d ="44";
	}
	
	public static void printMap() {
		
	  System.out.println(" laod Map :"+map);
	  
	}
	
	public static void main(String[] args) {
		
		StaticWordTest.printMap();
		
		new StaticWordTest("AA");
		
		StaticWordTest.printMap();
	}

}
