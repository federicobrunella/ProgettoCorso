package it.univpm.OpenWeather;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.OpenWeather.model.City;
import it.univpm.OpenWeather.model.WeatherData;
import it.univpm.OpenWeather.statistics.Statistics;

/**
 * @author Federico Brunella
 *
 */

class StatisticsTest {
	private static Statistics stats;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		City city = new City();
		WeatherData weatherData1 = new WeatherData();
		WeatherData weatherData2 = new WeatherData();
		WeatherData weatherData3 = new WeatherData();

		Vector <WeatherData> vector = new Vector<WeatherData>();
		
		weatherData1.setTemp(10);
		weatherData1.setFeelsLike(12);
		
		weatherData2.setTemp(20);
		weatherData2.setFeelsLike(22);
		
		weatherData3.setTemp(30);
		weatherData3.setFeelsLike(32);
		
		vector.add(weatherData1);
		vector.add(weatherData2);
		vector.add(weatherData3);

		
		city.setForecast(vector);
		
		stats = new Statistics(city);
		stats.calculateStatistics();
	}

	@Test
	void test() {
		assertEquals(20, stats.getAvgTemp());
		assertEquals(22, stats.getAvgFeelsLike());
	}
}
