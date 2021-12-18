package it.univpm.OpenWeather;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.filters.DailyFilter;
import it.univpm.OpenWeather.model.City;

/**
 * @author Federico Brunella
 *
 */

class WrongDaysValueExceptionTest {
	private DailyFilter filter;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {
		City city = new City();
		
		WrongDaysValueException exc = assertThrows(WrongDaysValueException.class, () -> {
			filter = new DailyFilter(city, "8");	  
		});

		assertEquals("Error: param days must be between 1 and 5", exc.getMsg());
	}

}
