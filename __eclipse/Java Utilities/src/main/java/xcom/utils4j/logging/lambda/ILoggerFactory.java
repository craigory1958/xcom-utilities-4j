

package xcom.utils4j.logging.lambda ;


public interface ILoggerFactory {

	org.slf4j.ILoggerFactory getUnderlyingLoggerFactory() ;

	Logger getLogger(String name) ;
}
