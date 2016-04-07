package epcylon;

public enum MinuteBarsEnum {
	AUD_USD_1(1,CurrencyPair.AUD_USD), AUD_USD_2(2,CurrencyPair.AUD_USD), AUD_USD_3(3,CurrencyPair.AUD_USD), AUD_USD_4(4,CurrencyPair.AUD_USD), AUD_USD_5(5,CurrencyPair.AUD_USD), AUD_USD_6(6,CurrencyPair.AUD_USD), AUD_USD_10(10,CurrencyPair.AUD_USD), AUD_USD_12(12,CurrencyPair.AUD_USD), AUD_USD_15(15,CurrencyPair.AUD_USD), AUD_USD_20(20,CurrencyPair.AUD_USD), AUD_USD_30(30,CurrencyPair.AUD_USD),
	EUR_USD_1(1,CurrencyPair.EUR_USD), EUR_USD_2(2,CurrencyPair.EUR_USD), EUR_USD_3(3,CurrencyPair.EUR_USD), EUR_USD_4(4,CurrencyPair.EUR_USD), EUR_USD_5(5,CurrencyPair.EUR_USD), EUR_USD_6(6,CurrencyPair.EUR_USD), EUR_USD_10(10,CurrencyPair.EUR_USD), EUR_USD_12(12,CurrencyPair.EUR_USD), EUR_USD_15(15,CurrencyPair.EUR_USD), EUR_USD_20(20,CurrencyPair.EUR_USD), EUR_USD_30(30,CurrencyPair.EUR_USD),
	USD_CAD_1(1,CurrencyPair.USD_CAD), USD_CAD_2(2,CurrencyPair.USD_CAD), USD_CAD_3(3,CurrencyPair.USD_CAD), USD_CAD_4(4,CurrencyPair.USD_CAD), USD_CAD_5(5,CurrencyPair.USD_CAD), USD_CAD_6(6,CurrencyPair.USD_CAD), USD_CAD_10(10,CurrencyPair.USD_CAD), USD_CAD_12(12,CurrencyPair.USD_CAD), USD_CAD_15(15,CurrencyPair.USD_CAD), USD_CAD_20(20,CurrencyPair.USD_CAD), USD_CAD_30(30,CurrencyPair.USD_CAD),
	USD_JPY_1(1,CurrencyPair.USD_JPY), USD_JPY_2(2,CurrencyPair.USD_JPY), USD_JPY_3(3,CurrencyPair.USD_JPY), USD_JPY_4(4,CurrencyPair.USD_JPY), USD_JPY_5(5,CurrencyPair.USD_JPY), USD_JPY_6(6,CurrencyPair.USD_JPY), USD_JPY_10(10,CurrencyPair.USD_JPY), USD_JPY_12(12,CurrencyPair.USD_JPY), USD_JPY_15(15,CurrencyPair.USD_JPY), USD_JPY_20(20,CurrencyPair.USD_JPY), USD_JPY_30(30,CurrencyPair.USD_JPY);
	
	private Integer minute;
	CurrencyPair pair;
	private MinuteBarsEnum(Integer minute,CurrencyPair pair) {
		this.minute = minute;
		this.pair = pair;
	}

	public Integer getMinute() {
		return minute;
	}

	public CurrencyPair getPair() {
		return pair;
	}
	
	public static MinuteBarsEnum getValue(Integer minuteBar, CurrencyPair pair){
		for (MinuteBarsEnum barsEnum : MinuteBarsEnum.values()) {
			if(barsEnum.getMinute().equals(minuteBar) && barsEnum.getPair().equals(pair))
				return barsEnum;
		}
		return null;
	}
}
