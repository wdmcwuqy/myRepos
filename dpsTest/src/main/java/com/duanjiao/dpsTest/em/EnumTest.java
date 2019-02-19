package com.duanjiao.dpsTest.em;

import org.junit.Test;

public class EnumTest {
	
	@Test
	public void test1() {
		LoginResultStateEnum state1 = LoginResultStateEnum.enumByIndex(0);
		
		LoginResultStateEnum state2 = LoginResultStateEnum.enumByIndex(5);
		
		System.out.println(state1 == state2);
		
		System.out.println(state2 == state1);
	}
}
