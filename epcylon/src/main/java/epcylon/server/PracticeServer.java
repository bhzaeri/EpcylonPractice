package epcylon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class PracticeServer {

	private static Logger logger = Logger.getLogger(PracticeServer.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PracticeServer practiceServer = new PracticeServer();
		practiceServer.startServer();
	}

	private volatile Boolean repeat = true;
	private ServerSocket serverSocket;

	public void startServer() {
		final PracticeServer practiceServer = this;
		new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					practiceServer.startServer2();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void startServer2() throws IOException {
		try {
			serverSocket = new ServerSocket(10000);
			logger.info("The srever is running on 10000");
			while (repeat) {
				final Socket socket = serverSocket.accept();
				logger.info("Hello! : " + socket.getRemoteSocketAddress());
				new Thread(new Runnable() {
					public void run() {
						try {
							final ClientHandler clientHandler = new ClientHandler(socket);
							clientHandler.startListen();
						} catch (IOException exception) {
							exception.printStackTrace();
						} finally {
							if (socket != null)
								try {
									socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
						}
					}
				}).start();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			if (serverSocket != null)
				serverSocket.close();
		}
	}

}
