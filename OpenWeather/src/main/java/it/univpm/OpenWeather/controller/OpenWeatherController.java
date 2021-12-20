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
import it.univpm.OpenWeather.statistics.Statistics;

/**
 * Classe che gestisce le chiamate in ingresso
 * 
 * @author Federico Brunella
 *
 */

@RestController
public class OpenWeatherController {



	@Autowired
	private WeatherServiceImpl weatherService;



	/**
	 * Rotta di tipo GET che restituisce i metadati.
	 * 
	 * @return JSONObject contenente i metadati
	 */
	@RequestMapping(value = "/getMetadata")
	public ResponseEntity<Object> getMetadata() {
		return new ResponseEntity<>(weatherService.getMetadata(), HttpStatus.OK);
	}


	/**
	 * Rotta di tipo GET per ottenere le condizioni meteo attuali di una determinata località
	 * 
	 * @param city: parametro per specificare la località di cui ottenere le previsioni [tipo String].
	 * @return JSONObject contenente le condizioni meteo della località scelta.
	 * @throws CityNotFoundException: se non e possibile trovare la località specificata.
	 */
	@RequestMapping(value = "/getCurrentWeather")
	public ResponseEntity<Object> getCurrentWeather(@RequestParam(name="city") String city) throws CityNotFoundException {
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


	/**
	 * Rotta per ottenere le previsioni meteo di una determinata località per i successivi 5 giorni (ogni 3 ore)
	 * 
	 * @param city: parametro per specificare la località di cui ottenere le previsioni [tipo String].
	 * @return JSONObject contenente le previsioni meteo della località scelta.
	 * @throws CityNotFoundException: se non e possibile trovare la località specificata.
	 */
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



	/**
	 * Rotta per ottenere le statistiche sulle previoni meteo relative a una specifica località
	 * con possibilità di applicare un filtro su base giornaliera
	 * 
	 * @param city: parametro per specificare la località di cui ottenere le previsioni [tipo String].
	 * @param days: parametro per specifcare l'intervallo temporale su cui effettuare
	 * 				le statistiche (espresso in giorni, valori compresi tra 1 e 5) [tipo String].
	 * @return JSONObject contenente le statistiche richieste
	 * @throws WrongDaysValueException: se viene inserito un valore di days minore di 1 o maggiore di 5
	 * @throws CityNotFoundException: se non e possibile trovare la località specificata.
	 * @throws NumberFormatException: se days non è un numero 
	 */
	@RequestMapping(value = "/getDailytStats")
	public ResponseEntity<Object> getDailyStats(@RequestParam(name="city") String city, @RequestParam(name="days") String days)
			throws NumberFormatException, WrongDaysValueException, CityNotFoundException{
		try {

			JSONObject JSONForecast = weatherService.getJSONForecast(city);
			DailyFilter filter = new DailyFilter(weatherService.JSONForecastToModelObj(JSONForecast), days);
			Statistics stats = new Statistics(filter.filter());

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


	/**
	 * Rotta per ottenere le statistiche sulle previoni meteo relative a una specifica località
	 * con possibilità di applicare un filtro su base giornaliera e per fascia oraria
	 * 
	 * @param city: parametro per specificare la località di cui ottenere le previsioni [tipo String].
	 * @param days: parametro per specifcare l'intervallo temporale su cui effettuare
	 * 				le statistiche (espresso in giorni, valori compresi tra 1 e 5) [tipo String].
	 * @param timeSlot: parametro per specificare la fascia oraria su cui filtrare i dati
	 * @return JSONObject contenente le statistiche richieste
	 * @throws NumberFormatException: se days non è un numero 
	 * @throws WrongDaysValueException: se viene inserito un valore di days minore di 1 o maggiore di 5
	 * @throws WrongTimeSlotValueException: se viene inserito un valore di days diverso da 00, 03, 06, ..., 18, 21.
	 * @throws CityNotFoundException: se non e possibile trovare la località specificata.
	 */
	@RequestMapping(value = "/getTimeSlotStats")
	public ResponseEntity<Object> getTimeSlotStats(@RequestParam(name="city") String city, @RequestParam(name="days") String days, @RequestParam(name="timeSlot") String timeSlot) 
			throws NumberFormatException, WrongDaysValueException, WrongTimeSlotValueException,CityNotFoundException {
		try {

			JSONObject JSONForecast = weatherService.getJSONForecast(city);
			TimeSlotFilter filter = new TimeSlotFilter(weatherService.JSONForecastToModelObj(JSONForecast), timeSlot, days);
			Statistics stats = new Statistics(filter.filter());

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
