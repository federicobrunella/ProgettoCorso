package it.univpm.OpenWeather.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import it.univpm.OpenWeather.model.City;
import it.univpm.OpenWeather.model.WeatherData;

/**
 * @author Federico Brunella
 *
 */

@Service
public class WeatherServiceImpl implements WeatherService {
	
	private String apiKey = "0350270b1abadb862209083d1e1fa0bf";
	private String forecastURL = "https://api.openweathermap.org/data/2.5/forecast?q=";
	private String currentURL = "https://api.openweathermap.org/data/2.5/weather?q=";
	
	@Override
	public JSONObject getJSONCurrentWeather(String city) {
		String requestURL = currentURL + city + "&appid=" + apiKey;
		return getJSONFromAPI(requestURL);
	}

	@Override
	public JSONObject getJSONForecast(String city) {
		String requestURL = forecastURL + city + "&appid=" + apiKey;
		return getJSONFromAPI(requestURL);

	}
	
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

	@Override
	public JSONObject getMetadata() {
		JSONObject metadata = new JSONObject();

		metadata.put("city", " : city name");
		metadata.put("ID", " : city ID");
		metadata.put("country"," : country");

		JSONArray forecastList = new JSONArray();

		for(int j = 0; j<40; j++) {
			JSONObject forecast = new JSONObject();

			forecast.put("dt"," : UNIX timestamp");
			forecast.put("txtDateTime", " : human readble timestamp( format: )");
			forecast.put("temp", ": temperature");
			forecast.put("temp_min", ": minimum temperature");
			forecast.put("temp_max", ": maximum temperature");
			forecast.put("feels_like", ": feels like temperature");

			forecast.put("main", " : descripion of the weather(ex. sunny)");
			forecast.put("description", " : detailed descripion of the weather");

			forecastList.add(forecast);
		}

		metadata.put("list",forecastList);

		return metadata;
	}


	@Override
	public void saveToFile(JSONObject obj) {
		//TODO: save to file
	}
	
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
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			

		return obj;
	}

}
