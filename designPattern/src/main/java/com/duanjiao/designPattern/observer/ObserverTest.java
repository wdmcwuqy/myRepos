package com.duanjiao.designPattern.observer;

import org.junit.Test;

import com.duanjiao.designPattern.observer.impl.User;
import com.duanjiao.designPattern.observer.impl.WechatServer;

public class ObserverTest {
	
	
	@Test
	public void observerTest(){
		 	WechatServer server = new WechatServer();
	        
	        Observer userZhang = new User("ZhangSan");
	        Observer userLi = new User("LiSi");
	        Observer userWang = new User("WangWu");
	        
	        server.registerObserver(userZhang);
	        server.registerObserver(userLi);
	        server.registerObserver(userWang);
	        server.setInfomation("PHP是世界上最好用的语言！");
	        
	        System.out.println("----------------------------------------------");
	        server.removeObserver(userZhang);
	        server.setInfomation("JAVA是世界上最好用的语言！");
	}
}
