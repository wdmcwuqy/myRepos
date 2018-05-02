package com.duanjiao.designPattern.responsibilityChain;

import com.duanjiao.designPattern.responsibilityChain.pojo.Request;
import com.duanjiao.designPattern.responsibilityChain.pojo.Result;

public class ChainMainTest {
	
	public static void main(String[] args) {  
		  
        Request request = new Request.Builder().setName("张三").setDays(5).setReason("事假").build();  
        ChainOfResponsibilityClient client = new ChainOfResponsibilityClient();  
        Result result = client.execute(request);  

        System.out.println("结果：" + result.toString());  
   }   
}
