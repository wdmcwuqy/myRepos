package com.duanjiao.dpsTest.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

public class StreamTest {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	private static List<String> list = new ArrayList<String>();
	
	static {
		map.put("AAA", "AAA");
		map.put("BBB", "BBB");
		map.put("CCC", "CCC");
		map.put("EEE", "EEE");
		map.put("FFF", "FFF");
		
		list.add("AAA");
		list.add("BBB");
		list.add("CCC");
		list.add("EEE");
	}
	
	@Test
	public void filter() {
		List<String> temp = list.stream().filter(new Predicate<String>() {
			@Override
			public boolean test(String t) {
				return t.equals("AAA");
			}
		}).collect(Collectors.toList());
		
		temp.stream().forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		});
		
	}
	
	@Test
	public void collect() {
		List<String> temp = list.stream().collect(Collectors.toList());
		temp.stream().forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		});
		
	}
	
	@Test
	public void limit() {
		List<String> temp = list.stream().limit(2).collect(Collectors.toList());
		temp.stream().forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		});
	}
	
	@Test
	public void joining() {
		String temp = list.stream().collect(Collectors.joining()).toString();
		
		System.out.println(temp);
	}
	
}
