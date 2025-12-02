

package xcom.utils4j.logging.lambda.api.interfaces ;


public interface ILoggerFactory {

	org.slf4j.ILoggerFactory getUnderlyingLoggerFactory() ;

	ILogger getLogger(String name) ;
}
