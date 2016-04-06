package epcylon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;

import epcylon.MinuteBar;
import epcylon.StockClient;
import epcylon.enums.CurrencyPair;
import epcylon.enums.MinuteBarsEnum;
import epcylon.exceptions.MinuteBarInvalidException;

public class ClientHandler {

	private static Logger logger = Logger.getLogger(ClientHandler.class);

	private static String errorMessage() {
		return "currency is ivalid.";
	}

	public static Integer tryParseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return null;
		}
	}

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
	private Vector<MinuteBarsEnum> minuteBarBases = new Vector<MinuteBarsEnum>();

	private MinuteBarsEnum findMinuteBar(CurrencyPair pair) {
		synchronized (minuteBarBases) {
			for (MinuteBarsEnum minuteBarsEnum : minuteBarBases) {
				if (minuteBarsEnum.getPair().equals(pair))
					return minuteBarsEnum;
			}
		}
		return null;
	}

	// public synchronized void addBarBase(Integer minuteBarBase,CurrencyPair
	// currency) {
	// this.minuteBarBases.add(barBase.getCurrency(), barBase);
	// }
	//
	// public synchronized void removeBarBase(MinuteBarBase barBase) {
	// this.minuteBarBases.remove(barBase.getCurrency());
	// }

	public void startListen() throws IOException {
		if (input == null)
			input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		while (flag1) {
			String clientSentence = input.readLine();
			if (clientSentence == null) {
				flag1 = false;
				for (MinuteBarsEnum barsEnum : minuteBarBases) {
					StockClient.stopClientHandler(barsEnum, this);
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
					this.write("{\"error\":\"invalid login key\"}");
					break;
				}

			} else if ("logout".equals(temp)) {
				this.write("{\"connected\":false}");
				break;
			} else if (temp.startsWith("subscribe")) {
				if (this.isLoggedIn) {
					String[] ss = temp.split(" ");
					CurrencyPair currency = null;
					if (ss.length > 1)
						currency = CurrencyPair.getValue(ss[1]);
					else {
						this.write("{\"error\":\"no currency pair\"}");
						continue;
					}
					Integer minuteBarBase;
					if (ss.length > 2)
						minuteBarBase = tryParseInt(ss[2]);
					else {
						this.write("{\"error\":\"no minute bar\"}");
						continue;
					}
					if (currency == null) {
						this.write("{\"error\":\"" + errorMessage() + "\"}");
						continue;
					}
					if (minuteBarBase == null) {
						this.write("{\"error\":\"invalid minute bar\"}");
						continue;
					}
					if (findMinuteBar(currency) == null) {
						try {
							MinuteBar.addClientHandler(minuteBarBase, currency, this);
						} catch (MinuteBarInvalidException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							this.write("{\"error\":\"invalid minute bar\"}");
							continue;
						}
						this.minuteBarBases.add(MinuteBarsEnum.getValue(minuteBarBase, currency));
						logger.info("subscribed");
					}
				} else
					this.write("{\"error\":\"not authorized\"}");
			} else if (temp.startsWith("unsubscribe")) {
				if (this.isLoggedIn) {
					String[] ss = temp.split(" ");
					CurrencyPair currency = null;
					if (ss.length > 1) {
						currency = CurrencyPair.getValue(ss[1]);
					} else {
						this.write("{\"error\":\"no currency pair\"}");
						continue;
					}
					if (currency == null) {
						this.write("{\"error\":\"" + errorMessage() + "\"}");
						continue;
					}
					MinuteBarsEnum barsEnum = findMinuteBar(currency);
					if (barsEnum != null) {
						this.minuteBarBases.remove(barsEnum);
						StockClient.stopClientHandler(barsEnum, this);
					}
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
		output.println(message);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		this.output.close();
		this.input.close();
	}
}
