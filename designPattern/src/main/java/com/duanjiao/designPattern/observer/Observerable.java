package com.duanjiao.designPattern.observer;

/**
 * 观察者模式--观察者--
* @ClassName: Observerable
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月28日 上午10:55:33
*
 */
public interface Observerable {
    
    public void registerObserver(Observer o);
    
    public void removeObserver(Observer o);
    
    public void notifyObserver();
    
}