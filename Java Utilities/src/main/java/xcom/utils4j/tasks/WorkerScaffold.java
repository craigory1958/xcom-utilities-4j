

package xcom.utils4j.tasks ;


import java.util.ArrayList ;
import java.util.List ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.api.interfaces.IWorker ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserver ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


public abstract class WorkerScaffold extends Thread implements IWorker {

	List<IWorkerObserver> observers ;


	@Log
	public WorkerScaffold() {
		observers = new ArrayList<>() ;
	}


	//
	// IWorker implementation ...
	//

	@Log
	@Override
	final public void notifyObservers(final IWorkerObserverEvent event) {
		for ( final IWorkerObserver observer : observers )
			observer.observeIWorkerObserver(event) ;
	}

	@Log
	@Override
	final public void addObserver(final IWorkerObserver observer) {
		synchronized ( observers ) {
			observers.add(observer) ;
		}
	}

	@Log
	@Override
	final public void removeObserver(final IWorkerObserver observer) {
		synchronized ( observers ) {
			observers.remove(observer) ;
		}
	}
}
