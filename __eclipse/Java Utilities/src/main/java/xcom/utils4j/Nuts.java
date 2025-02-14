

package xcom.utils4j ;


import org.slf4j.helpers.MessageFormatter ;


public class Nuts {

	public static void exit(String msg, Object... args) {

		if ( error(msg, args) )
			System.exit(0) ;
	}


	public static boolean error(String msg, Object... args) {

		boolean isError = false ;

		if ( args != null && args.length > 0 && args[0] instanceof Boolean )
			isError = (boolean) args[0] ;

		else
			isError = true ;

		if ( isError )
			System.err.println(MessageFormatter.arrayFormat(msg, args).getMessage()) ;


		return isError ;
	}
}
