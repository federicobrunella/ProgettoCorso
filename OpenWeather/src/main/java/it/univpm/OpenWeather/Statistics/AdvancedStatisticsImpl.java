package it.univpm.OpenWeather.Statistics;

import org.json.simple.JSONObject;

import it.univpm.OpenWeather.model.City;

/**
* @author Leonardo Pieralisi
*
*/

public class AdvancedStatisticsImpl extends Statistics {
	
	JSONObject obj;
	
	public AdvancedStatisticsImpl(City city, JSONObject obj) {
		
		this.city=city;
		this.obj=obj;
		//TODO:...
	}

	public JSONObject getJSONStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	public void calculateStatistics() {
		// TODO Auto-generated method stub
		
	}
	
}
