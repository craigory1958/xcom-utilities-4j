

package xcom.utils4j.tasks.api.interfaces ;


import java.util.Properties ;


public interface IManager {

	String[] getArgs() ;

	Properties getProps() ;

	IWorkerObserver getListener() ;
}
