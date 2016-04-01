package epcylon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinuteBar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] lines = new String[] { "2016-04-01T01:20:47.270Z", "2016-04-01T01:23:49.150Z",
				"2016-04-01T01:24:49.520Z", "2016-04-01T01:26:01.020Z", "2016-04-01T01:26:51.400Z",
				"2016-04-01T01:27:51.770Z", "2016-04-01T01:28:52.150Z", "2016-04-01T01:29:52.520Z",
				"2016-04-01T01:31:57.020Z", "2016-04-01T01:33:21.403Z" };
		MinuteBar minuteBar = new MinuteBar(5, new MACDCalculator());
		for (String line : lines) {
			minuteBar.getTickData("1", line);
		}
	}

	public MinuteBar(int minutes, MACDCalculator macdCalculator) {
		this.minutes = minutes;
		this.macdCalculator = macdCalculator;
	}

	private MACDCalculator macdCalculator;
	private int minutes;
	private String lastTickData = null;
	private int lastTickMin = 0;
	private int lastTickSec = 0;

	public void getTickData(String tickData, String tickTime) {
		String pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:([0-9]{2}):([0-9]{2}).[0-9]{3}Z";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(tickTime);
		String minSt = null, secSt = null;
		if (m.find()) {
			minSt = m.group(1);
			secSt = m.group(2);
		}
		int min = Integer.parseInt(minSt);
		int sec = Integer.parseInt(secSt);

		if (this.minutes == 1) {
			if (sec % 60 < lastTickSec) {
				if (lastTickData != null) {
					String temp = new StringBuilder(tickTime).replace(17, 23, "00.000").toString();
					macdCalculator.add(Double.parseDouble(lastTickData), temp);
				}
			}
			lastTickSec = sec;
			lastTickMin = min;
			lastTickData = tickData;
		} else {
			if ((min % minutes) < (lastTickMin % minutes)) {
				if (lastTickData != null) {
					int roundedMin = lastTickMin - lastTickMin % minutes;
					String t = Integer.toString(roundedMin);
					if (t.length() == 1)
						t = "0" + t;
					String temp = new StringBuilder(tickTime).replace(17, 23, "00.000").replace(14, 16, t).toString();
					macdCalculator.add(Double.parseDouble(lastTickData), temp);
				}
			}
			lastTickSec = sec;
			lastTickMin = min;
			lastTickData = tickData;
		}

		System.out.println(min + " -- " + sec);
	}
}
