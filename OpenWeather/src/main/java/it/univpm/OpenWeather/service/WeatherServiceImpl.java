/**
 * 
 */
package it.univpm.OpenWeather.service;

import java.io.BufferedReader;
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
