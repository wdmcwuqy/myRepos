package com.duanjiao.dpsTest.callback;


class AA {
	private final BB b;

	public AA(final BB b) {
		this.b = b;
	}

	public void ask(final String question) {
		this.b.answer(this, question);
	}

	public void processResult(final String answer) {
		System.out.println(answer);
	}
}

class BB {
	public void answer(final AA a, final String question) {
		if (question.equals("What is the answer to life, the universe and everything?")) {
			a.processResult("42");
		}
	}
}

public class SyncOOCallback {
	public static void main(final String[] args) {
		BB b = new BB();
		AA a = new AA(b);
		a.ask("What is the answer to life, the universe and everything?");
	}
}
