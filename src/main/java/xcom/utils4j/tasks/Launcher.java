

package xcom.utils4j.tasks ;


import static java.awt.BorderLayout.CENTER ;
import static java.awt.BorderLayout.EAST ;
import static java.awt.BorderLayout.PAGE_END ;
import static java.awt.BorderLayout.PAGE_START ;
import static java.awt.BorderLayout.WEST ;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE ;

import java.awt.BorderLayout ;
import java.awt.CardLayout ;
import java.awt.Insets ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.io.File ;
import java.io.IOException ;
import java.net.URISyntaxException ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Set ;

import javax.imageio.ImageIO ;
import javax.swing.ImageIcon ;
import javax.swing.JButton ;
import javax.swing.JComboBox ;
import javax.swing.JComponent ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;
import javax.swing.SwingUtilities ;
import javax.swing.UIManager ;
import javax.swing.UnsupportedLookAndFeelException ;

import org.apache.commons.io.FileUtils ;
import org.fest.reflect.core.Reflection ;
import org.fest.reflect.exception.ReflectionError ;
import org.reflections.Reflections ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.tasks.annotations.Launchable ;


/**
 * @author Craig Gregory
 *
 */
public class Launcher extends JPanel {

	private static final long serialVersionUID = -6075053348835664026L ;

	private static final Logger Logger = LoggerFactory.getLogger(Launcher.class) ;


	static final String Title = "Launcher" ;


	JFrame frame ;
//	JButton runButton ;
	JComboBox<String> launchablesComboBox ;
	JTextArea log ;
	JPanel panel ;
//	JButton saveButton ;
//	JButton cancelButton ;
	JTextArea settings ;

	Map<String, Class<?>> launchables ;


	/**
	 * @param frame
	 */
	public Launcher(final JFrame frame) {

		super(new BorderLayout()) ;

		this.frame = frame ;
		launchables = new HashMap<>() ;


		// Create banner w/controls (GUI top) ...
		{
			final JPanel banner = new JPanel(new BorderLayout()) ;

			{
				final JPanel panel = new JPanel() ;

				// Create the Copyright ...
				final JPanel copyright = new JPanel() ;
				copyright.add(new JLabel("Engineered by BDS Essentials  (Emergence)"), CENTER) ;
				panel.add(copyright, WEST) ;

				// Create the Settings button from an image ...
				try {
					final JButton settingsButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("settings.png")))) ;
					settingsButton.addActionListener(new SettingsButtonListener()) ;
					panel.add(settingsButton, EAST) ;
				}
				catch ( final IOException ex ) {}

				//
				banner.add(panel, EAST) ;
			}

			{
				final JPanel panel = new JPanel() ;

				// Create the Run button ...
				JButton run = new JButton("Run") ;
				run.addActionListener(new RunButtonListener()) ;
				panel.add(run, CENTER) ;

				// Create the Runners combo box ...
				final String classpath = "gov.ne.lance.testing.automation.tests" ;
				Logger.info("Scanning classpath '" + classpath + ".*' for launchables ...") ;
				final Set<Class<?>> _launchables = new Reflections(classpath).getTypesAnnotatedWith(Launchable.class) ;
				Logger.info("found {} runners in classpath '" + classpath + ".*': |{}|", _launchables.size(), _launchables) ;

				final String[] _combos = new String[_launchables.size()] ;
				int i = 0 ;
				for ( final Class<?> _launchable : _launchables ) {
					_combos[i++] = _launchable.getSimpleName() ;
					launchables.put(_launchable.getSimpleName(), _launchable) ;
				}

				launchablesComboBox = new JComboBox<String>(_combos) ;
				panel.add(launchablesComboBox, CENTER) ;

				//
				banner.add(panel, CENTER) ;
			}

			//
			add(banner, PAGE_START) ;
		}

		// Create views ...
		{
			panel = new JPanel(new CardLayout()) ;

			// Create logging view ...
			{
				log = new JTextArea() ;
				log.setMargin(new Insets(5, 5, 5, 5)) ;
				log.setEditable(false) ;

				panel.add(new JScrollPane(log), "log") ;
			}

			// Create settings view ...
			{
				final JPanel editor = new JPanel(new BorderLayout()) ;
				{
					String props = "" ;
					try {
						props = FileUtils.readFileToString(new File(this.getClass().getResource("settings.properties").toURI()), "UTF-8") ;
					}
					catch ( NullPointerException | IOException | URISyntaxException ex ) {}

					settings = new JTextArea(props) ;

					editor.add(new JScrollPane(settings), CENTER) ;
				}

				// footer
				{
					final JPanel footer = new JPanel() ;

					// Create the Run button ...
					JButton save = new JButton("Save") ;
					save.addActionListener(new SaveButtonListener()) ;
					footer.add(save, CENTER) ;

					// Create the Cancel button ...
					JButton concel = new JButton("Cancel") ;
					concel.addActionListener(new CancelButtonListener()) ;
					footer.add(concel, CENTER) ;

					editor.add(footer, PAGE_END) ;
				}

				//
				panel.add(editor, "settings") ;
			}

			//
			add(panel, CENTER) ;
		}

		((CardLayout) panel.getLayout()).show(panel, "log") ;
	}


	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		// Schedule a job for the event-dispatching thread ...
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
				}
				catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex ) {}

				bootstrapGUI() ;
			}
		}) ;
	}


	/**
	 * Create the GUI and show it.
	 *
	 * For thread safety, this method should be invoked from the event-dispatching thread.
	 */
	static void bootstrapGUI() {

		// Create and set up the window ...
		final JFrame frame = new JFrame(Title) ;

		final JComponent c = new Launcher(frame) ;
		c.setOpaque(true) ;

		frame.setContentPane(c) ;
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		frame.setBounds(110, 50, 1000, 400) ;

		frame.setVisible(true) ;
	}


	/**
	 *
	 */
	class RunButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {

			final String classname = launchables.get((String) launchablesComboBox.getSelectedItem()).getName() ;
			log.append("\nStarting " + classname + " ...") ;

			try {
				final Worker worker = (Worker) Reflection.constructor().in(Reflection.type(classname).load()).newInstance() ;
				worker.setFrame(frame) ;
				
				worker.setRunable(true);

//				{
//					final List<URL> urls = new ArrayList<URL>() ;
//
//					final Properties props = new Properties() ;
//					props.load(getClass().getResourceAsStream("settings.properties")) ;
//					for ( final Entry<Object, Object> entry : props.entrySet() ) {
//						Logger.warn("prop: |{}|", entry) ;
//
//						if ( entry.getKey().toString().startsWith("hints.jar.url.") )
//							urls.add(new URL(entry.getValue().toString())) ;
//					}
//
//
//					for ( final URL it : urls.toArray(new URL[0]) )
//						System.out.println(it) ;
//
//
//					worker.setClassLoader(URLClassLoader.newInstance(urls.toArray(new URL[0]))) ;
//					Logger.warn("loader: |{}|", worker.getClassLoader()) ;
//
//
//					final Enumeration<URL> them = worker.getClassLoader().getResources("") ;
//					while ( them.hasMoreElements() )
//						System.out.println(them.nextElement()) ;
//				}

				worker.initialize() ;

				if ( worker.isRunable() )
					new Monitor(frame, log, worker, 0, 0, 500, 400) ;
				else
					log.append("\n    could not start: " + worker.getMessage()) ;
			}
//			catch ( ReflectionError | IOException ex ) {
			catch ( ReflectionError ex ) {
				log.append("\n    could not start: Error instantiating " + classname + "; " + ex) ;
			}
		}
	}


	/**
	 *
	 */
	class SettingsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {
			((CardLayout) panel.getLayout()).show(panel, "settings") ;
		}
	}


	/**
	 *
	 */
	class SaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {

			try {
				FileUtils.writeStringToFile(new File(getClass().getResource("settings.properties").toURI()), settings.getText(), "UTF-8") ;
			}
			catch ( IOException | URISyntaxException ex ) {
				Logger.error("Error writting setting file: |{}|", ex) ;
			}

			((CardLayout) panel.getLayout()).show(panel, "log") ;
		}
	}


	/**
	 *
	 */
	class CancelButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {
			((CardLayout) panel.getLayout()).show(panel, "log") ;
		}
	}
}
