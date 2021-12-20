package it.univpm.OpenWeather.model;

import java.util.Vector;

/**
 * Classe modello che racchiude tutti i dati di una località
 * Contiene anche i dati meteorologici della classe WeatherData
 * 
 * @author Leonardo Pieralisi
 *
 */

public class City {

	private String name;							//Nome città
	private String ID;								//Numero identificativo della città
	private String country;							//Nome paese
	private Vector <WeatherData> forecast;			//Vettore con i dati delle previsioni ogni 3 ore per 5 gg


	public String toString() {
		String output = "Name: "+ this.name+"\n"
				+"ID: "+this.ID+"\n"
				+"Country: "+this.country+"\n";

		for(WeatherData element: forecast) {
			output+="------------------------------------ \n";
			output+=element.toString();
		}
		return output;		
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

}
