package epcylon.exceptions;

public class CurrencyPairInvalidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258659359901017707L;

	public CurrencyPairInvalidException(String currency) {
		// TODO Auto-generated constructor stub
		this.currency = currency;
	}

	private String currency;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return currency + " is invalid.";
	}

}
