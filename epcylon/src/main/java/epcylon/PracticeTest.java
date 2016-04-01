package epcylon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.codehaus.jackson.map.ObjectMapper;

public class PracticeTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

			for (int i = 0; i < 10; i++) {
				sentence = "subscribe USD-CAD";
				outToServer.writeBytes(sentence + '\n');
				response = inFromServer.readLine();
				StockData data = mapper.readValue(response, StockData.class);
				System.out.println("FROM SERVER: " + response);
				
				Thread.sleep(30000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (clientSocket != null){
					clientSocket.close();
					System.out.println("Connection closed!");
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
