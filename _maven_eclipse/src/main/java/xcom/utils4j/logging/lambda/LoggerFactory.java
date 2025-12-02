

package xcom.utils4j.logging.lambda ;


import xcom.utils4j.logging.lambda.api.interfaces.ILogger ;
import xcom.utils4j.logging.lambda.api.interfaces.ILoggerFactory ;
import xcom.utils4j.logging.lambda.logger.DefaultLoggerFactory ;


/**
 * Create {@link ILogger} instance.
 */
public abstract class LoggerFactory {

	private static ILoggerFactory lambdaLoggerFactory ;
	static {
		lambdaLoggerFactory = new DefaultLoggerFactory(org.slf4j.LoggerFactory.getILoggerFactory()) ;
	}

	public static ILogger getLogger(final String name) {
		return getILambdaLoggerFactory().getLogger(name) ;
	}

	public static ILogger getLogger(final Class<?> clazz) {
		return getLogger(clazz.getName()) ;
	}

	public static ILoggerFactory getILambdaLoggerFactory() {
		return lambdaLoggerFactory ;
	}
}
