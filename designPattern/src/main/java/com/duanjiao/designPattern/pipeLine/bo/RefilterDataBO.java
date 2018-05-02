package com.duanjiao.designPattern.pipeLine.bo;

import java.util.concurrent.TimeUnit;

import com.duanjiao.designPattern.pipeLine.PipeException;
import com.duanjiao.designPattern.pipeLine.impl.AbsractPipe;

public class RefilterDataBO extends AbsractPipe<String,String>{

	@Override
	public String doProcess(String input) throws PipeException {
		
		System.out.println(Thread.currentThread().getName()+" RefilterDataBO input:"+input );
		
		return input.toUpperCase();
	}
	
	@Override
	public void shutdown(long timeout, TimeUnit unit) {
		
		
		System.out.println("RefilterDataBO shutdown !");
		
		super.shutdown(timeout, unit);
	}
}
