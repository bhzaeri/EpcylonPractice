package epcylon;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import epcylon.enums.MinuteBarsEnum;
import epcylon.server.ClientHandler;

public class MACDCalculator {

	private static Logger logger = Logger.getLogger(MACDCalculator.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MACDCalculator(MinuteBarsEnum barBase) {
		_12 = new Calculator(12);
		_26 = new Calculator(26);
		_9 = new Calculator(9);
		this.clientHandlers = new Vector<ClientHandler>();
		this.minuteBarBase = barBase;
	}

	final MinuteBarsEnum minuteBarBase;
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
		if (ema_9 != null)
			try {
				// BigDecimal b = new BigDecimal(ema_9);
				// b = b.setScale(2, BigDecimal.ROUND_HALF_DOWN);
				// if (timeStamp.contains("5.000") ||
				// timeStamp.contains("0.000"))
				// logger.info(timeStamp + " --- " + clientHandlers.size());
				String json = "{\"timeStamp\":\"" + timeStamp + "\",\"pair\":\"" + minuteBarBase.getPair().getPair()
						+ "\",\"signal\":" + ema_9.toString() + "}";
				logger.info("Signal sent: " + json);
				for (ClientHandler clientHandler : clientHandlers) {
					clientHandler.write(json);
					logger.info("Data sent to client");
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
