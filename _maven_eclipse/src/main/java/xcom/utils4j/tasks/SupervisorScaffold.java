

package xcom.utils4j.tasks ;


import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Properties ;

import javax.swing.JDialog ;

import org.fest.reflect.core.Reflection ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.api.interfaces.IWorkable ;
import xcom.utils4j.tasks.api.interfaces.IWorker ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserver ;


@Log
public abstract class SupervisorScaffold extends JDialog implements IWorkable, IWorkerObserver {

	private static final long serialVersionUID = -6095278690239591415L ;
	private static final Logger Logger = LoggerFactory.getLogger(SupervisorScaffold.class) ;


	public static final String Props_MonitorClassname = "supervisor.monitor.classname" ;
	public static final String Props_ThreadsDefault = "supervisor.threads.default" ;
	public static final String Props_ThreadsDefault_Default = "1" ;
	public static final String Props_ThreadsPrefix = "supervisor.thread.prefix" ;
	public static final String Props_ThreadsPrefix_Default = "Thread" ;


	Boolean isError ;
	Boolean isLaunchable ;
	String[] args ;
	Properties props ;
	List<IWorker> workers ;


	public void isError(final Boolean isError) {
		this.isError = isError ;
	}


	public void isLaunchable(final Boolean isLaunchable) {
		this.isLaunchable = isLaunchable ;
	}


	public String[] getArgs() {
		return args ;
	}


	public Properties getProps() {
		return props ;
	}

	public SupervisorScaffold setProps(final Properties props) {
		this.props = props ;
		return this ;
	}


	public SupervisorScaffold(final String[] args, final Properties props) {

		this.args = args ;
		this.props = props ;

		workers = new ArrayList<>() ;

		isError = false ;
		isLaunchable(true) ;
		createGUI() ;
		addWindowListener(new CloseSupervisorListener()) ;
	}


	abstract protected void createGUI() ;

	abstract protected void createGUIWorker(IWorker worker, SupervisorScaffold supervisor) ;

	abstract protected Class<?> extractClass() ;

	abstract protected String extractWorkerFromAnnotation() ;


	//
	// IWorkable implementation ...
	//

	@Override
	public Boolean isError() {
		return isError ;
	}


	@Override
	public Boolean isLaunchable() {
		return isLaunchable ;
	}


	//
	// ILauncher implementation ...
	//

	@Override
	final public void work(final IWorkerObserver observer) {

		final String classname = extractWorkerFromAnnotation() ;
		Logger.debug("{}: {}", "extractWorkerFromAnnotation()", classname) ;
		final int defaultNumOfThreads = Integer.valueOf(getProps().getProperty(Props_ThreadsDefault, Props_ThreadsDefault_Default)) ;
		Logger.debug("{}: {}", Props_ThreadsDefault, defaultNumOfThreads) ;
		final String threadPrefix = getProps().getProperty(Props_ThreadsPrefix, Props_ThreadsPrefix_Default) ;
		Logger.debug("{}: {}", Props_ThreadsPrefix, threadPrefix) ;

		for ( int w = 0; w < defaultNumOfThreads; w++ ) {

			final IWorker worker = (IWorker) Reflection.constructor().in(Reflection.type(classname).load()).newInstance() ;
			workers.add(worker) ;

			createGUIWorker(worker, this) ;

			worker.addObserver(this) ;
			worker.addObserver(observer) ;

			((Thread) worker).setName(threadPrefix + " " + worker.getClass().getSimpleName() + " (#" + w + ")") ;
			((Thread) worker).start() ;
		}
	}


	public void supervisorWindowClosing(final WindowEvent e) {

		for ( final IWorker worker : workers )
			((Thread) worker).interrupt() ;
	}


	//
	// Swing listeners ...
	//

	@Log
	public class CloseSupervisorListener extends WindowAdapter {

		@Override
		public void windowClosing(final WindowEvent e) {
			supervisorWindowClosing(e) ;
		}
	}
}
