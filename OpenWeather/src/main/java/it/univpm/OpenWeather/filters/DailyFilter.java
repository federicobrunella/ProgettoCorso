package it.univpm.OpenWeather.filters;

/**
 * @author Leonardo Pieralisi
 *
 */

public class DailyFilter {

	protected int days;
	protected int counter;

	public DailyFilter(String days) {

		this.days=Integer.valueOf(days);
		this.counter=this.days*8;
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
