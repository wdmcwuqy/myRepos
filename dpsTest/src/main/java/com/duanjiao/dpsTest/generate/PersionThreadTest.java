package com.duanjiao.dpsTest.generate;

public class PersionThreadTest  extends Thread{
	
	public static void main(String[] args) {
		
		
		for(int i=0;i<10 ;i++) {
			new PersionThreadTest().start();;
		}
		
	}
	
	@Override
	public void run() {
	 Persion persion = Persion.getInstance();
	 try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 System.out.println(this.getName()+"----"+persion.hashCode());
	 
	}
}
