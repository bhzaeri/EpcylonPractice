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
		}
		return instance;
	}

	private StockClient(String currency) {
		minuteBars = new Vector<MinuteBar>();
		this.currency = currency;
		lock1 = new Object();
	}

	private Vector<MinuteBar> minuteBars;
	private String currency;
	private Object lock1;
	private volatile Boolean repeat = true;

	public String getCurrency() {
		return currency;
	}

	public Boolean getRepeat() {
		return repeat;
	}

	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}

	public void add(MinuteBar bar) {
		synchronized (lock1) {
			minuteBars.add(bar);
		}
	}

	public void startReceiveTickData() {
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
			while (repeat) {
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
