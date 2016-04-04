package epcylon.enums;

public enum CurrencyPair {
	AUD_USD("AUD-USD"), EUR_USD("EUR-USD"), USD_JPY("USD-JPY"), USD_CAD("USD-CAD");

	private String pair;

	private CurrencyPair(String pair) {
		this.pair = pair;
	}

	public String getPair() {
		return pair;
	}

	public static CurrencyPair getValue(String pair) {
		for (CurrencyPair currencyPair : values()) {
			if (currencyPair.pair.equals(pair))
				return currencyPair;
		}
		return null;
	}
}
