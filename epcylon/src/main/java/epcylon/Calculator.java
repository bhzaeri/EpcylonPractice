package epcylon;

import java.util.List;
import java.util.Vector;

public class Calculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator c = new Calculator(10);
		Double[] list = new Double[] { 22.27, 22.19, 22.08, 22.17, 22.18, 22.13, 22.23, 22.43, 22.24, 22.29, 22.15,
				22.39, 22.38, 22.61, 23.36, 24.05, 23.75, 23.83, 23.95, 23.63, 23.82, 23.87, 23.65, 23.19, 23.10, 23.33,
				22.68, 23.10, 22.40, 22.17 };
		for (Double i : list) {
			Double sma = c.sma(i, "");
			Double ema = c.ema(i);
			// System.out.println(i + " -- " + c.avgList.size() + " -- " +
			// c.emaList.size());
			System.out.println(i + " -- " + sma + " -- " + ema);
			// System.out.println();
		}
	}

	public Calculator(int length) {
		this.length = length;
		smoothingConst = 2.0 / (length + 1);
		list = new Vector<Double>();
		avgList = new Vector<Double>();
		emaList = new Vector<Double>();
		timeList = new Vector<String>();
	}

	final private int length;
	final private List<Double> list;
	final private List<Double> avgList;
	final private List<Double> emaList;
	final private List<String> timeList;

	final private double smoothingConst;

	private double average() {
		double sum = 0;
		for (double d : list) {
			sum += d;
		}
		double avg = sum / list.size();
		return avg;
	}

	private Double sma(Double newValue, String timeStamp) {
		list.add(newValue);
		timeList.add(timeStamp);
		if (list.size() >= length) {
			if (list.size() > length)
				list.remove(0);
			Double avg = this.average();
			avgList.add(avg);
			if (avgList.size() > length)
				avgList.remove(0);
			return avg;
		}
		return null;
	}

	private Double ema(Double newValue) {
		Double lastEma;
		if (emaList.size() > 0)
			lastEma = emaList.get(emaList.size() - 1);
		else {
			if (avgList.size() > 0) {
				lastEma = avgList.get(avgList.size() - 1);
				emaList.add(lastEma);
				return lastEma;
			} else
				return null;
		}
		double ema = smoothingConst * (newValue - lastEma) + lastEma;
		emaList.add(ema);
		if (emaList.size() > length)
			emaList.remove(0);
		return ema;
	}

	public synchronized Double add(Double newValue, String timeStamp) {
		sma(newValue, timeStamp);
		ema(newValue);
		return getEma();
	}

	public synchronized Double getEma() {
		return emaList.size() > 0 ? emaList.get(emaList.size() - 1) : null;
	}
}
