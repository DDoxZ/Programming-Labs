package tests;
import static org.junit.Assert.*;

import org.junit.Test;
import static exercise.RecognisePatterns.validForInt;
/**
*
* JUnit Tests for the method validForInt of the class RecognisePatterns
* 	
* Characteristic number 1 
* Description: the variable name within the loop must only contain letters or be blank
* Regions: true or false
*
* Characteristic number 2
* Description: the condition of the loop must always contain a boolean operator or be blank
* Regions: true or false
*
* Characteristic number 3 
* Description: the increment of the variable within the loop must always use ++ or -- or be blank
* Regions: true or false
*
* Characteristic number 4 
* Description: the loop must not have empty spaces except for the one next to the variable type or be blank within every region
* Regions: true or false
*
*
* Total number of viable combinations: 16
*
* @author Diogo Lopes 60447
*/
public class TestForInt {
	@Test
	/**
	* Test a for loop showing the correct format
	* 	variable name only with letters or blank: true
	* 	condition contains boolean operator or blank: true
	* 	increment only contains ++ or -- or be blank: true
	* 	no empty spaces with the mentioned exception or be blank: true
	*/
	public void validForInt1() {
		assertEquals(true, validForInt("for(int i=0;i<10;i++){}"));
	}
	
	@Test
	/**
	* Test a for loop with a variable with only letter or blank
	* 	variable name only with letters or blank: false
	* 	condition contains boolean operator or blank: true
	* 	increment only contains ++ or -- or be blank: true
	* 	no empty spaces with the mentioned exception or be blank: true
	*/
	public void validForInt2() {
		assertEquals(false, validForInt("for(int i2=0;i<10;i++){}"));
	}
	
	@Test
	/**
	* Test a for loop with boolean operator or black AND contains ++ or -- or blank within that conditions
	* 	variable name only with letters or blank: true
	* 	condition contains boolean operator or blank: true
	* 	increment only contains ++ or -- or be blank: false
	* 	no empty spaces with the mentioned exception or be blank: true
	*/
	public void validForInt3() {
		assertEquals(false, validForInt("for(int i=0;i<10;i=i+1){}"));
	}
	
	@Test
	/**
	* Test a for loop with no empty spaces with the mentioned expection or be blank within every region
	* 	variable name only with letters or blank: true
	* 	condition contains boolean operator or blank: true
	* 	increment only contains ++ or -- or be blank: true
	* 	no empty spaces with the mentioned exception or be blank within every region: false
	*/
	public void validForInt4() {
		assertEquals(false, validForInt("for(int i=0; i<10;i++) {}"));
	}
}