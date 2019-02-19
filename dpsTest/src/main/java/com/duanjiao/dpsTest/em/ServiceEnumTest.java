package com.duanjiao.dpsTest.em;

import org.junit.Test;

public class ServiceEnumTest {
	
	@Test
	public void test() {
		
		ServiceEnum userService =ServiceEnum.USER_SERVICE;
		
		userService.service("12345");
	}
	
}
