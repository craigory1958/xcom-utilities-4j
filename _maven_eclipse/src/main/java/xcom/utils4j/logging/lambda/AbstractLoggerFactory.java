

package xcom.utils4j.logging.lambda ;


import java.util.concurrent.ConcurrentHashMap ;

import xcom.utils4j.logging.lambda.api.interfaces.ILogger ;
import xcom.utils4j.logging.lambda.api.interfaces.ILoggerFactory ;


public abstract class AbstractLoggerFactory implements ILoggerFactory {

	private final org.slf4j.ILoggerFactory underlyingLoggerFactory ;

	private final ConcurrentHashMap<String, ILogger> loggers = new ConcurrentHashMap<>() ;

	public AbstractLoggerFactory(final org.slf4j.ILoggerFactory underlyingLoggerFactory) {
		this.underlyingLoggerFactory = underlyingLoggerFactory ;
	}

	@Override
	public org.slf4j.ILoggerFactory getUnderlyingLoggerFactory() {
		return underlyingLoggerFactory ;
	}

	@Override
	public ILogger getLogger(final String name) {
		return loggers.computeIfAbsent(name, this::doBuildLambdaLogger) ;
	}

	private ILogger doBuildLambdaLogger(final String name) {
		final org.slf4j.Logger underlyingLogger = org.slf4j.LoggerFactory.getLogger(name) ;
		return buildLambdaLogger(underlyingLogger) ;
	}

	/**
	 * build LambdaLogger object.
	 */
	public abstract ILogger buildLambdaLogger(org.slf4j.Logger underlyingLogger) ;
}
