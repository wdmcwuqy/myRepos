package com.duanjiao.dpsTest.string;

import org.junit.Test;

public class StringTest {

	@Test
	public void stringTest1() {
		String str1 = "string";
		String str2 = new String("string");
		String str3 = str2.intern();
		System.out.println(str1 == str2);// #1
		System.out.println(str1 == str3);// #2
	}

	@Test
	public void stringTest2() {
		String baseStr = "baseStr";
		final String baseFinalStr = "baseStr";

		String str1 = "baseStr01";
		String str2 = "baseStr" + "01";
		String str3 = baseStr + "01";
		String str4 = baseFinalStr + "01";
		String str5 = new String("baseStr01").intern();

		System.out.println(str1 == str2);// #3
		System.out.println(str1 == str3);// #4
		System.out.println(str1 == str4);// #5
		System.out.println(str1 == str5);// #6
	}

	@Test
	public void stringTest3() {
		String str2 = new String("str") + new String("01");
		str2.intern();
		String str1 = "str01";
		System.out.println(str2 == str1);// #7
	}

	@Test
	public void stringTest4() {
		String str1 = "str01";
		String str2 = new String("str") + new String("01");
		str2.intern();
		System.out.println(str2 == str1);// #8
	}
	
	@Test
	public void stringTest5() {
		String str1 = "str01";
		
		System.out.println(str1.contains("str"));// #8
		System.out.println(str1.contains("str123"));// #8
		System.out.println(str1.contains("st"));// #8
		System.out.println(str1.contains("str0"));// #8
	}
}
