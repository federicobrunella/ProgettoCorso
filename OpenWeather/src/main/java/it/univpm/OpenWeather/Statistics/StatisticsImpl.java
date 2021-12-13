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
		
		JSONObject output = new JSONObject();
		
		output.put("city", city.getName());
		output.put("ID", city.getID());
		output.put("country", city.getCountry());
		
		JSONObject statsJSON = new JSONObject();
		
		statsJSON.put("avg_temp", this.avgTemp);
		statsJSON.put("avg_temp_min",this.avgTempMin);
		statsJSON.put("avg_temp_max", this.avgTempMax);
		statsJSON.put("avg_feels_like", this.avgFeelsLike);
		
		output.put("stats", statsJSON);
		
		return output;
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
