package com.duanjiao.dpsTest.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedThreadPool {
	
	//private volatile AtomicInteger operand = new AtomicInteger(0);
	private volatile int operand = 0;
	
	private volatile int temp = 0;
	
	private Boolean isTrue;
	
	private static final int THREAD_NUM =50;
	
	private static CountDownLatch latch =new CountDownLatch(THREAD_NUM);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ExecutorService executoreServ =	Executors.newFixedThreadPool(THREAD_NUM);	
		
		FixedThreadPool threadPool =new FixedThreadPool();
		
		for(int i=0;i < THREAD_NUM;i++) {
			executoreServ.execute( new Runnable() {
				@Override
				public void run() {
					for(int i =0;i < 10000;i++) {
						threadPool.add();
					}
					latch.countDown();
				}
			});
		}
		System.out.println(" operand :"+threadPool.operand);
		executoreServ.shutdown();
		System.out.println(" operand :"+threadPool.operand);
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(" operand :"+threadPool.operand);
	}
	
	private void add() {
		//operand.incrementAndGet();
		 temp = operand +1;
		 operand = temp;
	}
	
	
	private void notOperation() {
		isTrue = !isTrue;
	}
	
	
	
	
}
