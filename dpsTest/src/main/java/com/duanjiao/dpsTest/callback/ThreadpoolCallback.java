package com.duanjiao.dpsTest.callback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ClientRunnable implements IClient, Runnable {
	private final Server server;
	private final String question;
	private final int id;

	public ClientRunnable(final Server server, final String question, final int id) {
		this.server = server;
		this.question = question;
		this.id = id;
	}

	@Override
	public void recvAnswer(final String answer) {
		System.out.println("clinet " + this.id + " got answer: " + answer);
	}

	@Override
	public void run() {
		server.answer(ClientRunnable.this, this.question);
	}
}


public class ThreadpoolCallback {
	public static void main(final String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();

		Server server = new Server();

		for (int i = 0; i < 100; i++) {
			ClientRunnable cr = new ClientRunnable(server, "What is the answer to life, the universe and everything?",
					i);
			es.execute(cr);
			System.out.println("client " + i + " asked !");
		}

		es.shutdown();
	}
}
