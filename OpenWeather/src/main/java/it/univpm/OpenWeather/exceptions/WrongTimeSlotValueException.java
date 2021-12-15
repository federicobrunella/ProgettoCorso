/**
 * 
 */
package it.univpm.OpenWeather.exceptions;

/**
 * @author Federico Brunella
 *
 */
public class WrongTimeSlotValueException extends Exception{
	private String msg;

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
