package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 
* @ClassName: ShiftRightZeroFillOperator
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:29:48
*
 */
public class ShiftRightZeroFillOperator {
	
	/**
	 * -2 二进制表示:10000010
	 * 补码:01111101
	 * 
	 * 
	 */
	private static int HASH_CODE = -2 >>> 3;
	
	private static long XINT = -1;
	
	@Test
	public void binaryOr() {
		
		
		
		String arch = System.getProperty("os.arch");
		String os= System.getProperty("os.name");

		System.out.println(arch);
		System.out.println(os); 
		
		System.out.println("Binary Xor Operator :"+HASH_CODE);
		
		System.out.println("Binary Xor Operator :"+Integer.toBinaryString(HASH_CODE));
		System.out.println("Binary Xor Operator :"+Integer.toBinaryString(HASH_CODE).length());
		System.out.println("Binary Xor Operator :"+Long.toBinaryString(XINT));
		System.out.println("Binary Xor Operator :"+Long.toBinaryString(XINT).length());
	}
}
