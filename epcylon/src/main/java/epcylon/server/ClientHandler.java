package epcylon.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import epcylon.MinuteBar;
import epcylon.MinuteBarBase;
import epcylon.StockClient;

public class ClientHandler {

	private static Logger logger = Logger.getLogger(ClientHandler.class);

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	private Socket socket;
	private Boolean isLoggedIn = false;
	private Boolean isSubscribed = false;
	private Boolean flag1 = true;
	private Boolean flag2 = true;
	BufferedReader input = null;
	PrintWriter output = null;
	private Map<String, MinuteBarBase> minuteBarBases = new HashMap<String, MinuteBarBase>();

	public synchronized void addBarBase(MinuteBarBase barBase) {
		this.minuteBarBases.put(barBase.getCurrency(), barBase);
	}

	public synchronized void removeBarBase(MinuteBarBase barBase) {
		this.minuteBarBases.remove(barBase.getCurrency());
	}

	public void startListen() throws IOException {
		if (input == null)
			input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		while (flag1) {
			String clientSentence = input.readLine();
			if (clientSentence == null) {
				flag1 = false;
				for (String currency : minuteBarBases.keySet()) {
					StockClient.stopClientHandler(minuteBarBases.get(currency), this);
				}
				break;
			}
			String temp = clientSentence == null ? "" : clientSentence.trim();
			if (temp.startsWith("login")) {
				String[] ss = temp.split(" ");
				String password = ss[1];
				isLoggedIn = true;
				logger.info(socket.getLocalSocketAddress() + " : logged in successfully");
			} else if (temp.startsWith("subscribe")) {
				if (this.isLoggedIn) {
					String[] ss = temp.split(" ");
					String currency = ss[1];
					int minuteBase = Integer.parseInt(ss[2]);
					MinuteBarBase barBase = new MinuteBarBase(currency, minuteBase, 0);
					if (!minuteBarBases.containsKey(currency)) {
						StockClient.start(barBase, this);
						this.minuteBarBases.put(barBase.getCurrency(), barBase);
						logger.info("subscribed");
					}
				} else
					this.write("not authorized");
			} else if (temp.startsWith("unsubscribe")) {
				if (this.isLoggedIn) {
					String[] ss = temp.split(" ");
					String currency = ss[1];
					StockClient.stopClientHandler(minuteBarBases.get(currency), this);
					logger.info("unsubscribed");
				} else
					this.write("not authorized");
			} else {

			}
		}
	}

	public void write(String message) throws IOException {
		if (output == null)
			output = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
		// write the message on the client output
		output.write(message + '\n');
	}

}
