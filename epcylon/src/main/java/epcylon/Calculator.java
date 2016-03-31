package epcylon;

import java.util.List;
import java.util.Vector;

public class Calculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator c = new Calculator(5);
		Double[] list = new Double[] { 1.0, 4.0, 8.0, 9.0, 10.0, 12.0, 14.0, 17.0, 18.0, 19.0, 21.0, 36.0 };
		for (Double i : list) {
			Double sma = c.sma(i);
			Double ema = c.ema(i);
			System.out.println(i + " -- " + c.avgList.size() + " -- " + c.emaList.size());
			System.out.println(i + " -- " + sma + " -- " + ema);
			System.out.println();
		}
	}

	public Calculator(int length) {
		this.length = length;
		smoothingConst = 2.0 / (length + 1);
		list = new Vector<Double>();
		avgList = new Vector<Double>();
		emaList = new Vector<Double>();
	}

	final private int length;
	final private List<Double> list;
	final private List<Double> avgList;
	final private List<Double> emaList;

	final private double smoothingConst;

	private double average() {
		double sum = 0;
		for (double d : list) {
			sum += d;
		}
		double avg = sum / list.size();
		return avg;
	}

	private Double sma(Double newValue) {
		list.add(newValue);
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

	public Double add(Double newValue) {
		sma(newValue);
		ema(newValue);
		return getEma();
	}

	public Double getEma() {
		return emaList.size() > 0 ? emaList.get(emaList.size() - 1) : null;
	}
}
