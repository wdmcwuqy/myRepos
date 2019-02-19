package com.duanjiao.dpsTest.list;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class Linklist {
	
	private static  List<String> list = new LinkedList();
	
	static {
		list.add("AAA");
		list.add("BBB");
	}
	
	@Test
	public void tetsLinklist() {
		list.add(0, "CCC");
		
		list.add(list.size(), "DDD");
		
		list.stream().forEach(System.out::println);
		System.out.println("----");
		
		
		list.remove("AAA");
		list.stream().forEach(System.out::println);
		System.out.println("----");
		list.remove(1);
		list.stream().forEach(System.out::println);
	}
}
