

package xcom.utils4j.logging.lambda.defaultlogger ;


import xcom.utils4j.logging.lambda.AbstractLoggerFactory ;
import xcom.utils4j.logging.lambda.Logger ;


/**
 * LambdaLoggerFactory for LambdaLoggerLocationAwareImpl and LambdaLoggerPlainImpl.
 *
 * @see LoggerLocationAwareImpl
 * @see LoggerPlainImpl
 */
public class DefaultLoggerFactory extends AbstractLoggerFactory {

	public DefaultLoggerFactory(final org.slf4j.ILoggerFactory underlyingLoggerFactory) {
		super(underlyingLoggerFactory) ;
	}

	@Override
	public Logger buildLambdaLogger(final org.slf4j.Logger underlyingLogger) {
		if ( underlyingLogger instanceof org.slf4j.spi.LocationAwareLogger )
			return new LoggerLocationAwareImpl((org.slf4j.spi.LocationAwareLogger) underlyingLogger) ;
		return new LoggerPlainImpl(underlyingLogger) ;
	}
}
