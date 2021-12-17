package it.univpm.OpenWeather.filters;

import java.util.Calendar;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.exceptions.WrongTimeSlotValueException;
import it.univpm.OpenWeather.model.City;

/**
 * @author Leonardo Pieralisi
 *
 */

public class TimeSlotFilter extends DailyFilter {

	private int timeSlot;

	public TimeSlotFilter(City city, String timeSlot, String days) throws WrongDaysValueException, WrongTimeSlotValueException {

		super(city, days);

		this.timeSlot=Integer.valueOf(timeSlot);

		if(this.timeSlot<0 || this.timeSlot>21)
			throw new WrongTimeSlotValueException();
		if(this.timeSlot%3!=0)
			throw new WrongTimeSlotValueException();
	}

	public City filter() {

		//while(this.city.getForecast().size()>this.counter)
		// this.city.getForecast().remove(this.city.getForecast().size()-1);

		this.city = super.filter();

		for(int j=0; j<this.city.getForecast().size(); j++)
		{
			if(extractHour(this.city.getForecast().get(j).getDt()) != this.timeSlot) {
				this.city.getForecast().remove(j);
				j--;
			}
		}
		//System.out.println(this.city.getForecast().size());
		//System.out.println(this.city.getForecast().toString());
		return this.city;
	}

	private int extractHour(int timestamp) {

		Calendar calendar = Calendar.getInstance();				// creates a new calendar instance
		calendar.setTimeInMillis((long)timestamp*1000); 		// assigns calendar to given date 

		return calendar.get(Calendar.HOUR_OF_DAY)-1;			// -1 per il formato gmt+1, non ho trovato un altro modo per risolvere.
	}

	public int getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}
}