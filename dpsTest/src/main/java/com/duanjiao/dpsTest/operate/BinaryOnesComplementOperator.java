package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 运算符 非/补数(~)
* @ClassName: BinaryOnesComplementOperator
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:15:31
*
 */
public class BinaryOnesComplementOperator {
	
	private static int HASH_CODE = ~ 0b01110100;
	
	@Test
	public void binaryOr() {
		
		System.out.println("Binary Xor Operator :"+Integer.toBinaryString(HASH_CODE));
	}
	
}
