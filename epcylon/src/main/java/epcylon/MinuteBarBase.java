package epcylon;

public class MinuteBarBase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MinuteBarBase(String currency, int minuteBase, int secondBase) {
		super();
		this.currency = currency;
		this.minuteBase = minuteBase;
		this.secondBase = secondBase;
	}

	private String currency;
	private int minuteBase;
	private int secondBase;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getMinuteBase() {
		return minuteBase;
	}

	public void setMinuteBase(int minuteBase) {
		this.minuteBase = minuteBase;
	}

	public int getSecondBase() {
		return secondBase;
	}

	public void setSecondBase(int secondBase) {
		this.secondBase = secondBase;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj != null && obj instanceof MinuteBarBase) {
			MinuteBarBase t = (MinuteBarBase) obj;
			if (this.minuteBase == t.minuteBase && this.secondBase == t.secondBase && this.currency.equals(t.currency))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 1;
	}

}
