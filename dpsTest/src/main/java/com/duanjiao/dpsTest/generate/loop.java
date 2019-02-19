package com.duanjiao.dpsTest.generate;

public class loop {
	

	
	public static void main(String[] args) {
		
		
	 int num =0;
	 int num2 =0;
	 
	 String en ="AAA";

	 

	switch (en) {
	case "AAA":
			System.out.println("AAA");
	case "BBB":
			System.out.println("BBB");
			break;
	case "CCC":
			System.out.println("CCC");
	default:
		System.out.println("NO");
		break;
	}
	 
		
	 for(;;) {
			num++;
			System.out.println("for loop 1 "+num);
			if(num == 1000) {
				break;
		}
	 }
	 
	label:for(;;) {
			for(;;) {
				num2++;
				System.out.println("for loop 2 "+num2);
				if(num2 == 1000) {
					break label;
				}
			}
			
		}
	 
	 loop lop =new loop();
		 
	 	try {
			lop.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	
	 	Runtime.getRuntime().addShutdownHook( new Thread() {
	 		@Override
	 		public void run() {
	 			
	 			try {
					finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 			System.out.println("------");
	 		}
	 	});
	 	
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("����ִ��------");
		super.finalize();
	}
}
