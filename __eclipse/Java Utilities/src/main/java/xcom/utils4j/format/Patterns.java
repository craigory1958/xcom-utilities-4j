

package xcom.utils4j.format ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Utility class for escaping special characters in Strings
 */
public abstract class Patterns {

	// The meta-characters supported by this API are: <([{\^-=$!|]})?*+.>
	public static final String MetaCharacters = "<([{\\^-=$!|]})?*+.>" ;


	@Log
	private Patterns() {
		throw new UnsupportedOperationException() ;
	}


	/**
	 * Convert pattern meta-characters to properly escaped characters.
	 *
	 * @param  metaCharacters
	 *                            Character string to convert.
	 * @return                string with properly escaped meta characters.
	 */
	@Log
	public static String escapeMetaCharacters(final String metaCharacters) {
		String results = "" ;

		for ( int i = 0; (i < metaCharacters.length()); i++ ) {
			final String ch = metaCharacters.substring(i, i + 1) ;

			if ( MetaCharacters.indexOf(ch) > -1 )
				results += "\\" ;

			results += ch ;
		}

		return results ;
	}
}
