package com.duanjiao.dpsTest.callback;


interface IClient {
	void recvAnswer(String answer);
}

class Server {
	public void answer(final IClient client, final String question) {
		if (question.equals("What is the answer to life, the universe and everything?")) {
			calclating();
			client.recvAnswer("42");
		}
	}

	private void calclating() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ClientSync implements IClient {
	private final Server server;

	public ClientSync(final Server server) {
		this.server = server;
	}

	public void ask(final String question) {
		this.server.answer(this, question);
	}

	@Override
	public void recvAnswer(final String answer) {
		System.out.println(answer);
	}
}

public class SyncInterfaceCallback {

	public static void main(final String[] args) {
		Server server = new Server();
		ClientSync client = new ClientSync(server);
		client.ask("What is the answer to life, the universe and everything?");
	}
}
