package it.univpm.OpenWeather.filters;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;

/**
 * @author Leonardo Pieralisi
 *
 */

public class DailyFilter {

	protected int days;
	protected int counter;

	public DailyFilter(String days) throws WrongDaysValueException {

		this.days=Integer.valueOf(days);
		this.counter=this.days*8;

		if(this.days<1 || this.days>5)
			throw new WrongDaysValueException();
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
