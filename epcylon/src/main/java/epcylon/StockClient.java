package epcylon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import epcylon.server.ClientHandler;

public class StockClient {

	private static Logger logger = Logger.getLogger(StockClient.class);
	public static String ERROR_RESPONSE = "{\"error\":\"invalid currency pair.\"}\n";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new StockClient("USD-CAD").startReceiveTickData();
	}

	private static Map<String, StockClient> stockClients = null;

	public synchronized static StockClient getInstance(String currency) {
		if (stockClients == null)
			stockClients = new HashMap<String, StockClient>();
		StockClient instance = stockClients.get(currency);
		if (instance == null) {
			instance = new StockClient(currency);
			stockClients.put(currency, instance);
		}
		return instance;
	}

	public static void start(MinuteBarBase barBase, ClientHandler clientHandler) {
		final StockClient stockClient = StockClient.getInstance(barBase.getCurrency());
		MinuteBar minuteBar = MinuteBar.getInstance(barBase, clientHandler);
		stockClient.add(minuteBar);
		logger.info(barBase.getCurrency() + " :: is running");
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				stockClient.startReceiveTickData();
			}
		}).start();
	}

	public static void stopClientHandler(MinuteBarBase minuteBase, ClientHandler clientHandler) {
		MinuteBar minuteBar = MinuteBar.getInstance(minuteBase, clientHandler);
		minuteBar.removeClientHandler(clientHandler);
	}

	private StockClient(String currency) {
		minuteBars = new Vector<MinuteBar>();
		this.currency = currency;
		lock1 = new Object();
	}

	private Vector<MinuteBar> minuteBars;
	private String currency;
	private Object lock1;
	private volatile Boolean receiving = false;

	public String getCurrency() {
		return currency;
	}

	public Boolean isStopped() {
		return !receiving;
	}

	public void stop() {
		this.receiving = false;
	}

	public void add(MinuteBar bar) {
		synchronized (lock1) {
			if (!minuteBars.contains(bar))
				minuteBars.add(bar);
		}
	}

	public void startReceiveTickData() {
		if (receiving) {
			return;
		}
		receiving = true;
		String sentence;
		String response;
		Socket clientSocket = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			clientSocket = new Socket("practiceproblem.epcylon.com", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "login fiInKuFbMzUQtqiCXfJbuowMgFEzJcguLXMirmsfGjfsJMdF";
			outToServer.writeBytes(sentence + '\n');
			response = inFromServer.readLine();
			System.out.println("FROM SERVER: " + response);
			sentence = "subscribe " + currency;
			outToServer.writeBytes(sentence + '\n');
			int i = 0;
			while (receiving) {
				if (i >= Util.lines.length)
					break;
				response = Util.lines[i++];
				// response = inFromServer.readLine();
				if (response.contains("error"))
					break;
				StockData data = mapper.readValue(response, StockData.class);

				synchronized (lock1) {
					for (MinuteBar minuteBar : minuteBars) {
						minuteBar.getTickData(data.quote.data.last, data.quote.time);
					}
				}

				System.out.println("FROM SERVER: " + response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				receiving = false;
				if (clientSocket != null) {
					clientSocket.close();
					System.out.println("Connection closed!");
					// this.startReceiveTickData();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
