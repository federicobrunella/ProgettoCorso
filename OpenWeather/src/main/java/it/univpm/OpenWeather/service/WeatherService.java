package it.univpm.OpenWeather.service;

import org.json.simple.JSONObject;

import it.univpm.OpenWeather.exceptions.CityNotFoundException;
import it.univpm.OpenWeather.model.City;

/**
 * Interfaccia contenente tutti i metodi necessari per le attività del controllere, implementati in WeatherServiceImpl.java
 * 
 * @author Federico Brunella
 *
 */

public interface WeatherService {
	public abstract JSONObject getMetadata();
	public abstract JSONObject getJSONForecast(String city) throws CityNotFoundException;
	public abstract JSONObject getJSONCurrentWeather(String city) throws CityNotFoundException;
	public abstract City JSONForecastToModelObj(JSONObject obj);
	public abstract City JSONCurrentToModelObj(JSONObject obj);
	public abstract JSONObject ModelObjToMyJSON(City city);
		
	public abstract void saveToFile(JSONObject obj);
}
