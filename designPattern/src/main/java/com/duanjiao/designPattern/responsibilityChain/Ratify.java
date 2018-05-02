package com.duanjiao.designPattern.responsibilityChain;

import com.duanjiao.designPattern.responsibilityChain.pojo.Request;
import com.duanjiao.designPattern.responsibilityChain.pojo.Result;

/**  
 * 接口描述：处理请求  
 *  
 * @author lzy  
 *  
 */  
public interface Ratify {  
     // 处理请求  
     public Result deal(Chain chain);  
}  
