package com.duanjiao.designPattern.pipeLine.bo;

import java.util.concurrent.TimeUnit;

import com.duanjiao.designPattern.pipeLine.PipeException;
import com.duanjiao.designPattern.pipeLine.impl.AbsractPipe;

public class AssembleDataBO extends AbsractPipe<String,String>{

	@Override
	public String doProcess(String input) throws PipeException {
		
		System.out.println(Thread.currentThread().getName()+" AssembleDataBO input:"+input );
		
		return input+"完成";
	}

	@Override
	public void shutdown(long timeout, TimeUnit unit) {
		
		System.out.println("AssembleDataBO shutdown !");
		
		super.shutdown(timeout, unit);
	}
	
	
	
}
