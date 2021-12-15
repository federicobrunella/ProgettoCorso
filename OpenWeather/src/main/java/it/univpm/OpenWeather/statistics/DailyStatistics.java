package it.univpm.OpenWeather.statistics;

import it.univpm.OpenWeather.filters.DailyFilter;
import it.univpm.OpenWeather.model.City;

/**
 * @author Leonardo Pieralisi
 *
 */

public class DailyStatistics extends Statistics {

	private DailyFilter filter;

	public DailyStatistics(DailyFilter filter, City city) {

		super(city);
		this.filter=filter;
	}

	public void calculateStatistics() {

		for(int j=0; j<filter.getCounter(); j++) {

			this.avgTemp+=(city.getForecast().get(j)).getTemp();
			this.avgTempMin+=(city.getForecast().get(j)).getTempMin();
			this.avgTempMax+=(city.getForecast().get(j)).getTempMax();
			this.avgFeelsLike+=(city.getForecast().get(j)).getFeelsLike();
		}

		try {
			this.avgTemp=this.avgTemp/filter.getCounter();
			this.avgTempMin=this.avgTempMin/filter.getCounter();
			this.avgTempMax=this.avgTempMax/filter.getCounter();
			this.avgFeelsLike=this.avgFeelsLike/filter.getCounter();
		}
		catch(ArithmeticException e){
			System.out.println("Errore, divisione per 0! Passato parametro non valido");
		}

	}

	public DailyFilter getFilter() {
		return filter;
	}

	public void setFilter(DailyFilter filter) {
		this.filter = filter;
	}

}
