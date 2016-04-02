package epcylon.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

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
	private Boolean isLoggedIn = false;
	private Boolean flag1 = true;
	private Boolean flag2 = true;
	BufferedReader input = null;
	BufferedWriter output = null;
	private Map<String, MinuteBarBase> barBases = new HashMap<String, MinuteBarBase>();

	public synchronized void addBarBase(MinuteBarBase barBase) {
		this.barBases.put(barBase.getCurrency(), barBase);
	}

	public synchronized void removeBarBase(MinuteBarBase barBase) {
		this.barBases.remove(barBase.getCurrency());
	}

	public void startListen() throws IOException {
		if (input == null)
			input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		while (flag1) {
			String clientSentence = input.readLine();
			String temp = clientSentence.trim();
			if (temp.startsWith("login")) {
				String[] ss = temp.split(" ");
				String password = ss[1];
				isLoggedIn = true;
			} else if (temp.startsWith("subscribe")) {
				String[] ss = temp.split(" ");
				String currency = ss[1];
				int minuteBase = Integer.parseInt(ss[2]);
				MinuteBarBase barBase = new MinuteBarBase(currency, minuteBase, 0);
				if (!barBases.containsKey(currency)) {
					StockClient.start(currency, barBase, this);
					this.barBases.put(barBase.getCurrency(), barBase);
				}
			} else if (temp.startsWith("unsubscribe")) {
				String[] ss = temp.split(" ");
				String currency = ss[1];
				StockClient.stopClientHandler(barBases.get(currency), this);
			} else {

			}
		}
	}

	public void write(String message) throws IOException {
		if (output == null)
			output = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		// write the message on the client output
	}

}
