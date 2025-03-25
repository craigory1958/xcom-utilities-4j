

package xcom.utils4j.logging.lambda ;


import xcom.utils4j.logging.lambda.defaultlogger.DefaultLoggerFactory ;


/**
 * Create {@link Logger} instance.
 */
public abstract class LoggerFactory {

	private static ILoggerFactory lambdaLoggerFactory ;
	static {
		lambdaLoggerFactory = new DefaultLoggerFactory(org.slf4j.LoggerFactory.getILoggerFactory()) ;
	}

	public static Logger getLogger(final String name) {
		return getILambdaLoggerFactory().getLogger(name) ;
	}

	public static Logger getLogger(final Class<?> clazz) {
		return getLogger(clazz.getName()) ;
	}

	public static ILoggerFactory getILambdaLoggerFactory() {
		return lambdaLoggerFactory ;
	}
}
