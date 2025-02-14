

package xcom.utils4j.tasks.api.interfaces ;


public interface IWorker {

	void notifyObservers(IWorkerObserverEvent event) ;

	void addObserver(IWorkerObserver observer) ;

	void removeObserver(IWorkerObserver observer) ;
}
