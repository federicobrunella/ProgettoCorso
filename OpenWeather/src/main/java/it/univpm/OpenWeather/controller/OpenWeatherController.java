package it.univpm.OpenWeather.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.OpenWeather.service.WeatherService;
import it.univpm.OpenWeather.statistics.StatisticsImpl;

/**
 * @author Federico Brunella
 *
 */

@RestController
public class OpenWeatherController {

	@Autowired
	private WeatherService weatherService;
	
	//Rotta /getMetatdata per ottenere i metadati
	@RequestMapping(value = "/getMetadata")
	public ResponseEntity<Object> getMetadata() {
		return new ResponseEntity<>(weatherService.getMetadata(), HttpStatus.OK);
	}
	
	//Rotta /getCurrentWeather per ottenere il meteo attuale, parametri accettati: 
	//	"city"= la località su cui effettuare la ricerca
	@RequestMapping(value = "/getCurrentWeather")
	public ResponseEntity<Object> getCurrentWeather(@RequestParam(name="city") String city) {
		JSONObject output = weatherService.ModelObjToMyJSON(weatherService.JSONCurrentToModelObj(weatherService.getJSONCurrentWeather(city)));
		weatherService.saveToFile(output);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	//Rotta /getWeatherForecast per ottenre le previsioni meteo, parametri accettati: 
	//	"city"= la località su cui effettuare la ricerca 
	@RequestMapping(value = "/getWeatherForecast")
	public ResponseEntity<Object> getForecastbyCity(@RequestParam(name="city") String city) {
		return new ResponseEntity<>(weatherService.ModelObjToMyJSON(weatherService.JSONForecastToModelObj(weatherService.getJSONForecast(city))), HttpStatus.OK);
	}

	//Rotta /getStatistics per ottenere le statistiche sui dati meteo, parametri accettati: 
	//  "city"= la località su cui effettuare la ricerca
	//	"days"= i giorni su cui calcolare le statistiche (da 1 a 5, prossimi giorni)
	@RequestMapping(value = "/getStatistics")
	public ResponseEntity<Object> getStatistics(@RequestParam(name="city") String city, @RequestParam(name="days", defaultValue="5") String days) {
		StatisticsImpl stats = new StatisticsImpl(weatherService.JSONForecastToModelObj(weatherService.getJSONForecast(city)), days);
		stats.calculateStatistics();
		return new ResponseEntity<>(stats.getJSONStatistics(), HttpStatus.OK);
	}

	//TODO: richiesta post per statistiche con filtro più avanzato( /getAdvancedStats)

}
