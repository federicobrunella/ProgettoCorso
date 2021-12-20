package it.univpm.OpenWeather.filters;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.model.City;

/**
 * Classe che si occupa del filtraggio dei dati su base giornaliera
 * 
 * @author Leonardo Pieralisi
 *
 */

public class DailyFilter {



	protected int days;
	protected int counter;
	protected City city;

	/**
	 * Costruttore
	 * @param city: modello contenente i dati da filtrare
	 * @param days: parametro che specifica su quanti giorni effettuare il filtraggio 
	 * @throws WrongDaysValueException
	 */

	public DailyFilter(City city, String days) throws WrongDaysValueException {
		this.city=city;

		this.days=Integer.valueOf(days);
		this.counter=this.days*8;



		if(this.days<1 || this.days>5)
			throw new WrongDaysValueException();
	}

	/**
	 * Metodo che filtra i dati in base al parametro temporale passato
	 * @return Oggetto City contenente gli elementi filtrati
	 */

	public City filter() {

		//rimuovo l'ultimo elemento finche non raggiungo le dim giuste
		while(this.city.getForecast().size()>this.counter) {

			this.city.getForecast().remove(this.city.getForecast().size()-1);
		}

		return this.city;
	}

	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
}

