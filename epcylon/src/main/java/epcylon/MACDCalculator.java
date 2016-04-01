package epcylon;

public class MACDCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MACDCalculator() {
		_12 = new Calculator(12);
		_26 = new Calculator(26);
		_9 = new Calculator(9);
	}

	final private Calculator _12;
	final private Calculator _26;
	final private Calculator _9;

	public synchronized void add(Double newValue, String timeStamp) {
		Double a1 = _12.add(newValue, timeStamp);
		Double a2 = _26.add(newValue, timeStamp);
		if (a1 != null && a2 != null)
			_9.add(a1 - a2, timeStamp);
	}
}
