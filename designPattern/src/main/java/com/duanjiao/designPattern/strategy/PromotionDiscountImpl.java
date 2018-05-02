package com.duanjiao.designPattern.strategy;

/**
 * 促销活动 7折优惠
* @ClassName: PromotionDiscountImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月27日 上午8:58:07
*
 */
public class PromotionDiscountImpl implements IDiscountStrategy{
	
	/**
	 * 7 折优惠
	 */
	public double rebate(double money) {
		return money * 0.7;
	}

}
