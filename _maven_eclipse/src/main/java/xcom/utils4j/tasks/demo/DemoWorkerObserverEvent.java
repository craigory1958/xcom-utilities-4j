

package xcom.utils4j.tasks.demo ;


import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


@Log
final public class DemoWorkerObserverEvent implements IWorkerObserverEvent {

	Long id ;
	Integer progress ;
	Boolean isCompleted ;
	String msg ;


	public DemoWorkerObserverEvent(final long id, final String msg) {
		this.id = id ;
		this.msg = msg ;
	}


	public DemoWorkerObserverEvent(final long id, final int progress, final boolean isCompleted) {
		this.id = id ;
		this.progress = progress ;
		this.isCompleted = isCompleted ;
	}
}
