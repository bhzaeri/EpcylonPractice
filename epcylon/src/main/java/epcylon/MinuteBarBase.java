package epcylon;

public class MinuteBarBase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MinuteBarBase(int minuteBase, int secondBase) {
		super();
		this.minuteBase = minuteBase;
		this.secondBase = secondBase;
	}

	private int minuteBase;
	private int secondBase;

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
			if (this.minuteBase == t.minuteBase && this.secondBase == t.secondBase)
				return true;
		}
		return false;
	}

}
