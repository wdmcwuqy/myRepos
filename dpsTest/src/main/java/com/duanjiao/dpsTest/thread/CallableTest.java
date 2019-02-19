package com.duanjiao.dpsTest.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
/**
 * @author zejian
 * @time 2016��3��15�� ����2:05:43
 * @decrition callableִ�в�����
 */
public class CallableTest {
	
	public static void main(String[] args) {
//		//�����̳߳�
//		ExecutorService es = Executors.newSingleThreadExecutor();
//		//����Callable��������
//		CallableDemo calTask=new CallableDemo();
//		//�ύ���񲢻�ȡִ�н��
//		Future<Integer> future =es.submit(calTask);
//		//�ر��̳߳�
//		es.shutdown();
		
		//�����̳߳�
		ExecutorService es = Executors.newSingleThreadExecutor();
		//����Callable��������
		CallableDemo calTask = new CallableDemo();
		//����FutureTask
		FutureTask<Integer> futureTask=new FutureTask<>(calTask);
		//ִ������
		es.submit(futureTask);
		//�ر��̳߳�
		//es.shutdown();
		try {
			//Thread.sleep(2000);
		System.out.println("���߳���ִ����������");
		
		
		if(futureTask.get()!=null){
			//�����ȡ���Ľ��
			System.out.println("futureTask.get()-->"+futureTask.get());
		}else{
			//�����ȡ���Ľ��
			System.out.println("futureTask.get()δ��ȡ�����");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("���߳���ִ�����");
	}

}