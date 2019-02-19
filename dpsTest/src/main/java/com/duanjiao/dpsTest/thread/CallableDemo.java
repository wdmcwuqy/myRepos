package com.duanjiao.dpsTest.thread;

import java.util.concurrent.Callable;
/**
 * @author zejian
 * @time 2016��3��15�� ����2:02:42
 * @decrition Callable�ӿ�ʵ��
 */
public class CallableDemo implements Callable<Integer> {
	
	private int sum;
	@Override
	public Integer call() throws Exception {
		System.out.println("Callable���߳̿�ʼ��������");
		Thread.sleep(2000);
		
		for(int i=0 ;i<5000;i++){
			sum=sum+i;
		}
		System.out.println("Callable���̼߳��������");
		return sum;
	}
}
