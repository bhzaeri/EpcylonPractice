package epcylon.exceptions;

public class MinuteBarInvalidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258659359901017707L;

	public MinuteBarInvalidException(Integer minuteBar) {
		// TODO Auto-generated constructor stub
		this.minuteBar = minuteBar;
	}

	private Integer minuteBar;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return minuteBar + " is invalid.";
	}

}
