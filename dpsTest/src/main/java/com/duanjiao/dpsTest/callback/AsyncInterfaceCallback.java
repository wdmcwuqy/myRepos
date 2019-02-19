package com.duanjiao.dpsTest.callback;

class ClientAsync implements IClient {
	private final Server server;

	public ClientAsync(final Server server) {
		this.server = server;
	}

	public void ask(final String question) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				server.answer(ClientAsync.this, question);
			}
		}).start();
	}

	@Override
	public void recvAnswer(final String answer) {
		System.out.println(answer);
	}
}

public class AsyncInterfaceCallback {
	public static void main(final String[] args) {
		Server server = new Server();
		ClientAsync client = new ClientAsync(server);
		client.ask("What is the answer to life, the universe and everything?");
		System.out.println("asked ! waiting for the answer...");
	}
}
