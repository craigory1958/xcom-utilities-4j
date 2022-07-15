

package xcom.utils4j.tasks ;


import java.awt.Component ;
import java.awt.Frame ;
import java.awt.HeadlessException ;
import java.awt.Toolkit ;
import java.io.File ;
import java.util.Random ;

import javax.swing.JDialog ;
import javax.swing.JFileChooser ;
import javax.swing.SwingWorker ;
import javax.swing.filechooser.FileNameExtensionFilter ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.tasks.annotations.Launchable ;


@Launchable
public class Worker extends SwingWorker<Void, Void> {

	private static final Logger Logger = LoggerFactory.getLogger(Worker.class) ;


	public static final boolean ProgressNotCompleted = false ;
	public static final boolean ProgressCompleted = !ProgressNotCompleted ;


	/**
	 *
	 */
	boolean runable = false ;


	public boolean isRunable() {
		return runable ;
	}


	public Worker setRunable(final boolean runable) {
		this.runable = runable ;
		return this ;
	}


	/**
	 *
	 */
	String message ;


	public String getMessage() {
		return message ;
	}


	public Worker setMessage(final String message) {
		this.message = message ;
		return this ;
	}


	/**
	 *
	 */
	Frame frame ;


	public Frame getFrame() {
		return frame ;
	}


	public Worker setFrame(final Frame frame) {
		this.frame = frame ;
		return this ;
	}


	/**
	 *
	 */
	ClassLoader classLoader ;


	public ClassLoader getClassLoader() {
		return classLoader ;
	}


	public Worker setClassLoader(final ClassLoader classLoader) {
		this.classLoader = classLoader ;
		return this ;
	}


	/**
	 *
	 */
	public void initialize() {}


	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	public Void doInBackground() {

		if ( isRunable() ) {
			postProgress(1, ProgressNotCompleted) ;    // Start progress bar.

			try {
				process() ;
			}
			catch ( final Throwable ex ) {
				ex.printStackTrace() ;
			}

			postProgress(100, ProgressCompleted) ;    // Stop progress bar.
		}

		return (null) ;
	}


	/**
	 * @throws Throwable
	 */
	public void process() throws Throwable {

		final Random random = new Random() ;
		int progress = 0 ;

		postProgress(0, ProgressNotCompleted) ;
		postMessage("Starting ...") ;

		// Sleep for at least one second to simulate "startup".
		try {
			Thread.sleep(1000 + random.nextInt(2000)) ;
		}
		catch ( final InterruptedException ignore ) {}


		postMessage("Working ...") ;
		for ( /* no init */ ; (progress < 50); progress += random.nextInt(10) ) {
			try {
				Thread.sleep(random.nextInt(1000)) ;
			}
			catch ( final InterruptedException ignore ) {}
			postProgress(progress, ProgressNotCompleted) ;
		}


		postMessage("Working harder ...") ;
		postMessage("launcher", "Working harder ...") ;
		postProgress(0, ProgressNotCompleted) ;    // Set progress to zero to start intermediate progress bar mode.
		for ( int i = 0; (i < 50); i += random.nextInt(10) )
			try {
				Thread.sleep(random.nextInt(1000)) ;
			}
			catch ( final InterruptedException ignore ) {}


		postMessage("Working ...") ;
		postProgress(Math.min(progress, 99), ProgressNotCompleted) ;
		for ( /* no init */ ; (progress < 100); progress += random.nextInt(10) ) {
			try {
				Thread.sleep(random.nextInt(1000)) ;
			}
			catch ( final InterruptedException ignore ) {}
			postProgress(progress, ProgressNotCompleted) ;
		}


		postMessage("Completing ...") ;
		// Sleep for at least one second to simulate "shutdown".
		try {
			Thread.sleep(1000 + random.nextInt(2000)) ;
		}
		catch ( final InterruptedException ignore ) {}
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	public void done() {
		firePropertyChange("launcher", "", "Done.") ;
		firePropertyChange("monitor", "", "Done.") ;
		Toolkit.getDefaultToolkit().beep() ;
	}


	/**
	 * @param frame
	 * @param filter
	 * @return
	 */
	public File chooseFile(final Frame frame, final FileNameExtensionFilter filter) {

		File results = null ;

		final JFileChooser chooser = new JFileChooser() {

			private static final long serialVersionUID = -6075057348835664026L ;

			@Override
			protected JDialog createDialog(final Component parent) throws HeadlessException {
				final JDialog jDialog = super.createDialog(parent) ;
				jDialog.setAlwaysOnTop(true) ;
				return jDialog ;
			}
		} ;

		chooser.setFileFilter(filter) ;

		if ( chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION ) {
			results = new File(chooser.getSelectedFile().getAbsolutePath()) ;
			Logger.trace("selected file: |{}|", results) ;
		}
		else
			setMessage("File selection cancelled by user.") ;

		return results ;
	}


	/**
	 * @param progress
	 * @param completed
	 */
	protected void postProgress(final int progress, final boolean completed) {

		setProgress((completed ? 100 : (progress < 100 ? progress : 99))) ;

		try {
			Thread.sleep(1) ;
		}
		catch ( final InterruptedException ex ) {
			ex.printStackTrace() ;
		}
	}


	/**
	 * @param msg
	 */
	protected void postMessage(final String msg) {

		firePropertyChange("monitor", "", msg) ;
		Logger.info(msg) ;
	}


	/**
	 * @param property
	 * @param msg
	 */
	protected void postMessage(final String property, final String msg) {

		firePropertyChange(property, "", msg) ;
		Logger.info(msg) ;
	}
}
