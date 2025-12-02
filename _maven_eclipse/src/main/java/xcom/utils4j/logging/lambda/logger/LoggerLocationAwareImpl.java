

package xcom.utils4j.logging.lambda.logger ;


import java.util.function.Supplier ;

import xcom.utils4j.logging.lambda.LambdaLoggers ;
import xcom.utils4j.logging.lambda.api.interfaces.ILogger ;


/**
 * LambdaLoggerLocationAwareImpl is for slf4j implementation that supports {@link org.slf4j.spi.LocationAwareLogger}.
 *
 * Logback supports LocationAwareLogger.
 */
public class LoggerLocationAwareImpl implements ILogger, org.slf4j.spi.LocationAwareLogger {

	/** MUST use LambdaLogger's FQCN because LambdaLogger default methods do logs */
	public static final String FQCN = ILogger.class.getName() ;

	/**
	 * Real Slf4j logger instance
	 */
	private final org.slf4j.spi.LocationAwareLogger underlyingLogger ;

	public LoggerLocationAwareImpl(final org.slf4j.spi.LocationAwareLogger underlyingLogger) {
		if ( underlyingLogger == null )
			throw new IllegalArgumentException("underlyingLogger must not be null.") ;

		this.underlyingLogger = underlyingLogger ;
	}

	/** return logger's fully qualified class name for LocationAwareLogger */
	protected String getLoggerFQCN() {
		return FQCN ;
	}

	@Override
	public org.slf4j.Logger getUnderlyingLogger() {
		return underlyingLogger ;
	}

	@Override
	public void doLog(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final Supplier<String> msgSupplier, final Throwable t) {
		if ( LambdaLoggers.isLogLevelEnabled(underlyingLogger, level, marker) )
			underlyingLogger.log(marker, getLoggerFQCN(), level.toInt(), msgSupplier.get(), null, t) ;
	}

	@Override
	public void doLog(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final String format, final Supplier<?>[] argSuppliers,
			final Throwable t) {
		if ( LambdaLoggers.isLogLevelEnabled(underlyingLogger, level, marker) )
			underlyingLogger.log(marker, getLoggerFQCN(), level.toInt(), format, LambdaLoggers.argSuppliersToArgs(argSuppliers), t) ;
	}

	@Override
	public void doLog(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final String format, final Object[] arguments, final Throwable t) {
		if ( LambdaLoggers.isLogLevelEnabled(underlyingLogger, level, marker) )
			underlyingLogger.log(marker, getLoggerFQCN(), level.toInt(), format, arguments, t) ;
	}

	@Override
	public void log(final org.slf4j.Marker marker, final String fqcn, final int level, final String message, final Object[] arguments, final Throwable t) {
		underlyingLogger.log(marker, fqcn, level, message, arguments, t) ;
	}
}
