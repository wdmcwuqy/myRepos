package com.duanjiao.dpsTest.date;

import java.util.Date;

import org.junit.Test;

public class DateTest {
	
	@Test
	public void test1() {
		
		Date date1 =new Date();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date2 =new Date();
		
		System.out.println(date2.getTime() -date1.getTime() );;
	}
}
