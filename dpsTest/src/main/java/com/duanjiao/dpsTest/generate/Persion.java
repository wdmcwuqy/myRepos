package com.duanjiao.dpsTest.generate;

public class Persion{
	
	private static Persion persion = new Persion();
	
	public static Persion getInstance() {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(persion == null) {
			
			System.out.println("null ---------- ");
			
			persion = new Persion();
		}
		return persion;
	}
	
	private String name;
	
	private int age;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
