package com.duanjiao.dpsTest.multithreading;

public class FutureClient {
	
	public Data getRequset(final String queryStr) {
		// 初始化代理对象，先返回给客户端
		final FutureData futureData = new FutureData();
		// 启动一个新的线程去加载真实的数据，传递给这个代理对象
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 此线程去加载真实对象,然后传递给代理对象
				RealData realData = new RealData(queryStr);
				futureData.setRealData(realData);
			}
		}).start();
		System.out.println("代理对象返回:" + futureData);
		return futureData;
	}

	public static void main(String[] args) {
		FutureClient fc = new FutureClient();
		Data data = fc.getRequset("jianzh5");
		System.out.println("请求完毕...");
		String result = data.getRequest();
		System.out.println("返回的结果:" + result);
	}
}