

package xcom.utils4j.tasks ;


import java.util.ArrayList ;
import java.util.List ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.api.interfaces.IWorker ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserver ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


@Log
public abstract class WorkerScaffold extends Thread implements IWorker {

	List<IWorkerObserver> observers ;


	public WorkerScaffold() {
		observers = new ArrayList<>() ;
	}


	//
	// IWorker implementation ...
	//

	@Override
	final public void notifyObservers(final IWorkerObserverEvent event) {
		for ( final IWorkerObserver observer : observers )
			observer.observeIWorkerObserver(event) ;
	}

	@Override
	final public void addObserver(final IWorkerObserver observer) {
		synchronized ( observers ) {
			observers.add(observer) ;
		}
	}

	@Override
	final public void removeObserver(final IWorkerObserver observer) {
		synchronized ( observers ) {
			observers.remove(observer) ;
		}
	}
}
