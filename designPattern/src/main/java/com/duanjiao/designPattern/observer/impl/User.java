package com.duanjiao.designPattern.observer.impl;

import com.duanjiao.designPattern.observer.Observer;

/**
 * 观察者模式--具体观察者对象
* @ClassName: User
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月28日 上午11:03:12
*
 */
public class User implements Observer{

    private String name;
    private String message;
    
    public User(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        this.message = message;
        read();
    }
    
    public void read() {
        System.out.println(name + " 收到推送消息： " + message);
    }
    
}
