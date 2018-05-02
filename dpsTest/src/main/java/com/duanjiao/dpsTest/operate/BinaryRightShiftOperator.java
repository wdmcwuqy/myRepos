package com.duanjiao.dpsTest.operate;

import org.junit.Test;

/**
 * 左移运算符(>>)
* @ClassName: BinaryRightShiftOperator
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月26日 下午5:22:17
*
 */
public class BinaryRightShiftOperator {
	
private static final int HASH_CODE = 0b1000000 >> 5;
	
	@Test
	public void shifValueTest() {
		
		System.out.println("this is shift value:"+Integer.toBinaryString(HASH_CODE));
	}
}
