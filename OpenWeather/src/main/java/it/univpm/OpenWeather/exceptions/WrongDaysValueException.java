package it.univpm.OpenWeather.exceptions;

/**
 * Eccezione per l'inserimento non corretto del parametro days.
 * 
 * @author Federico Brunella
 *
 */
public class WrongDaysValueException extends Exception{
	
	private String msg = "Error: param days must be between 1 and 5";
	
	public WrongDaysValueException(){
		super();
	}
	
	public WrongDaysValueException(String msg){
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
