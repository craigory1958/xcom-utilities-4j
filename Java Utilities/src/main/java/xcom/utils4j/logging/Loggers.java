

package xcom.utils4j.logging ;


import org.apache.logging.log4j.message.ParameterizedMessage ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public abstract class Loggers {

	public static final String ConsoleLoggerName = "Console" ;

	public static final String ApplicationLoggerName = "AppLog" ;


	@Log
	private Loggers() {
		throw new UnsupportedOperationException() ;
	}


	@Log
	public static String message(final String msg, final Object... objs) {
		return new ParameterizedMessage(msg, objs).toString() ;
	}
}
