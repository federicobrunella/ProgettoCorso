package it.univpm.OpenWeather.model;

/**
 * @author Leonardo Pieralisi
 *
 */

public class WeatherData {

	private int dt;									//DataTime in formato Unix
	private String txtDateTime;						//DataTime in formato Testuale
	private double temp;							//Temperatura
	private double feelsLike;						//Temperatura percepita
	private double tempMin;							//Temperatura minima
	private double tempMax;							//Temperatura massima
	private String main;							//Condizione meteo 
	private String description;						//Descrizione condizione meteo

	public String toString() {
		String output = "Date Time: "+this.dt+"\n"
				+"Textual Date Time: "+this.txtDateTime+"\n"
				+"Temperature: "+this.temp+"\n"
				+"Feels Like: "+this.feelsLike+"\n"
				+"Min Temperature: "+this.tempMin+"\n"
				+"Max Temperature: "+this.tempMax+"\n"
				+"Weather: "+this.main+"\n"
				+"Weather Description: "+this.description+"\n";
		return output;
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
