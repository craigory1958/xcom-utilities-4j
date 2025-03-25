

package xcom.utils4j ;


import org.junit.Assert ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Contains additional assertions for unit testing
 */
public abstract class JUnits {

	/**
	 * @param msg
	 * @param expected
	 * @param e
	 */
	@Log
	public static void assertExceptionEquals(final String msg, final Throwable expected, final Throwable e) {

		final String actualClassname = e.getClass().getName() ;
		String expectedClassname = null ;

		if ( expected != null )
			expectedClassname = expected.getClass().getName() ;

		if ( !actualClassname.equals(expectedClassname) )
			e.printStackTrace() ;

		Assert.assertEquals(msg, expectedClassname, actualClassname) ;
	}
}
