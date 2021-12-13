package it.univpm.OpenWeather.model;

import java.util.Vector;

/**
* @author Leonardo Pieralisi
*
*/

public class City {
	
	private String name;							//Nome città
	private String ID;								//Numero identificativo della città
	private String country;							//Nome paese
	private Vector <WeatherData> forecast;			//Vettore con i dati delle previsioni ogni 3 ore per 5 gg
	private String main;							//Condizione meteo 
	private String description;						//Descrizione condizione meteo
	
	
	public String toString() {
		//TODO: finire il toString!
		return "todo";
	}
	public boolean equals(Object obj) {
		//TODO: finire l'equals!
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Vector<WeatherData> getForecast() {
		return forecast;
	}
	public void setForecast(Vector<WeatherData> forecast) {
		this.forecast = forecast;
	}
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
