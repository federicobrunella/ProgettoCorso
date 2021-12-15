package it.univpm.OpenWeather.filters;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.exceptions.WrongTimeSlotValueException;

/**
 * @author Leonardo Pieralisi
 *
 */

public class TimeSlotFilter extends DailyFilter {

	private int timeSlot;

	public TimeSlotFilter(String timeSlot, String days) throws WrongDaysValueException, WrongTimeSlotValueException {

		super(days);
		this.timeSlot=Integer.valueOf(timeSlot);

		if(this.timeSlot<1 || this.timeSlot>21)
			throw new WrongTimeSlotValueException();
		if(this.timeSlot%3!=0)
			throw new WrongTimeSlotValueException();
	}

	public int getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}

}
