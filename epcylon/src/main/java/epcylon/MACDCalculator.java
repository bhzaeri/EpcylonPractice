package epcylon;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.codehaus.jackson.map.ObjectMapper;

import epcylon.StockClient.StockData;
import epcylon.server.ClientHandler;

public class MACDCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MACDCalculator(MinuteBarBase barBase) {
		_12 = new Calculator(12);
		_26 = new Calculator(26);
		_9 = new Calculator(9);
		this.clientHandlers = new Vector<ClientHandler>();
		this.minuteBarBase = barBase;
	}

	final MinuteBarBase minuteBarBase;
	final List<ClientHandler> clientHandlers;
	final private Calculator _12;
	final private Calculator _26;
	final private Calculator _9;

	public synchronized void addClientHandler(ClientHandler clientHandler) {
		this.clientHandlers.add(clientHandler);
	}

	public synchronized void removeClientHandler(ClientHandler clientHandler) {
		this.clientHandlers.remove(clientHandler);
	}

	public synchronized void add(Double newValue, String timeStamp) {
		Double a1 = _12.add(newValue, timeStamp);
		Double a2 = _26.add(newValue, timeStamp);
		if (a1 != null && a2 != null)
			_9.add(a1 - a2, timeStamp);
		Double ema_9 = _9.getEma();
		if (clientHandlers.size() > 1) {
			@SuppressWarnings("unused")
			int index = 0;
			index++;
		}
		if (ema_9 != null)
			try {
				for (ClientHandler clientHandler : clientHandlers) {
					String json = "{\"timeStamp\":\"" + timeStamp + "\",\"pair\":\"" + minuteBarBase.getCurrency()
							+ "\",\"signal\":" + ema_9.toString() + "}";
					clientHandler.write(json);
					Class1 data = new ObjectMapper().readValue(json, Class1.class);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static class Class1 {
		private String timeStamp;
		private String pair;
		private Double signal;

		public String getTimeStamp() {
			return timeStamp;
		}

		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}

		public String getPair() {
			return pair;
		}

		public void setPair(String pair) {
			this.pair = pair;
		}

		public Double getSignal() {
			return signal;
		}

		public void setSignal(Double signal) {
			this.signal = signal;
		}

	}
}
