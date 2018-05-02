package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 运算符 与(&)
* @ClassName: BinaryANDOperator
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:12:01
*
 */
public class BinaryANDOperator {
	
	
	private static int HASH_CODE = 0b00011001 ^ 0b01110100;
	
	@Test
	public void binaryAnd() {
		
		System.out.println("Binary And Operator :"+Integer.toBinaryString(HASH_CODE));
	}
	
}
