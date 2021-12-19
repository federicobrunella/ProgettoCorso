/**
 * 
 */
package it.univpm.OpenWeather;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.exceptions.WrongTimeSlotValueException;
import it.univpm.OpenWeather.filters.DailyFilter;
import it.univpm.OpenWeather.filters.TimeSlotFilter;
import it.univpm.OpenWeather.model.City;

/**
 * @author Federico Brunella
 *
 */
class WrongTimeSlotValueExceptionTest {

	private TimeSlotFilter filter;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {
		City city = new City();

		WrongTimeSlotValueException exc = assertThrows(WrongTimeSlotValueException.class, () -> {
			filter = new TimeSlotFilter(city, "2", "02");	  
		});

		assertEquals("Error: param timeSlot must be 00 or 03 or 06 ... or 21", exc.getMsg());
	}

}
