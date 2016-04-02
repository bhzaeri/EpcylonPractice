package epcylon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import epcylon.MinuteBar;
import epcylon.MinuteBarBase;
import epcylon.StockClient;

public class ClientHandler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	private Socket socket;
	private Boolean flag1 = true;
	private Boolean flag2 = true;

	public void startListen() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		while (flag1) {
			String clientSentence = input.readLine();
			String temp = clientSentence.trim();
			if (temp.startsWith("login")) {
				String[] ss = temp.split(" ");
				String password = ss[1];
			} else if (temp.startsWith("subscribe")) {
				String[] ss = temp.split(" ");
				String currency = ss[1];
				int minuteBase = Integer.parseInt(ss[2]);
				MinuteBar minuteBar = MinuteBar.getInstance(new MinuteBarBase(minuteBase, 0));
				StockClient stockClient = StockClient.getInstance(currency);
				stockClient.add(minuteBar);
				stockClient.startReceiveTickData();
				//start the write process for the user
				
			} else if (temp.startsWith("unsubscribe")) {

			} else {

			}
		}
	}

	public void write(String message) {

	}

}
