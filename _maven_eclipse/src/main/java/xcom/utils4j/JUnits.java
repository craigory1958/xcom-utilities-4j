

package xcom.utils4j ;


import org.junit.Assert ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Contains additional assertions for unit testing
 */
@Log
public abstract class JUnits {

	private JUnits() {
		throw new UnsupportedOperationException() ;
	}


	/**
	 * @param expected
	 * @param actual
	 */
	public static void assertExceptionEquals(final Throwable expected, final Throwable actual) {
		assertExceptionEquals(null, expected, actual) ;
	}


	/**
	 * @param msg
	 * @param expected
	 * @param actual
	 */
	public static void assertExceptionEquals(final String msg, final Throwable expected, final Throwable actual) {

		final String actualClassname = actual.getClass().getName() ;
		String expectedClassname = null ;

		if ( expected != null )
			expectedClassname = expected.getClass().getName() ;

		if ( !actualClassname.equals(expectedClassname) )
			actual.printStackTrace() ;

		if ( msg != null )
			Assert.assertEquals(msg, expectedClassname, actualClassname) ;
		else
			Assert.assertEquals(expectedClassname, actualClassname) ;
	}
}
