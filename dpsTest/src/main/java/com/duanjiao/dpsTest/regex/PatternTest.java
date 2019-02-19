package com.duanjiao.dpsTest.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PatternTest {
	
	@Test
	public void test1() {
		 Pattern p = Pattern.compile("a*b");
		 Matcher m = p.matcher("aaaaab");
		 boolean b = m.matches();
		 System.out.println(b);
	}
	
}
