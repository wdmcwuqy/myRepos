package com.duanjiao.dpsTest.reflect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class ClassName1{
	
}

class ClassName22 extends ClassName1{
	
}

public class ClassNameTest extends ClassName22 implements Serializable{
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		
		
		List<Class<?>> list =getsuperClass(ClassNameTest.class);
		
		for(Class cls :list) {
			System.out.println(cls.getSimpleName());
		}
		
	}
	
	public static List<Class<?>> getsuperClass(Class<?> calzz){  
        List<Class<?>> listSuperClass = new ArrayList<Class<?>>();  
        Class<?> superclass = calzz.getSuperclass();  
        while (superclass != null) {  
            if(superclass.getName().equals("java.lang.Object")){  
                break;  
            }  
            listSuperClass.add(superclass);  
            superclass = superclass.getSuperclass();  
        }  
        return listSuperClass;  
    } 
}
