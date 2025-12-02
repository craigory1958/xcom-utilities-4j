

package xcom.utils4j.tasks.demo ;


import java.awt.BorderLayout ;
import java.awt.Insets ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Properties ;

import javax.swing.BoxLayout ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JProgressBar ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.resources.Props ;
import xcom.utils4j.tasks.SupervisorScaffold ;
import xcom.utils4j.tasks.api.interfaces.IWorker ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


@Log
@ADemoWorkable(selector = "Demo")
public class DemoSupervisor extends SupervisorScaffold {

	private static final long serialVersionUID = -6095278690239591415L ;


	Map<Long, JProgressBar> progressBars ;
	JPanel statusView ;
	JTextArea log ;


	public DemoSupervisor(final String[] args, final Properties props) {

		super(args, props) ;

		progressBars = new HashMap<>() ;

		setProps(Props.merge(this.getClass(), getProps())) ;
	}


	//
	// Supervisor implementation ...
	//

	@Override
	protected void createGUI() {

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)) ;
		setBounds(Math.max(200, 0), Math.max(100, 0), Math.max(300, 500), Math.max(200, 400)) ;
		setVisible(true) ;

		{
			statusView = new JPanel() ;
			add(statusView) ;

			statusView.setLayout(new BoxLayout(statusView, BoxLayout.Y_AXIS)) ;
		}

		{
			final JPanel panel = new JPanel(new BorderLayout()) ;
			add(panel, BoxLayout.Y_AXIS) ;

			log = new JTextArea(4, 100) ;
			log.setMargin(new Insets(5, 5, 5, 5)) ;
			log.setEditable(false) ;

			panel.add(new JScrollPane(log), BorderLayout.CENTER) ;
		}
	}


	@Override
	protected void createGUIWorker(final IWorker worker, final SupervisorScaffold supervisor) {

		final Thread thread = (Thread) worker ;

		{
			final JPanel panel = new JPanel() ;
			statusView.add(panel) ;

			final String label = thread.getThreadGroup().toString() + ", " + thread.threadId() + ", " + thread.getName() ;
			panel.add(new JLabel(label), BorderLayout.CENTER) ;

			final JProgressBar progressBar = new JProgressBar(0, 100) ;
			panel.add(progressBar, BorderLayout.EAST) ;

			progressBars.put(thread.threadId(), progressBar) ;

			progressBar.setValue(0) ;
			progressBar.setStringPainted(true) ;
		}

		log.append(thread.getName() + ": spawning worker " + worker + " ...\n") ;
	}

	@Override
	protected Class<?> extractClass() {
		return this.getClass() ;
	}

	@Override
	protected String extractWorkerFromAnnotation() {
		return this.getClass().getAnnotation(ADemoWorkable.class).worker() ;
	}

	@Override
	public void observeIWorkerObserver(final IWorkerObserverEvent event) {

		final DemoWorkerObserverEvent _event = (DemoWorkerObserverEvent) event ;
		JProgressBar _progressBar ;

		if ( _event.msg == null ) {

			_progressBar = progressBars.get(_event.id) ;

			if ( _event.progress == 0 )
				_progressBar.setIndeterminate(true) ;

			else {
				_progressBar.setIndeterminate(false) ;
				_progressBar.setString(null) ;
				_progressBar.setValue(_event.progress) ;
			}
		}
		else
			log.append(_event.msg + "\n") ;
	}
}
