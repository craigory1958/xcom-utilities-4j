

package xcom.utils4j.format ;


import org.junit.Assert ;
import org.junit.Test ;


/**
 * JUnit to test Strings class
 */
public class _Test_Strings {
	/**
	 * Positive test for method fillStringWithSpaces
	 */
	@Test
	public void passFillStringWithSpaces() {
		String expected = "          " ;// ten spaces
		String actual = Strings.fillStringWithSpaces(10) ;
		Assert.assertEquals("Did not get the right number of spaces back.", expected, actual) ;

		expected = "" ;
		actual = Strings.fillStringWithSpaces(0) ;
		Assert.assertEquals("Did not get the right number of spaces back.", expected, actual) ;
	}


	/**
	 * Exception testing for method fillStringWithSpaces
	 */
	@Test(expected = java.lang.NegativeArraySizeException.class)
	public void failFillStringWithSpacesBadArraySize() {
		final String expected = "" ;
		final String actual = Strings.fillStringWithSpaces(-1) ;// A String made of -1 spaces does not make sense.
		Assert.assertEquals("Did not get the expected exception.", expected, actual) ;
	}


	/**
	 * Positive test for fillString method
	 */
	@Test
	public void passFillString() {
		String expected = "          " ;
		String actual = Strings.fillString(10, ' ') ;
		Assert.assertEquals("Did not get the right number of characters back.", expected, actual) ;

		expected = "~~~" ;
		actual = Strings.fillString(3, '~') ;
		Assert.assertEquals("Did not get the right number of characters back.", expected, actual) ;

		expected = "" ;
		actual = Strings.fillString(0, 'h') ;
		Assert.assertEquals("Did not get the right number of characters back.", expected, actual) ;

		expected = "4444" ;
		actual = Strings.fillString(4, '4') ;
		Assert.assertEquals("Did not get the right number of characters back.", expected, actual) ;

		expected = "\n\n\n" ;
		actual = Strings.fillString(3, '\n') ;
		Assert.assertEquals("Did not get the right number of characters back.", expected, actual) ;
	}


	/**
	 * Exception test for fillString method
	 */
	@Test(expected = java.lang.NegativeArraySizeException.class)
	public void failFillStringBadArraySize() {
		final String expected = null ;
		final String actual = Strings.fillString(-1, ' ') ;// A String -1 characters long does not make sense.
		Assert.assertEquals("Did not get the expected exception.", expected, actual) ;
	}
}
