package com.duanjiao.dpsTest.generate;

import org.junit.Test;

public class StringTest {
	
	
	@Test
	public void tets(){
		
		String LIKE_REG ="^%.*%$";
		
		String value ="%asd%";
		if(value.matches(LIKE_REG)) {
			value =value.substring(1, value.length()-1);
		}
		
		System.out.println("value:"+value);
	}
	
}
