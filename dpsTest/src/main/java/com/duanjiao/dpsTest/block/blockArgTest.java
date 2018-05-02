package com.duanjiao.dpsTest.block;

import org.junit.Test;

public class blockArgTest {
	
	@Test
	public void ifBlock() {
		
		String str ="111";
		 int strLen=0;
	        if (str == null || (strLen = str.length()) == 0) {
	            System.out.println(" this str is null");
	        }
	      System.out.println("this str length:"+Integer.valueOf(strLen));
	}
}
