package it.univpm.OpenWeather.exceptions;

/**
 * Eccezione per l'inserimento di una località non corretta.
 * 
 * @author Federico Brunella
 *
 */
public class CityNotFoundException extends Exception {

	private String msg = "Error: city not found";

	public CityNotFoundException() {
		super();
	}

	public CityNotFoundException(String msg) {
		super(msg);
		this.msg=msg;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public String getMsg(String msg){
		this.msg = msg;
		return this.msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
