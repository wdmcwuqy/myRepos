package com.duanjiao.dpsTest.callback;


class A {
	public void ask(final B b, final String question) {
		System.out.println("A Class process ask .....");
		
		b.answer(this, question);
	}

	public void processResult(final String answer) {
		System.out.println("A Class process processResult .....");
		System.out.println(answer);
	}
}

class B {
	public void answer(final A a, final String question) {
		
		System.out.println("B Class process .....");
		if (question.equals("What is the answer to life, the universe and everything?")) {
			a.processResult("42");
		}
	}
}

public class SyncObjectCallback {
	public static void main(final String[] args) {
		B b = new B();
		A a = new A();

		a.ask(b, "What is the answer to life, the universe and everything?");
	}
}
