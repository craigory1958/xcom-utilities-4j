

package xcom.utils4j.logging ;


import org.apache.logging.log4j.message.ParameterizedMessage ;


public abstract class Loggers {

	public static final String ConsoleLoggerName = "Console" ;

	public static final String ApplicationLoggerName = "AppLog" ;


	private Loggers() {
		throw new UnsupportedOperationException() ;
	}


	public static String message(final String msg, final Object... objs) {
		return new ParameterizedMessage(msg, objs).toString() ;
	}
}
