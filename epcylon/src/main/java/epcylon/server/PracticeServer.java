package epcylon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PracticeServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private volatile Boolean repeat = true;
	private ServerSocket serverSocket;

	public void startServer() throws IOException {
		serverSocket = new ServerSocket(10000);
		while (repeat) {
			Socket socket = serverSocket.accept();
			ClientHandler clientHandler = new ClientHandler(socket);
			clientHandler.startListen();
		}

	}

}
