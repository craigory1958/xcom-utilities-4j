

package xcom.utils4j.logging.lambda.logger ;


import xcom.utils4j.logging.lambda.AbstractLoggerFactory ;
import xcom.utils4j.logging.lambda.api.interfaces.ILogger ;


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
	public ILogger buildLambdaLogger(final org.slf4j.Logger underlyingLogger) {
		if ( underlyingLogger instanceof org.slf4j.spi.LocationAwareLogger )
			return new LoggerLocationAwareImpl((org.slf4j.spi.LocationAwareLogger) underlyingLogger) ;
		return new LoggerPlainImpl(underlyingLogger) ;
	}
}
