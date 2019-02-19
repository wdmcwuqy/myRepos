package com.duanjiao.dpsTest.generate;

public class staticKeyword {
	
	
	public static void main(String[] args) {
		
		tetsStatic tets =new tetsStatic();
		tetsStatic tets1 =new tetsStatic();
		tetsStatic tets2=new tetsStatic();
		tetsStatic tets3 =new tetsStatic();
		tetsStatic tets4 =new tetsStatic();
		
		System.out.println(tets == tets1);
		System.out.println(tets1 == tets2);
		System.out.println(tets2 == tets3);
		System.out.println(tets3 == tets4);
		
		System.out.println(tets.hashCode() == tets1.hashCode());
		System.out.println(tets1.hashCode() == tets2.hashCode());
		System.out.println(tets2.hashCode() == tets3.hashCode());
		
	
		
	}

	
	public static class tetsStatic{
		
		private String test ="Static test";
	}
}


