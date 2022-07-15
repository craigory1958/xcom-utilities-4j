

package xcom.utils4j ;


import org.junit.Assert ;

import xcom.utils4j.logging.annotations.Log ;


/**
 * Contains additional assertions for unit testing
 */
public class JUnits {

	/**
	 * @param msg
	 * @param expected
	 * @param ex
	 */
	@Log
	public static void assertExceptionEquals(final String msg, final Throwable expected, final Throwable ex) {

		final String actualClassname = ex.getClass().getName() ;
		String expectedClassname = null ;

		if ( expected != null )
			expectedClassname = expected.getClass().getName() ;

		if ( !actualClassname.equals(expectedClassname) )
			ex.printStackTrace() ;

		Assert.assertEquals(msg, expectedClassname, actualClassname) ;
	}
}
