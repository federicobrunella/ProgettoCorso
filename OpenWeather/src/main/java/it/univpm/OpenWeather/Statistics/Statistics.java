package it.univpm.OpenWeather.Statistics;

import org.json.simple.JSONObject;

import it.univpm.OpenWeather.model.City;

/**
* @author Leonardo Pieralisi
*
*/

public abstract class Statistics {
	
	protected double avgTempMin;
	protected double avgTempMax;
	protected double avgTemp;
	protected double avgFeelsLike;
	
	protected City city;
	
	public abstract JSONObject getJSONStatistics();			//restituisce i risultati della statistica in formato JSON
	public abstract void calculateStatistics();				//calcola le statistiche
	
	public String toString() {
		//TODO: fare il toString()!
		return "todo";
	}
	
	public boolean equals() {
		//TODO: fare l'equals!
		return true;
	}
	public double getAvgTempMin() {
		return avgTempMin;
	}
	public void setAvgTempMin(double avgTempMin) {
		this.avgTempMin = avgTempMin;
	}
	public double getAvgTempMax() {
		return avgTempMax;
	}
	public void setAvgTempMax(double avgTempMax) {
		this.avgTempMax = avgTempMax;
	}
	public double getAvgTemp() {
		return avgTemp;
	}
	public void setAvgTemp(double avgTemp) {
		this.avgTemp = avgTemp;
	}
	public double getAvgFeelsLike() {
		return avgFeelsLike;
	}
	public void setAvgFeelsLike(double avgFeelsLike) {
		this.avgFeelsLike = avgFeelsLike;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	
}
