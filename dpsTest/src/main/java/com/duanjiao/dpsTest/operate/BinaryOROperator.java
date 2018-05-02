package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 运算符 或(|)
* @ClassName: BinaryOROperator
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:13:57
*
 */
public class BinaryOROperator {
	
	private static int HASH_CODE = 0b00011001 | 0b01110100;
	
	@Test
	public void binaryOr() {
		
		System.out.println("Binary Xor Operator :"+Integer.toBinaryString(HASH_CODE));
	}
	
}
