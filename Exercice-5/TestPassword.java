package tests;
import static org.junit.Assert.*;

import org.junit.Test;
import static exercise.RecognisePatterns.passwordStrength;
/**
*
* JUnit Tests for the method passwordStrength of the class RecognisePatterns
* 	
* Characteristic number 1 
* Description: the password must contain at least one special character
* Regions: true or false
*
* Characteristic number 2
* Description: the password must contain at least one digit
* Regions: true or false
*
* Characteristic number 3 
* Description: the password must contain at least one capitalized character
* Regions: true or false
*
* Characteristic number 4 
* Description: the password must contain at least 6 characters and at most 15 characters
* Regions: true or false
*
*
* Total number of viable combinations: 16
*
* @author Diogo Lopes 60447
*/

public class TestPassword {
    @Test
	/**
	* Test a password showing the correct format
	*   contains special chars: true
	*   contains digits: true
	*   contains capitalized: true
	*   length between 6 and 15: true
	*/
	public void testPasswordStrength1() {	
   		assertEquals(7, passwordStrength("passworD1_"));
	}

	@Test
	/**
	* Test a password missing a digit
	*   contains special chars: true
	*   contains digits: false
	*   contains capitalized: true
	*   length between 6 and 15: true
	*/
	public void testPasswordStrength2() {	
   		assertEquals(-1, passwordStrength("passworD_"));
	}

	@Test
	/**
	* Test a password missing a special char 
	*   contains special chars: false
	*   contains digits: true
	*   contains capitalized: true
	*   length between 6 and 15: true
	*/
	public void testPasswordStrength3() {
		assertEquals(-1, passwordStrength("passworD1"));
	}
	
	@Test
	/**
	* Test a password missing a capitalized char 
	*   contains special chars: true
	*   contains digits: true
	*   contains capitalized: false
	*   length between 6 and 15: true
	*/
	public void testPasswordStrength4() {
		assertEquals(-1, passwordStrength("password1_"));
	}
	
	@Test
	/**
	* Test a password length 
	*   contains special chars: true
	*   contains digits: true
	*   contains capitalized: true
	*   length between 6 and 15: false
	*/
	public void testPasswordStrength5() {
		assertEquals(-1, passwordStrength("evenmorepassworD1_"));
	}
	
	@Test
	/**
	* Test a password length and the capitalized char
	*   contains special chars: true
	*   contains digits: true
	*   contains capitalized: false
	*   length between 6 and 15: false
	*/
	public void testPasswordStrength6() {
		assertEquals(-1, passwordStrength("pad1_"));
	}
	
	
}