

package xcom.utils4j.logging.lambda ;


import java.util.function.Supplier ;


public abstract class LambdaLoggers {

	private LambdaLoggers() {
		throw new UnsupportedOperationException() ;
	}

	/**
	 * check if log level is enabled in the underlying logger
	 *
	 * @param underlyingLogger
	 *            real Slf4j Logger implementation
	 * @param logLevel
	 *            log level
	 * @param marker
	 *            marker
	 * @return true if log level is enabled or false.
	 */
	public static boolean isLogLevelEnabled(final org.slf4j.Logger underlyingLogger, final org.slf4j.event.Level logLevel, final org.slf4j.Marker marker) {
		switch ( logLevel ) {
			case TRACE:
				if ( marker == null )
					return underlyingLogger.isTraceEnabled() ;
				return underlyingLogger.isTraceEnabled(marker) ;
			case DEBUG:
				if ( marker == null )
					return underlyingLogger.isDebugEnabled() ;
				return underlyingLogger.isDebugEnabled(marker) ;
			case INFO:
				if ( marker == null )
					return underlyingLogger.isInfoEnabled() ;
				return underlyingLogger.isInfoEnabled(marker) ;
			case WARN:
				if ( marker == null )
					return underlyingLogger.isWarnEnabled() ;
				return underlyingLogger.isWarnEnabled(marker) ;
			case ERROR:
				if ( marker == null )
					return underlyingLogger.isErrorEnabled() ;
				return underlyingLogger.isErrorEnabled(marker) ;
			default:
				break ;
		}
		return false ;
	}

	public static Object[] argSuppliersToArgs(final Supplier<?>... argSuppliers) {
		if ( argSuppliers == null )
			return null ;

		final Object[] args = new Object[argSuppliers.length] ;
		for ( int i = 0; i < argSuppliers.length; i++ )
			args[i] = argSuppliers[i].get() ;
		return args ;
	}
}
