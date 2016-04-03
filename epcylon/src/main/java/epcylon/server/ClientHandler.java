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
import epcylon.exceptions.CurrencyPairInvalidException;

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
	private BufferedReader input = null;
	private PrintWriter output = null;
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
				String password = null;
				if (ss.length > 1)
					password = ss[1];
				else {
					this.write("{\"error\":\"no password.\"}");
					continue;
				}
				if ("iamprogrammerihavenolife".equals(password)) {
					isLoggedIn = true;
					logger.info(socket.getLocalSocketAddress() + " : logged in successfully");
					this.write("{\"connected\":true}");
				} else {
					this.write("{\"error\":\"invalid login key\"}\n<close connection>");
					break;
				}

			} else if (temp.startsWith("subscribe")) {
				if (this.isLoggedIn) {
					String[] ss = temp.split(" ");
					String currency = "";
					if (ss.length > 1)
						currency = ss[1];
					else {
						this.write("{\"error\":\"no currency pair\"}");
						continue;
					}
					int minuteBase;
					if (ss.length > 2)
						minuteBase = Integer.parseInt(ss[2]);
					else {
						this.write("{\"error\":\"no minute bar\"}");
						continue;
					}
					MinuteBarBase barBase = new MinuteBarBase(currency, 0, 15);
					if (!minuteBarBases.containsKey(currency)) {
						try {
							StockClient.start(barBase, this);
						} catch (CurrencyPairInvalidException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							this.write("{\"error\":\"" + e.getMessage() + "\"}");
							continue;
						}
						this.minuteBarBases.put(barBase.getCurrency(), barBase);
						logger.info("subscribed");
					}
				} else
					this.write("{\"error\":\"not authorized\"}");
			} else if (temp.startsWith("unsubscribe")) {
				if (this.isLoggedIn) {
					String[] ss = temp.split(" ");
					String currency = null;
					if (ss.length > 1) {
						currency = ss[1];
					} else {
						this.write("{\"error\":\"no currency pair\"}");
						continue;
					}
					MinuteBarBase barBase = minuteBarBases.get(currency);
					if (barBase != null)
						StockClient.stopClientHandler(barBase, this);
					logger.info("unsubscribed");
				} else
					this.write("{\"error\":\"not authorized\"}");
			} else {
				this.write("{\"error\":\"unidentified command\"}");
			}
		}
	}

	public void write(final String message) throws IOException {
		if (output == null)
			output = new PrintWriter(socket.getOutputStream(), true);
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				// write the message on the client output
				output.println(message);
			}
		}).start();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		this.output.close();
		this.input.close();
	}
}
