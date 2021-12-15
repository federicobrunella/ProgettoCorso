package it.univpm.OpenWeather.statistics;

import java.util.Calendar;

import it.univpm.OpenWeather.filters.TimeSlotFilter;
import it.univpm.OpenWeather.model.City;

/**
 * @author Federico Brunella
 *
 */

public class TimeSlotStatistics extends Statistics {
	private TimeSlotFilter filter;

	public TimeSlotStatistics(TimeSlotFilter filter, City city) {
		super(city);
		this.filter=filter;
	}
	
	public void calculateStatistics() {		
		for(int j = 0; j<filter.getCounter(); j++) {
			if(filter.getTimeSlot() == extractHour((city.getForecast().get(j)).getDt())) {
				this.avgTemp += (city.getForecast().get(j)).getTemp();
				this.avgTempMin += (city.getForecast().get(j)).getTempMin();
				this.avgTempMax += (city.getForecast().get(j)).getTempMax();
				this.avgFeelsLike += (city.getForecast().get(j)).getFeelsLike();
				//System.out.println(extractHour((city.getForecast().get(j)).getDt()));
			}
			//System.out.println(extractHour((city.getForecast().get(j)).getDt()));
		}

		try {
			this.avgTempMin = this.avgTempMin/filter.getDays();
			this.avgTempMax = this.avgTempMax/filter.getDays();
			this.avgTemp = this.avgTemp/filter.getDays();
			this.avgFeelsLike = this.avgFeelsLike/filter.getDays();
		}
		catch (ArithmeticException e){
			System.out.println("Errore divisione per zero! passato parametro invalido");
		}
	}

	private int extractHour(int timestamp) {
		Calendar calendar = Calendar.getInstance();// creates a new calendar instance
		calendar.setTimeInMillis((long)timestamp*1000);   // assigns calendar to given date

		return calendar.get(Calendar.HOUR_OF_DAY)-1; // gets hour in 24h format*/
	}
}
