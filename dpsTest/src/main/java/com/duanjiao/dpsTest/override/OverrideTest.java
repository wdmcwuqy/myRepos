package com.duanjiao.dpsTest.override;

public class OverrideTest extends ParentTest{
	
	
	
	@Override
	public void test() {
		super.test();
		
		System.out.println("Override .....");
	}
	
	public static void main(String[] args) {
		OverrideTest over = new OverrideTest();
		over.test();
	}
}
