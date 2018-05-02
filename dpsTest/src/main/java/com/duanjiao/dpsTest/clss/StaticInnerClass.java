package com.duanjiao.dpsTest.clss;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class StaticInnerClass {
	
	private InnerTest inner;
	

	public StaticInnerClass(String innerString) {
		this.inner = InnerTest.newInstance(innerString);
	}
	
	public static void main(String[] args) {
		
		StaticInnerClass inner1 = new StaticInnerClass("AAA");
		StaticInnerClass inner2 = new StaticInnerClass("AAA");
		
		System.out.println(inner1.inner.hashCode());
		System.out.println(inner2.inner.hashCode());
		System.out.println(inner2.inner == inner1.inner);
	}

	
	
	private static class InnerTest{
		
		 private final static Map<String, InnerTest>  INSTANCE_MAP = 
	        		new ConcurrentHashMap<String, InnerTest>();
		
		private static int staticValue = 9;
		
		private int generalValue = 0;

		public int getGeneralValue() {
			return generalValue;
		}

		public void setGeneralValue(int generalValue) {
			this.generalValue = generalValue;
		}
		
		static InnerTest newInstance(String executorService){
			InnerTest token = INSTANCE_MAP.get(executorService);
            if(null == token){
                token = new InnerTest();
                InnerTest existingToken = INSTANCE_MAP.putIfAbsent(executorService, token);

                if(null != existingToken){
                    token = existingToken;
                }
            }
            return token;
        }
	}
}	
