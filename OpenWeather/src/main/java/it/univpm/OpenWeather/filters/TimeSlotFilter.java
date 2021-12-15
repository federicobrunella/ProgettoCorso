package it.univpm.OpenWeather.filters;

/**
 * @author Leonardo Pieralisi
 *
 */

public class TimeSlotFilter extends DailyFilter {

	private int timeSlot;

	public TimeSlotFilter(String timeSlot, String days) {

		super(days);
		this.timeSlot=Integer.valueOf(timeSlot);

	}

	public int getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}

}
