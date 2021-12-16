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
import it.univpm.OpenWeather.exceptions.WrongTimeSlotValueException;
import it.univpm.OpenWeather.filters.DailyFilter;
import it.univpm.OpenWeather.filters.TimeSlotFilter;
import it.univpm.OpenWeather.service.WeatherServiceImpl;
import it.univpm.OpenWeather.statistics.DailyStatistics;
import it.univpm.OpenWeather.statistics.TimeSlotStatistics;

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
		try {

			JSONObject JSONCurrentWeather = weatherService.getJSONCurrentWeather(city);
			JSONObject output = weatherService.ModelObjToMyJSON(weatherService.JSONCurrentToModelObj(JSONCurrentWeather));
			weatherService.saveToFile(output);

			return new ResponseEntity<>(output, HttpStatus.OK);

		} catch(CityNotFoundException e) {
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST); 
		} catch(Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST); 
		}	

	}

	//Rotta /getWeatherForecast per ottenre le previsioni meteo, parametri accettati: 
	//	"city"= la località su cui effettuare la ricerca 
	@RequestMapping(value = "/getWeatherForecast")
	public ResponseEntity<Object> getForecastbyCity(@RequestParam(name="city") String city) throws CityNotFoundException {
		try {

			JSONObject JSONForecast = weatherService.getJSONForecast(city);	

			return new ResponseEntity<>(weatherService.ModelObjToMyJSON(weatherService.JSONForecastToModelObj(JSONForecast)), HttpStatus.OK);

		} catch(CityNotFoundException e) {
			System.out.println("Error: city not found");
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST); 
		} catch(Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST); 
		}	
	}

	//Rotta /getDailyStats per ottenere le statistiche sui dati meteo con filtro giornaliero, parametri accettati: 
	//  "city"= la località su cui effettuare la ricerca
	//	"days"= i giorni su cui calcolare le statistiche (da 1 a 5, prossimi giorni)
	@RequestMapping(value = "/getDailytStats")
	public ResponseEntity<Object> getDailyStats(@RequestParam(name="city") String city, @RequestParam(name="days") String days)
			throws WrongDaysValueException, CityNotFoundException{
		try {

			DailyFilter filter = new DailyFilter(days);
			JSONObject JSONForecast = weatherService.getJSONForecast(city);
			DailyStatistics stats = new DailyStatistics(filter,weatherService.JSONForecastToModelObj(JSONForecast));
			return new ResponseEntity<>(stats.getJSONStatistics(), HttpStatus.OK);

		} catch(NumberFormatException e) {
			return new ResponseEntity<>("ERROR: param should be a number", HttpStatus.BAD_REQUEST);
		} catch(WrongDaysValueException e) {
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST);
		} catch(CityNotFoundException e) {
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST); 
		} catch(Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST); 
		}	

	}


	//Rotta /getTimeSlotStats per ottenere le statistiche sui dati meteo con filtro giornaliero/fascia oraria, parametri accettati: 
	//  "city"= la località su cui effettuare la ricerca
	//	"days"= i giorni su cui calcolare le statistiche (da 1 a 5, prossimi giorni)
	//  "timeSlot" = fascia oraria (da 00 a 21, ogni 3h, quindi ammessi come parametri: 00, 03, 06, ..., 21)
	@RequestMapping(value = "/getTimeSlotStats")
	public ResponseEntity<Object> getTimeSlotStats(@RequestParam(name="city") String city, @RequestParam(name="days") String days, @RequestParam(name="timeSlot") String timeSlot) {
		try {

			TimeSlotFilter filter = new TimeSlotFilter(timeSlot, days);
			JSONObject JSONForecast = weatherService.getJSONForecast(city);
			TimeSlotStatistics stats = new TimeSlotStatistics(filter,weatherService.JSONForecastToModelObj(JSONForecast));

			return new ResponseEntity<>(stats.getJSONStatistics(), HttpStatus.OK);

		} catch(NumberFormatException e) {
			return new ResponseEntity<>("ERROR: param should be a number", HttpStatus.BAD_REQUEST);
		} catch(WrongDaysValueException e) {
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST);
		} catch(WrongTimeSlotValueException e) {
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST);
		} catch(CityNotFoundException e) {
			return new ResponseEntity<>(e.getMsg(), HttpStatus.BAD_REQUEST); 
		} catch(Exception e) {
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		}
	}
}
