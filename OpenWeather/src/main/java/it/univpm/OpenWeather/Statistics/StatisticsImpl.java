package it.univpm.OpenWeather.Statistics;

import org.json.simple.JSONObject;

import it.univpm.OpenWeather.model.City;

/**
* @author Leonardo Pieralisi
*
*/

public class StatisticsImpl extends Statistics {
	
	private int days;
	private int forecastCount;
	
	public StatisticsImpl(City city, String days) {
		
		this.city=city;
		this.days=Integer.valueOf(days);
		this.forecastCount=this.days*8;  //ogni giorno ricevo le previsioni ogni 3 ore...
	}
	
	public JSONObject getJSONStatistics() {
		//TODO: fare l'output
		return null;
	}
	
	public void calculateStatistics() {
		//TODO: calcolo
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getForecastCount() {
		return forecastCount;
	}

	public void setForecastCount(int forecastCount) {
		this.forecastCount = forecastCount;
	}
	
	
}
