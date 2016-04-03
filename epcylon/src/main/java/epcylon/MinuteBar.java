package epcylon;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import epcylon.server.ClientHandler;

public class MinuteBar {

	private static Map<MinuteBarBase, MinuteBar> instances;

	public synchronized static MinuteBar getInstance(MinuteBarBase minuteBase, ClientHandler clientHandler) {
		if (instances == null) {
			instances = new HashMap<MinuteBarBase, MinuteBar>();
		}
		MinuteBar instance = instances.get(minuteBase);
		if (instance == null) {
			instance = new MinuteBar(minuteBase.getMinuteBase(), minuteBase.getSecondBase(),
					new MACDCalculator(minuteBase));
			instances.put(minuteBase, instance);
		}
		instance.macdCalculator.addClientHandler(clientHandler);
		return instance;
	}

	private static Logger logger = Logger.getLogger(MinuteBar.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] lines = new String[] { // "2016-04-01T01:20:47.270Z",
										// "2016-04-01T01:23:49.150Z",
				// "2016-04-01T01:24:49.520Z", "2016-04-01T01:26:50.020Z",
				// "2016-04-01T01:26:51.400Z",
				"2016-04-01T01:27:51.770Z", "2016-04-01T01:28:52.150Z", "2016-04-01T01:41:52.520Z",
				"2016-04-01T02:33:06.020Z", "2016-04-01T03:03:21.403Z" };
		MinuteBar minuteBar = new MinuteBar(5, 0, new MACDCalculator(null));
		for (String line : lines) {
			minuteBar.getTickData(1.0, line);
		}
	}

	public MinuteBar(int minutes, int seconds, MACDCalculator macdCalculator) {
		this.minutes = minutes;
		this.macdCalculator = macdCalculator;
		this.seconds = seconds;
	}

	private MACDCalculator macdCalculator;
	private int minutes;
	private int seconds;
	private Double lastTickData = null;
	private String lastTickTime = null;
	private int lastTickYear = 0;
	private int lastTickMonth = 0;
	private int lastTickDay = 0;
	private int lastTickHour = 0;
	private int lastTickMin = 0;
	private int lastTickSec = 0;

	public void getTickData(Double tickData, String tickTime) {
		String pattern = "([0-9]{4})-([0-9]{2})-([0-9]{2})T([0-9]{2}):([0-9]{2}):([0-9]{2}).[0-9]{3}Z";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(tickTime);
		String yearSt = null, monthSt = null, daySt = null, hourSt = null, minSt = null, secSt = null;
		if (m.find()) {
			yearSt = m.group(1);
			monthSt = m.group(2);
			daySt = m.group(3);
			hourSt = m.group(4);
			minSt = m.group(5);
			secSt = m.group(6);
		}
		int year = Integer.parseInt(yearSt);
		int month = Integer.parseInt(monthSt);
		int day = Integer.parseInt(daySt);
		int hour = Integer.parseInt(hourSt);
		int min = Integer.parseInt(minSt);
		int sec = Integer.parseInt(secSt);

		if (this.seconds > 0) {
			if ((sec / seconds > lastTickSec / seconds) || (sec % seconds) < (lastTickSec % seconds)
					|| (lastTickMin < min) || (lastTickHour < hour) || (lastTickDay < day) || (lastTickMonth < month)
					|| (lastTickYear < year)) {
				if (lastTickData != null) {
					int roundedSec = lastTickSec - lastTickSec % seconds;
					String t = Integer.toString(roundedSec);
					if (t.length() == 1)
						t = "0" + t;
					String temp = new StringBuilder(lastTickTime).replace(17, 23, t + ".000").toString();
					macdCalculator.add(lastTickData, temp);
					logger.info(temp);
				}
			}
			lastTickSec = sec;
			lastTickMin = min;
			lastTickHour = hour;
			lastTickDay = day;
			lastTickMonth = month;
			lastTickYear = year;
			lastTickTime = tickTime;
			lastTickData = tickData;
		} else if (this.minutes == 1) {
			if ((sec % 60 < lastTickSec) || (lastTickMin < min) || (lastTickHour < hour) || (lastTickDay < day)
					|| (lastTickMonth < month) || (lastTickYear < year)) {
				if (lastTickData != null) {
					String temp = new StringBuilder(lastTickTime).replace(17, 23, "00.000").toString();
					macdCalculator.add(lastTickData, temp);
					logger.info(temp);
				}
			}
			lastTickSec = sec;
			lastTickMin = min;
			lastTickHour = hour;
			lastTickDay = day;
			lastTickMonth = month;
			lastTickYear = year;
			lastTickTime = tickTime;
			lastTickData = tickData;
		} else {
			if ((min / minutes > lastTickMin / minutes) || (min % minutes < lastTickMin % minutes)
					|| (lastTickHour < hour) || (lastTickDay < day) || (lastTickMonth < month)
					|| (lastTickYear < year)) {
				if (lastTickData != null) {
					int roundedMin = lastTickMin - lastTickMin % minutes;
					String t = Integer.toString(roundedMin);
					if (t.length() == 1)
						t = "0" + t;
					String temp = new StringBuilder(lastTickTime).replace(17, 23, "00.000").replace(14, 16, t)
							.toString();
					macdCalculator.add(lastTickData, temp);
					logger.info(temp);
				}
			}
			lastTickSec = sec;
			lastTickMin = min;
			lastTickHour = hour;
			lastTickDay = day;
			lastTickMonth = month;
			lastTickYear = year;
			lastTickTime = tickTime;
			lastTickData = tickData;
		}

		logger.info(min + " -- " + sec);
	}

	public void addClientHandler(ClientHandler clientHandler) {
		this.macdCalculator.addClientHandler(clientHandler);
	}

	public void removeClientHandler(ClientHandler clientHandler) {
		this.macdCalculator.removeClientHandler(clientHandler);
	}
}
