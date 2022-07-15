

package xcom.utils4j.format ;


import java.util.Arrays ;

import xcom.utils4j.logging.annotations.Log ;


/**
 * String utility class
 */
public class Strings {

	/**
	 *
	 */
	protected Strings() {}


	/**
	 * Returns a string with the number of spaces given as length parameter
	 *
	 * @param length
	 *            Number of spaces string will have
	 * @return String object with number of spaces
	 */
	@Log
	public static String fillStringWithSpaces(final int length) {
		return fillString(length, ' ') ;
	}


	/**
	 * Returns string containing the given character length amount of times
	 *
	 * @param length
	 *            Number of fill characters string will have
	 * @param fill
	 *            Character string will contain
	 * @return String containing the given character length amount of times
	 */
	@Log
	public static String fillString(final int length, final char fill) {

		String results = null ;

		final char[] chars = new char[length] ;
		Arrays.fill(chars, fill) ;
		results = new String(chars) ;

		return results ;
	}
}
