package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 运算符  右移(<<)
* @ClassName: ShiftOperators
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:12:35
*
 */
public class BinaryLeftShiftOperator {
	
	private static final int HASH_CODE = 1 << 30;
	
	@Test
	public void shifValueTest() {
		
		System.out.println("this is shift value:"+Integer.toBinaryString(HASH_CODE));
	}
}
