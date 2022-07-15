

package xcom.utils4j.tasks ;


import static java.awt.BorderLayout.CENTER ;
import static java.awt.BorderLayout.PAGE_START ;

import java.awt.Insets ;
import java.beans.PropertyChangeEvent ;
import java.beans.PropertyChangeListener ;

import javax.swing.JDialog ;
import javax.swing.JFrame ;
import javax.swing.JPanel ;
import javax.swing.JProgressBar ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;


public class Monitor extends JDialog implements PropertyChangeListener {

	private static final long serialVersionUID = -6095278690239591415L ;


	JTextArea jLauncherLog ;
	JProgressBar jProgressBar ;
	JTextArea jLog ;


	public Monitor(final JFrame frame, final JTextArea log, final Worker worker) {

		super(frame) ;

		jLauncherLog = log ;
		initialize(worker, 0, 0, 0, 0) ;
	}


	public Monitor(final JFrame frame, final JTextArea log, final Worker worker, final int x, final int y, final int width, final int height) {

		super(frame) ;

		jLauncherLog = log ;
		initialize(worker, x, y, width, height) ;
	}


	@Override
	public void propertyChange(final PropertyChangeEvent event) {

		if ( event.getPropertyName() == "launcher" )
			jLauncherLog.append(event.getNewValue() + "\n") ;

		if ( event.getPropertyName() == "monitor" )
			jLog.append(event.getNewValue() + "\n") ;

		if ( event.getPropertyName() == "progress" ) {

			final int progress = Integer.valueOf(event.getNewValue().toString()) ;

			if ( progress == 0 )
				jProgressBar.setIndeterminate(true) ;
			else {
				jProgressBar.setIndeterminate(false) ;
				jProgressBar.setString(null) ;
				jProgressBar.setValue(progress) ;
			}
		}
	}


	void initialize(final Worker worker, final int x, final int y, final int width, final int height) {

		// Create the GUI ...

		final JPanel panel = new JPanel() ;

		jProgressBar = new JProgressBar(0, 100) ;
		jProgressBar.setValue(0) ;
		jProgressBar.setStringPainted(true) ;

		panel.add(jProgressBar) ;


		jLog = new JTextArea(4, 100) ;
		jLog.setMargin(new Insets(5, 5, 5, 5)) ;
		jLog.setEditable(false) ;

		add(new JScrollPane(jLog), CENTER) ;

		add(panel, PAGE_START) ;

		setBounds(Math.max(200, x), Math.max(100, y), Math.max(300, width), Math.max(200, height)) ;
		setVisible(true) ;

		//

		worker.addPropertyChangeListener(this) ;
		worker.execute() ;
	}
}
