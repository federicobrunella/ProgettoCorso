package it.univpm.OpenWeather.exceptions;

/**
 * Eccezione per l'inserimento di un valore non corretto del parametro timeSlot.
 * 
 * @author Federico Brunella
 *
 */
public class WrongTimeSlotValueException extends Exception{
	private String msg = "Error: param timeSlot must be 00 or 03 or 06 ... or 21";

	public WrongTimeSlotValueException(){
		super();
	}

	public WrongTimeSlotValueException(String msg){
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
