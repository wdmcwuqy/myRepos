package com.duanjiao.dpsTest.generate;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SubListTest {
	
	public List<String> list =Arrays.asList("AAA");
	
	@Test
	public void test () {
		
		System.out.println(list.size()/2);
		
		for(int i =0; i < (list.size()/2+1);i++) {
			
			List<String> subList =list.subList(i*2, (i+1)*2 > list.size()?list.size():(i+1)*2);
			System.out.println(i +"--------------");
			subList.forEach(str->System.out.println(str));
			
			
		}
		
	}

}
