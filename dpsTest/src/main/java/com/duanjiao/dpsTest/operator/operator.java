package com.duanjiao.dpsTest.operator;

public class operator {
	
	public static void main(String[] args) {
		
		
		System.out.println("������"+ 8/3 );
		System.out.println("ȡģ:"+ 8%3);
		System.out.println("ȡ����"+ ~4 );
		System.out.println("�����������"+ Integer.toBinaryString(~6));
		System.out.println("�����������"+ Integer.toBinaryString(5));
		System.out.println("�����㣺"+(~6&5));
		System.out.println("�����㣺"+(~6|5));
		System.out.println("������㣺"+(~6^5));
		System.out.println("���ƣ�"+( 6>>5));
		System.out.println("���ƣ�"+( 6<<5));
		System.out.println("����0��䣺"+( 6>>>5));
	}
}
