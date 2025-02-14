

package xcom.utils4j.tasks.api.interfaces ;


public interface IWorkable {

	Boolean isError() ;

	Boolean isLaunchable() ;

	void work(IWorkerObserver observer) ;
}
