package it.univpm.OpenWeather.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.OpenWeather.exceptions.CityNotFoundException;
import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.service.WeatherServiceImpl;
import it.univpm.OpenWeather.statistics.StatisticsImpl;

/**
 * @author Federico Brunella
 *
 */

@RestController
public class OpenWeatherController {

	@Autowired
	private WeatherServiceImpl weatherService;

	//Rotta /getMetatdata per ottenere i metadati
	@RequestMapping(value = "/getMetadata")
	public ResponseEntity<Object> getMetadata() {
		return new ResponseEntity<>(weatherService.getMetadata(), HttpStatus.OK);
	}

	//Rotta /getCurrentWeather per ottenere il meteo attuale, parametri accettati: 
	//	"city"= la località su cui effettuare la ricerca
	@RequestMapping(value = "/getCurrentWeather")
	public ResponseEntity<Object> getCurrentWeather(@RequestParam(name="city") String city) {
		JSONObject JSONCurrentWeather = weatherService.getJSONCurrentWeather(city);

		if(JSONCurrentWeather != null) {
			JSONObject output = weatherService.ModelObjToMyJSON(weatherService.JSONCurrentToModelObj(JSONCurrentWeather));
			weatherService.saveToFile(output);
			return new ResponseEntity<>(output, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(new CityNotFoundException("ERROR: City not found").message(), HttpStatus.BAD_REQUEST);	

	}

	//Rotta /getWeatherForecast per ottenre le previsioni meteo, parametri accettati: 
	//	"city"= la località su cui effettuare la ricerca 
	@RequestMapping(value = "/getWeatherForecast")
	public ResponseEntity<Object> getForecastbyCity(@RequestParam(name="city") String city) {
		JSONObject JSONForecast = weatherService.getJSONForecast(city);

		if(JSONForecast != null)	
			return new ResponseEntity<>(weatherService.ModelObjToMyJSON(weatherService.JSONForecastToModelObj(JSONForecast)), HttpStatus.OK);
		else
			return new ResponseEntity<>(new CityNotFoundException("ERROR: City not found").message(), HttpStatus.BAD_REQUEST);	
	}

	//Rotta /getStatistics per ottenere le statistiche sui dati meteo, parametri accettati: 
	//  "city"= la località su cui effettuare la ricerca
	//	"days"= i giorni su cui calcolare le statistiche (da 1 a 5, prossimi giorni)
	@RequestMapping(value = "/getStatistics")
	public ResponseEntity<Object> getStatistics(@RequestParam(name="city") String city, @RequestParam(name="days", defaultValue="5") String days) {
		try {
			if(Integer.valueOf(days)>0 && Integer.valueOf(days)<=5) {
				JSONObject JSONForecast = weatherService.getJSONForecast(city);

				if(JSONForecast != null) {
					StatisticsImpl stats = new StatisticsImpl(weatherService.JSONForecastToModelObj(JSONForecast), days);
					stats.calculateStatistics();
					return new ResponseEntity<>(stats.getJSONStatistics(), HttpStatus.OK);
				}
				else
					return new ResponseEntity<>(new CityNotFoundException("ERROR: City not found").message(), HttpStatus.BAD_REQUEST);	

			}else
				return new ResponseEntity<>(new WrongDaysValueException("ERROR: param days should be between 1 and 5").message(), HttpStatus.BAD_REQUEST);		

		}
		catch(NumberFormatException e) {
			return new ResponseEntity<>("ERROR: param days should be a number", HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		}
	}

	//TODO: richiesta post per statistiche con filtro più avanzato( /getAdvancedStats)

}
