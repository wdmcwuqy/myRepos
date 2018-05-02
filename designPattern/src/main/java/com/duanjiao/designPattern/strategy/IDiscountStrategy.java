package com.duanjiao.designPattern.strategy;

/**
 * 打折策略接口
* @ClassName: IDiscountStrategy
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月27日 上午8:55:04
*
 */
public interface IDiscountStrategy {
	
	/**
	 * 折扣
	* @Title: rebate
	* @Description: TODO
	* @param @param money
	* @param @return
	* @return int
	* @author duanjiao
	* @date 2018年4月27日
	* @throws
	 */
	double rebate(double money);
	
}
