package epcylon.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import epcylon.MinuteBar;
import epcylon.MinuteBarInvalidException;
import epcylon.StockClient2;
import epcylon.enums.CurrencyPair;
import epcylon.enums.MinuteBarsEnum;

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
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputQueueReader = new Thread(new MyOutputRunnable());
		outputQueueReader.start();
	}

	private Socket socket;
	private Boolean isLoggedIn = false;
	private Boolean isSubscribed = false;
	volatile private Boolean flag1 = true;
	private Boolean flag2 = true;
	private BufferedReader input = null;
	private DataOutputStream output = null;
	private List<MinuteBarsEnum> bars = new Vector<MinuteBarsEnum>();
	private BlockingQueue<String> outputQueue = new ArrayBlockingQueue<String>(10000);
	private Thread outputQueueReader = null;

	public void startListen() throws IOException {
		while (flag1) {
			String clientSentence = null;
			try {
				clientSentence = input.readLine();
			} catch (Exception ex) {
				clientSentence = null;
			}
			if (clientSentence == null) {
				flag1 = false;
				stopHandler();
				finishOutputQueue();
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
					flag1 = false;
					break;
				}

			} else if ("logout".equals(temp)) {
				stopHandler();
				this.write("{\"connected\":false}");
				finishOutputQueue();
				flag1 = false;
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
					try {
						MinuteBarsEnum t = MinuteBarsEnum.getValue(minuteBarBase, currency);
						if (!bars.contains(t)) {
							MinuteBar.addClientHandler(minuteBarBase, currency, this);
							bars.add(t);
							logger.info("subscribed");
						}
					} catch (MinuteBarInvalidException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						this.write("{\"error\":\"invalid minute bar\"}");
						continue;
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
					StockClient2.getInstance().removeClientHandlers(currency, this);
					synchronized (bars) {
						for (int i = 0; i < bars.size(); i++) {
							if (bars.get(i).getPair() == currency) {
								bars.remove(i);
								i--;
							}
						}
					}
					logger.info("unsubscribed");
				} else
					this.write("{\"error\":\"not authorized\"}");
			} else {
				this.write("{\"error\":\"unidentified command\"}");
			}
		}
	}

	private void stopHandler() throws IOException {
		for (CurrencyPair currencyPair : CurrencyPair.values()) {
			StockClient2.getInstance().removeClientHandlers(currencyPair, this);
		}
	}

	private void finishOutputQueue() {
		try {
			outputQueue.put("finished");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(final String message) throws IOException {
		// output.writeBytes(message + '\n');
		// output.flush();
		try {
			outputQueue.put(message);
			if (outputQueue.remainingCapacity() < 30)
				logger.warn("queue size is exceeding!!!!!!!!!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		this.output.close();
		this.input.close();
	}

	public class MyOutputRunnable implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			while (flag1) {
				try {
					String temp = outputQueue.take();
					if ("finished".equals(temp))
						break;
					output.writeBytes(temp + '\n');
					output.flush();
				} catch (SocketException exception) {
					flag1 = false;
					try {
						stopHandler();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("Output queue thread finished.");
		}

	}
}
