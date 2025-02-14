

package xcom.utils4j.format ;


public class Obfuscate {

	static final String luminated = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" ;
	static final String obfuscated = "aQp5oA8qZWnSrm0sXlEtDC6RkuFVT9vwGBYj4xHNU3Je2ydMcI1zbKfO7gLPhi" ;


	public static String obfuscate(final String s) {

		final char[] result = new char[s.length()] ;
		for ( int i = 0; i < s.length(); i++ ) {
			final char c = s.charAt(i) ;
			final int idx = luminated.indexOf(c) ;
			result[i] = (idx == -1 ? c : obfuscated.charAt(idx)) ;
		}

		return new String(result) ;
	}

	public static String luminate(final String s) {

		final char[] result = new char[s.length()] ;
		for ( int i = 0; i < s.length(); i++ ) {
			final char c = s.charAt(i) ;
			final int idx = obfuscated.indexOf(c) ;
			result[i] = (idx == -1 ? c : luminated.charAt(idx)) ;
		}

		return new String(result) ;
	}
}
