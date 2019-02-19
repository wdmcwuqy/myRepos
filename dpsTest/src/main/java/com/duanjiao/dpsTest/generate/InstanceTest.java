package com.duanjiao.dpsTest.generate;

import org.junit.Test;

public class InstanceTest {
	
	@Test
	public void test() {
		
	B b =new B();
	
	A a =new B();
	

	System.out.println(A.class.isAssignableFrom(B.class));
	System.out.println(A.class.isAssignableFrom(b.getClass()));

		

	
	
		
	}
	
	interface A {
		
	}
	
	class B implements A{
		
	}
}
