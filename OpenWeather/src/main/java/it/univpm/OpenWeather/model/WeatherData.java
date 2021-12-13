package it.univpm.OpenWeather.model;

/**
* @author Leonardo Pieralisi
*
*/

public class WeatherData {
	
	private int dt;					//DataTime in formato Unix
	private String txtDateTime;		//DataTime in formato Testuale
	private double temp;			//Temperatura
	private double feelsLike;		//Temperatura percepita
	private double tempMin;			//Temperatura minima
	private double tempMax;			//Temperatura massima
	
	public String toString() {
		//TODO: completare metodo toString!
		return "toDo";
	}
	public boolean equals(Object obj) {
		//TODO: completare metodo equals!
		return true;
	}
	
	public int getDt() {
		return dt;
	}
	public void setDt(int dt) {
		this.dt = dt;
	}
	public String getTxtDateTime() {
		return txtDateTime;
	}
	public void setTxtDateTime(String txtDateTime) {
		this.txtDateTime = txtDateTime;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getFeelsLike() {
		return feelsLike;
	}
	public void setFeelsLike(double feelsLike) {
		this.feelsLike = feelsLike;
	}
	public double getTempMin() {
		return tempMin;
	}
	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}
	public double getTempMax() {
		return tempMax;
	}
	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}
	
}
