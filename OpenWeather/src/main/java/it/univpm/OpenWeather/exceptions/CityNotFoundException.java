package it.univpm.OpenWeather.exceptions;

/**
 * @author Federico Brunella
 *
 */
public class CityNotFoundException extends Exception {

	private String msg;

	public CityNotFoundException() {
		super();
	}

	public CityNotFoundException(String msg) {
		super(msg);
		this.msg=msg;
	}
	
	public String message() {
		return this.msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
