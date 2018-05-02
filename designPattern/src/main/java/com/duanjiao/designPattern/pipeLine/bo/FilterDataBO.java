package com.duanjiao.designPattern.pipeLine.bo;

import java.util.concurrent.TimeUnit;

import com.duanjiao.designPattern.pipeLine.PipeException;
import com.duanjiao.designPattern.pipeLine.impl.AbsractPipe;

/**
 * first step
 * 过滤数据
 * 
* @ClassName: filterDataBO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author duanjiao
* @date 2018年4月28日 下午3:07:23
*
 */
public class FilterDataBO extends AbsractPipe<String,String>{

	@Override
	public String doProcess(String input) throws PipeException {
		
		System.out.println(Thread.currentThread().getName()+" FilterDataBO input:"+input );
		
		return input.replace("pipe", "管道");
	}

	@Override
	public void shutdown(long timeout, TimeUnit unit) {
		
		
		System.out.println("FilterDataBO shutdown !");
		
		super.shutdown(timeout, unit);
	}
}
