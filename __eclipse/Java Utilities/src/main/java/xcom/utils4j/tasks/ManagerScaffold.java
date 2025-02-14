

package xcom.utils4j.tasks ;


import java.util.Properties ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.api.interfaces.IManager ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserver ;


public abstract class ManagerScaffold implements IManager, IWorkerObserver {

	String[] args ;
	Properties props ;
	MonitorGUI gui ;


	@Log
	public ManagerScaffold(final String[] args, final Properties props, final MonitorGUI gui) {

		this.args = args ;
		this.props = props ;
		this.gui = gui ;
	}


	//
	// IManager implementation ...
	//

	@Override
	final public String[] getArgs() {
		return args ;
	}

	@Override
	final public Properties getProps() {
		return props ;
	}

	@Override
	final public IWorkerObserver getListener() {
		return this ;
	}


	public MonitorGUI getGUI() {
		return gui ;
	}
}
