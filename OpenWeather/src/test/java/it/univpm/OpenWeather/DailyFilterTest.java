/**
 * 
 */
package it.univpm.OpenWeather;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.univpm.OpenWeather.exceptions.WrongDaysValueException;
import it.univpm.OpenWeather.filters.DailyFilter;

/**
 * @author Federico Brunella
 *
 */
class DailyFilterTest {

	/**
	 * @throws java.lang.Exception
	 */

	private String days = "06";
	private String days2 = "erff";
	private DailyFilter filter;

	@Test
	void test() {

		//-----------TEST 1------------------
		WrongDaysValueException wrongDays = assertThrows(WrongDaysValueException.class, () -> {
			filter = new DailyFilter(days);	  
		});

		assertEquals("Error:", wrongDays.getMsg("Error:"));
		//-----------------------------------

		//-----------TEST 2------------------
		NumberFormatException numberFormat = assertThrows(NumberFormatException.class, () -> {
			filter = new DailyFilter(days2);	  
		});

		assertEquals("Error: param days must be a number", "Error: param days must be a number");
		//-----------------------------------

	}
}
