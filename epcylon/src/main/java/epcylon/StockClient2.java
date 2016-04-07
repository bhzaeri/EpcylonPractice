package epcylon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import epcylon.enums.CurrencyPair;
import epcylon.enums.MinuteBarsEnum;
import epcylon.server.ClientHandler;

public class StockClient2 {

	private static Logger logger = Logger.getLogger(StockClient2.class);

	private static StockClient2 instance = null;

	public static StockClient2 getInstance() {
		if (instance == null)
			instance = new StockClient2();
		return instance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private Thread thread;
	private Boolean running = false;
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private ObjectMapper mapper = new ObjectMapper();
	private Map<String, Map<Integer, MinuteBar>> minuteBars = new HashMap<String, Map<Integer, MinuteBar>>();

	public Map<String, Map<Integer, MinuteBar>> getMinuteBars() {
		return minuteBars;
	}

	private StockClient2() {
		for (CurrencyPair currencyPair : CurrencyPair.values()) {
			minuteBars.put(currencyPair.getPair(), new HashMap<Integer, MinuteBar>());
		}
		for (MinuteBarsEnum minuteBarsEnum : MinuteBarsEnum.values()) {
			MinuteBar bar = new MinuteBar(minuteBarsEnum.getMinute(), 0, new MACDCalculator(minuteBarsEnum));
			minuteBars.get(minuteBarsEnum.getPair().getPair()).put(minuteBarsEnum.getMinute(), bar);
		}
	}

	public void stop() {
		if (clientSocket != null) {
			try {
				clientSocket.close();
				logger.info("Connection Closed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (!running) {
			thread = new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						try {
							connect();
							subscribe();
							receive();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if (clientSocket != null) {
								try {
									clientSocket.close();
									logger.info("Connection Closed!");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			});
			thread.start();
			running = true;
		}
	}

	public void removeClientHandlers(CurrencyPair currencyPair, ClientHandler clientHandler) {
		Map<Integer, MinuteBar> map = minuteBars.get(currencyPair.getPair());
		for (Integer key : map.keySet()) {
			map.get(key).removeClientHandler(clientHandler);
		}
	}

	private Boolean connect() {
		try {
			clientSocket = new Socket("practiceproblem.epcylon.com", 80);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String sentence = "login fiInKuFbMzUQtqiCXfJbuowMgFEzJcguLXMirmsfGjfsJMdF";
			outToServer.writeBytes(sentence + '\n');
			String response = inFromServer.readLine();
			logger.info("FROM SERVER: " + response);
			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn("Epcylon server is not reachable!");
			// e.printStackTrace();
			return false;
		}
	}

	private void subscribe() throws IOException {
		for (CurrencyPair currency : CurrencyPair.values()) {
			String sentence = "subscribe " + currency.getPair();
			outToServer.writeBytes(sentence + '\n');
		}
	}

	private void receive() throws IOException {
		String response;
		while (true) {
			response = inFromServer.readLine();
			if (response == null || response.contains("error")) {
				logger.info("AGAIN BROKEN!!!!");
				break;
			}
			Boolean dataIsValid = true;
			StockData data = null;
			try {
				data = mapper.readValue(response, StockData.class);
			} catch (Exception ex) {
				dataIsValid = false;
				ex.printStackTrace();
			}
			final StockData temp = data;
			if (dataIsValid) {
				// TODO Auto-generated method stub
				for (Integer minuteBarValue : minuteBars.get(temp.getQuote().pair).keySet()) {
					MinuteBar minuteBar = minuteBars.get(temp.getQuote().pair).get(minuteBarValue);
					minuteBar.getTickData(temp.quote.data.last, temp.quote.time);
				}
			}
		}
	}

	public static class StockData {
		StockData2 quote;

		public StockData2 getQuote() {
			return quote;
		}

		public void setQuote(StockData2 quote) {
			this.quote = quote;
		}
	}

	public static class StockData2 {
		String pair;
		String time;
		StockData3 data;

		public String getPair() {
			return pair;
		}

		public void setPair(String pair) {
			this.pair = pair;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public StockData3 getData() {
			return data;
		}

		public void setData(StockData3 data) {
			this.data = data;
		}
	}

	public static class StockData3 {
		double bid, ask, last;

		public double getBid() {
			return bid;
		}

		public void setBid(double bid) {
			this.bid = bid;
		}

		public double getAsk() {
			return ask;
		}

		public void setAsk(double ask) {
			this.ask = ask;
		}

		public double getLast() {
			return last;
		}

		public void setLast(double last) {
			this.last = last;
		}
	}
}
