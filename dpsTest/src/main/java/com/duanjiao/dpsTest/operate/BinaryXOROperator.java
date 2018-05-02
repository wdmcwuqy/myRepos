package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 运算符 异或(^)
* @ClassName: BinaryXOROperator
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:11:25
*
 */
public class BinaryXOROperator {
	
private static int HASH_CODE = 0b00011001 ^ 0b01110100;
	
	@Test
	public void binaryXor() {
		
		System.out.println("Binary Xor Operator :"+Integer.toBinaryString(HASH_CODE));
	}
	
}
