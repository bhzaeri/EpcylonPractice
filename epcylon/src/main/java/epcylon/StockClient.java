package epcylon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.codehaus.jackson.map.ObjectMapper;

import epcylon.PracticeTest.StockData;
import epcylon.server.ClientHandler;

public class StockClient {

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

	public static void start(String currency, MinuteBarBase barBase, ClientHandler clientHandler) {
		final StockClient stockClient = StockClient.getInstance(currency);
		MinuteBar minuteBar = MinuteBar.getInstance(barBase, clientHandler);
		stockClient.add(minuteBar);
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
			while (receiving) {
				response = inFromServer.readLine();
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
				if (clientSocket != null) {
					clientSocket.close();
					System.out.println("Connection closed!");
					this.startReceiveTickData();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
