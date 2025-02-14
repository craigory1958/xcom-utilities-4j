

package xcom.utils4j.tasks.demo ;


import java.util.Random ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.WorkerScaffold ;


public class DemoWorker extends WorkerScaffold {

	private static final Logger Logger = LoggerFactory.getLogger(DemoWorker.class) ;


	public static final boolean ProgressNotCompleted = false ;
	public static final boolean ProgressCompleted = !ProgressNotCompleted ;


	@Log
	void process() throws InterruptedException {

		final Random random = new Random() ;
		int progress = 0 ;

		postEvent(0, ProgressNotCompleted) ;
		postEvent(getName() + ": Starting ...") ;
		Thread.sleep(1000 + random.nextInt(2000)) ;    // Sleep for at least one second to simulate "startup".


		postEvent(getName() + ":     working ...") ;
		for ( /* no init */ ; (progress < 50); progress += random.nextInt(10) ) {
			Thread.sleep(random.nextInt(1000)) ;
			postEvent(progress, ProgressNotCompleted) ;
		}

		postEvent(getName() + ":     working harder ...") ;
		postEvent(0, ProgressNotCompleted) ;    // Set progress to zero to start intermediate progress bar mode.
		for ( int i = 0; (i < 50); i += random.nextInt(10) )
			Thread.sleep(random.nextInt(1000)) ;


		postEvent(getName() + ":     working ...") ;
		postEvent(Math.min(progress, 99), ProgressNotCompleted) ;
		for ( /* no init */ ; (progress < 100); progress += random.nextInt(10) ) {
			Thread.sleep(random.nextInt(1000)) ;
			postEvent(progress, ProgressNotCompleted) ;
		}


		postEvent(getName() + ":     completing ...") ;
		Thread.sleep(1000 + random.nextInt(2000)) ;    // Sleep for at least one second to simulate "shutdown".

		postEvent(getName() + ": completed.") ;
	}


	@Log
	void postEvent(final String msg) {
		Logger.debug(msg) ;
		notifyObservers(new DemoWorkerObserverEvent(getId(), msg)) ;
	}


	@Log
	void postEvent(final int progress, final boolean isCompleted) {
		notifyObservers(new DemoWorkerObserverEvent(getId(), progress, isCompleted)) ;
	}


	//
	//
	//

	@Log
	@Override
	public void run() {

		postEvent(getName() + ": is initializing ...") ;

		postEvent(1, ProgressNotCompleted) ;    // Start progress bar.

		try {
			process() ;
		}
		catch ( final InterruptedException ignore ) {
			postEvent(getName() + "    interrupted ...") ;
		}

		postEvent(100, ProgressCompleted) ;    // Stop progress bar.
	}
}
