package com.duanjiao.designPattern.responsibilityChain;

import com.duanjiao.designPattern.responsibilityChain.pojo.Request;
import com.duanjiao.designPattern.responsibilityChain.pojo.Result;

/**  
 * 接口描述：对request和Result封装，用来转发  
 */  
public interface Chain {  
     // 获取当前request  
     Request request();  

     // 转发request  
     Result proceed(Request request);  
}  