package tests;
import static org.junit.Assert.*;

import org.junit.Test;
import static exercise.RecognisePatterns.validAddressJson;
/**
*
* JUnit Tests for the method validAddressJson of the class RecognisePatterns
* 	
* Characteristic number 1 
* Description: within the city field there should not be the char: "
* Regions: true or false
*
* Characteristic number 2
* Description: within the postalcode field there should only be 4 to 5 digits
* Regions: true or false
*
* Characteristic number 3 
* Description: wihtin the countryCode field there should be exacly 3 characters, all uppercase
* Regions: true or false
*
* Characteristic number 4 
* Description: the curly brackets at the end of the string should have the correct format: {{
* Regions: true or false
*
*
* Total number of viable combinations: 16
*
* @author Diogo Lopes 60447
*/
public class TestJsonAddress {
	@Test
	/**
	* Test a validAddressJson showing the correct format
	* 	no " within the city field: true
	* 	4 to 5 digits within the postalcode field: true
	* 	exacly 3 characters within the countryCode field: true
	* 	curly brackets correct format: true
	*/
	public void validForAdderssJson1() {
		assertEquals(true, validAddressJson("{\"address\":{\"postalCode\":\"12345\",\"city\":\"New York\",\"countryCode\":\"USA\"}}"));
	}
	
	@Test
	/**
	* Test a validAddressJson showing the correct format
	* 	no " within the city field: false
	* 	4 to 5 digits within the postalcode field: true
	* 	exacly 3 characters within the countryCode field: true
	* 	curly brackets correct format: true
	*/
	public void validForAdderssJson2() {
		assertEquals(false, validAddressJson("{\"address\":{\"postalCode\":\"12345\",\"city\":\"New Y\"ork\",\"countryCode\":\"USA\"}}"));
	}
	
	@Test
	/**
	* Test a validAddressJson showing the correct format
	* 	no " within the city field: true
	* 	4 to 5 digits within the postalcode field: false
	* 	exacly 3 characters within the countryCode field: true
	* 	curly brackets correct format: true
	*/
	public void validForAdderssJson3() {
		assertEquals(false, validAddressJson("{\"address\":{\"postalCode\":\"123645\",\"city\":\"New York\",\"countryCode\":\"USA\"}}"));
		assertEquals(false, validAddressJson("{\"address\":{\"postalCode\":\"123\",\"city\":\"New York\",\"countryCode\":\"USA\"}}"));
	}
	
	@Test
	/**
	* Test a validAddressJson showing the correct format
	* 	no " within the city field: true
	* 	4 to 5 digits within the postalcode field: true
	* 	exacly 3 characters within the countryCode field: false
	* 	curly brackets correct format: true
	*/
	public void validForAdderssJson4() {
		assertEquals(false, validAddressJson("{\"address\":{\"postalCode\":\"12345\",\"city\":\"New York\",\"countryCode\":\"CHINA\"}}"));
	}
}