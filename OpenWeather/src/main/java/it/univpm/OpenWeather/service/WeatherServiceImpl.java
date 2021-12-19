package it.univpm.OpenWeather.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import it.univpm.OpenWeather.exceptions.CityNotFoundException;
import it.univpm.OpenWeather.model.City;
import it.univpm.OpenWeather.model.WeatherData;

/**
 * @author Federico Brunella
 *
 */

@Service
public class WeatherServiceImpl implements WeatherService {

	private String apiKey = "0350270b1abadb862209083d1e1fa0bf";								//APIkey API OpenWeatherMap.org
	private String forecastURL = "https://api.openweathermap.org/data/2.5/forecast?q=";		//URL richiesta meteo real-time
	private String currentURL = "https://api.openweathermap.org/data/2.5/weather?q=";		//URL richiesta previsioni 5 giorni/3h

	//Metodo per ottenre in formato JSON i dati meteo in real time di una certa localita
	@Override
	public JSONObject getJSONCurrentWeather(String city) throws CityNotFoundException{
		String requestURL = currentURL + city + "&appid=" + apiKey;
		JSONObject obj = getJSONFromAPI(requestURL);

		if(obj==null)
			throw new CityNotFoundException();

		return obj;
	}

	//Metodo per ottenre in formato JSON le previsioni meteo di una certa localita
	@Override
	public JSONObject getJSONForecast(String city) throws CityNotFoundException{

		String requestURL = forecastURL + city + "&appid=" + apiKey;
		JSONObject obj = getJSONFromAPI(requestURL);

		if(obj==null)
			throw new CityNotFoundException();

		return obj;
	}

	//Inserisce negli oggetti del modello i dati meteo attuali necessari 
	@Override
	public City JSONCurrentToModelObj(JSONObject obj) {
		City city = new City();
		JSONObject main = (JSONObject)obj.get("main");
		JSONArray weather = (JSONArray)obj.get("weather");

		city.setName((String)obj.get("name"));
		city.setCountry((String)((JSONObject)obj.get("sys")).get("country"));
		city.setID(String.valueOf(obj.get("id")));

		Vector<WeatherData> weatherDataVector = new Vector<WeatherData>();
		WeatherData weatherData = new WeatherData();
		weatherData.setTemp(((Number)main.get("temp")).doubleValue());
		weatherData.setTempMin(((Number)main.get("temp_min")).doubleValue());
		weatherData.setTempMax(((Number)main.get("temp_max")).doubleValue());
		weatherData.setFeelsLike(((Number)main.get("feels_like")).doubleValue());

		Date date = new Date();
		weatherData.setDt((int)new Timestamp(date.getTime()).getTime());
		weatherData.setTxtDateTime((String)new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));

		weatherData.setMain((String)(((JSONObject)weather.get(0)).get("main")));
		weatherData.setDescription((String)(((JSONObject)weather.get(0)).get("description")));


		weatherDataVector.add(weatherData);
		city.setForecast(weatherDataVector);

		return city;
	}

	//Inserisce negli oggetti del modello i dati meteo delle previsioni necessari 
	@Override
	public City JSONForecastToModelObj(JSONObject obj) {
		City city = new City();
		Vector<WeatherData> weatherData = new Vector<WeatherData>();

		JSONObject cityData = (JSONObject)obj.get("city");
		JSONArray list = (JSONArray)obj.get("list");

		city.setName((String)cityData.get("name"));
		city.setCountry((String)cityData.get("country"));
		city.setID(String.valueOf(cityData.get("id")));


		for(int j = 0; j<list.size(); j++) {
			JSONObject listElement = (JSONObject)list.get(j);
			WeatherData singleForecast = new WeatherData();

			JSONObject weather = (JSONObject)((JSONArray)listElement.get("weather")).get(0);
			JSONObject main = (JSONObject)listElement.get("main");

			singleForecast.setDt(((Number)listElement.get("dt")).intValue());
			singleForecast.setTxtDateTime((String)listElement.get("dt_txt"));
			singleForecast.setTemp(((Number)main.get("temp")).doubleValue()); 
			singleForecast.setFeelsLike(((Number)main.get("feels_like")).doubleValue());
			singleForecast.setTempMin(((Number)main.get("temp_min")).doubleValue()); 
			singleForecast.setTempMax(((Number)main.get("temp_max")).doubleValue());

			singleForecast.setMain((String)weather.get("main"));
			singleForecast.setDescription((String)weather.get("description"));

			weatherData.add(singleForecast);
		}

		city.setForecast(weatherData);
		return city;
	}

	//Ritorna un JSONObject contenente solo i dati utili al progetto
	@Override
	public JSONObject ModelObjToMyJSON(City city) {
		JSONObject output = new JSONObject();

		output.put("city", city.getName());
		output.put("ID", city.getID());
		output.put("country",city.getCountry());

		JSONArray forecastList = new JSONArray();

		for(WeatherData singleForecast : city.getForecast()) {
			JSONObject forecast = new JSONObject();

			forecast.put("dt",singleForecast.getDt());
			forecast.put("txtDateTime", singleForecast.getTxtDateTime());
			forecast.put("temp", singleForecast.getTemp());
			forecast.put("temp_min", singleForecast.getTempMin());
			forecast.put("temp_max", singleForecast.getTempMax());
			forecast.put("feels_like", singleForecast.getFeelsLike());

			forecast.put("main", singleForecast.getMain());
			forecast.put("description", singleForecast.getDescription());

			forecastList.add(forecast);
		}

		output.put("weather",forecastList);

		return output;
	}

	//Ritorna i metadati utili alla comprensione del JSON in output dagli altri metodi
	@Override
	public JSONObject getMetadata() {
		JSONObject metadata = new JSONObject();

		metadata.put("city", "city name [type: String]" );
		metadata.put("ID", " : city ID [type: String]");
		metadata.put("country"," : country [type: String]");

		JSONArray weatherDataJSONArray = new JSONArray();
		JSONObject weatherData = new JSONObject();

		weatherData.put("dt"," : UNIX timestamp [type: int]");
		weatherData.put("txtDateTime", " : human readble timestamp (format: YYYY-MM-DD hh:mm:ss)[type: String]");
		weatherData.put("temp", ": temperature [type: double]");
		weatherData.put("temp_min", ": minimum temperature [type: double]");
		weatherData.put("temp_max", ": maximum temperature [type: double]");
		weatherData.put("feels_like", ": feels like temperature [type: double]");

		weatherData.put("main", " : descripion of the weather(ex. sunny) [type: String]");
		weatherData.put("description", " : detailed descripion of the weather [type: String]");

		weatherDataJSONArray.add(weatherData);

		metadata.put("weatherData",weatherDataJSONArray);

		return metadata;
	}


	//Salva su un file (.json) i dati meteo attuali di una certa località
	@Override
	public void saveToFile(JSONObject obj) {
		String timeStamp =new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date().getTime());
		String city = ((String)obj.get("city"));

		String fileName = "localData/CurrentWeather_" + city + "_" + timeStamp +".json";//+timeStamp+".json";

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(obj.toJSONString());
			out.close();
		} 
		catch ( IOException e) {
			System.out.println("I/O error");
			System.out.println(e);
		}
	}


	//Metodo privato utile a ottenere la risposta (JSONObject) di una chiamata alle API OpenWeather
	private JSONObject getJSONFromAPI(String url) {
		JSONObject obj = null;

		try {
			URLConnection openConnection = new URL(url).openConnection();
			InputStream in = openConnection.getInputStream();

			String data = "";
			String line = "";
			try {
				InputStreamReader inR = new InputStreamReader( in );
				BufferedReader buf = new BufferedReader( inR );

				while ( ( line = buf.readLine() ) != null ) {
					data+= line;
				}
			} finally {
				in.close();
			}
			obj = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object


		} catch (IOException | ParseException e) {
			//e.printStackTrace();
			System.out.println("ERROR: I/O error or parse error");
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("ERROR");
		}			

		return obj;
	}

}
