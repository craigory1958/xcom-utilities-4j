

package xcom.utils4j.logging.lambda.logger ;


import java.util.function.Supplier ;

import xcom.utils4j.logging.lambda.LambdaLoggers ;
import xcom.utils4j.logging.lambda.api.interfaces.ILogger ;


/**
 * LambdaLoggerPlainImpl is for slf4j implementation that does not support {@link org.slf4j.spi.LocationAwareLogger}.
 * <p>
 * slf4j-simple does not support LocationAwareLogger.
 */
public class LoggerPlainImpl implements ILogger {
	/**
	 * Real Slf4j logger instance
	 */
	private final org.slf4j.Logger underlyingLogger ;

	public LoggerPlainImpl(final org.slf4j.Logger underlyingLogger) {
		if ( underlyingLogger == null )
			throw new IllegalArgumentException("underlyingLogger must not be null.") ;

		this.underlyingLogger = underlyingLogger ;
	}

	@Override
	public org.slf4j.Logger getUnderlyingLogger() {
		return underlyingLogger ;
	}

	@Override
	public void doLog(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final Supplier<String> msgSupplier, final Throwable t) {
		if ( !LambdaLoggers.isLogLevelEnabled(underlyingLogger, level, marker) )
			return ;

		logFormatted(marker, level, msgSupplier.get(), t) ;
	}

	private void logFormatted(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final String msg, final Throwable t) {
		switch ( level ) {
			case TRACE:
				underlyingLogger.trace(marker, msg, t) ;
				break ;
			case DEBUG:
				underlyingLogger.debug(marker, msg, t) ;
				break ;
			case INFO:
				underlyingLogger.info(marker, msg, t) ;
				break ;
			case WARN:
				underlyingLogger.warn(marker, msg, t) ;
				break ;
			case ERROR:
				underlyingLogger.error(marker, msg, t) ;
				break ;
			default:
				System.err.println("Log Level level " + level + " is unknown.") ;
				return ;
		}
	}

	@Override
	public void doLog(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final String format, final Supplier<?>[] argSuppliers,
			final Throwable t) {
		if ( !LambdaLoggers.isLogLevelEnabled(underlyingLogger, level, marker) )
			return ;

		if ( argSuppliers == null )
			logFormatted(marker, level, format, t) ;
		else {
			final org.slf4j.helpers.FormattingTuple formattingTuple =
					org.slf4j.helpers.MessageFormatter.arrayFormat(format, LambdaLoggers.argSuppliersToArgs(argSuppliers), t) ;
			logFormatted(marker, level, formattingTuple.getMessage(), formattingTuple.getThrowable()) ;
		}
	}

	@Override
	public void doLog(final org.slf4j.Marker marker, final org.slf4j.event.Level level, final String format, final Object[] arguments, final Throwable t) {
		if ( !LambdaLoggers.isLogLevelEnabled(underlyingLogger, level, marker) )
			return ;

		if ( arguments == null )
			logFormatted(marker, level, format, t) ;
		else {
			final org.slf4j.helpers.FormattingTuple formattingTuple = org.slf4j.helpers.MessageFormatter.arrayFormat(format, arguments, t) ;
			logFormatted(marker, level, formattingTuple.getMessage(), formattingTuple.getThrowable()) ;
		}
	}
}
