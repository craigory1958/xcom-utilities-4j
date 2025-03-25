

package xcom.utils4j.tasks.misc ;


import static java.awt.BorderLayout.CENTER ;

import java.awt.BorderLayout ;
import java.awt.Insets ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.util.Properties ;

import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;
import javax.swing.Timer ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;
import org.slf4j.event.Level ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.ManagerScaffold ;
import xcom.utils4j.tasks.MonitorGUI ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserver ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


/**
 * A dashboard for a <code>Launcher</code> (area under header).
 *
 * This dashboard displays the list of all <code>Worker</code> tasks threads.
 *
 * @author Craig J. Gregory
 *
 */
public class MonitorThreadsDashboard extends ManagerScaffold implements IWorkerObserver, ActionListener {

	private static final Logger Logger = LoggerFactory.getLogger(MonitorThreadsDashboard.class) ;


	String threadPrefix ;
	JTextArea log ;


	@Log
	MonitorThreadsDashboard(final String[] args, final Properties props, final MonitorGUI gui) {

		super(args, props, gui) ;

		threadPrefix = getProps().getProperty("supervisor.thread.prefix", "Thread") ;
		Logger.debug("{}: {}", "supervisor.thread.prefix", threadPrefix) ;

		{
			final JPanel monitor = new JPanel(new BorderLayout()) ;

			log = new JTextArea() ;
			log.setMargin(new Insets(5, 5, 5, 5)) ;
			log.setEditable(false) ;

			monitor.add(new JScrollPane(log), CENTER) ;

			gui.replaceDashboard(monitor) ;
		}

		{
			final Timer timer = new Timer(1000, this) ;
//			timer.setInitialDelay(5000) ;
			timer.start() ;
		}
	}


	//
	// IWorkerObserver implementation ...
	//
	@Log(value = Level.INFO)
	@Override
	public void observeIWorkerObserver(final IWorkerObserverEvent event) {}


	//
	// ActionListener implementation ...
	//

	/**
	 * Display list of threads on dashboard.
	 */
	@Log
	@Override
	public void actionPerformed(final ActionEvent e) {

		log.setText(null) ;

		for ( final Thread thread : Thread.getAllStackTraces().keySet() )
			if ( (thread.getThreadGroup() != null) && thread.getName().startsWith(threadPrefix) )
				log.append(thread.getThreadGroup().toString() + ", " + thread.getId() + ", " + thread.getName() + ", " + thread.getState() + ", "
						+ thread.getPriority() + "\n") ;
	}
}
