

package xcom.utils4j.tasks ;


import static java.awt.BorderLayout.CENTER ;
import static java.awt.BorderLayout.EAST ;
import static java.awt.BorderLayout.PAGE_END ;
import static java.awt.BorderLayout.PAGE_START ;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE ;

import java.awt.BorderLayout ;
import java.awt.CardLayout ;
import java.awt.Component ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;
import java.io.IOException ;
import java.util.Properties ;

import javax.imageio.ImageIO ;
import javax.swing.ImageIcon ;
import javax.swing.JButton ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;
import javax.swing.SwingUtilities ;
import javax.swing.UIManager ;
import javax.swing.UnsupportedLookAndFeelException ;

import org.fest.reflect.core.Reflection ;
import org.fest.reflect.exception.ReflectionError ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;
import org.slf4j.event.Level ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.resources.Props ;
import xcom.utils4j.tasks.api.interfaces.IExecutive ;
import xcom.utils4j.tasks.api.interfaces.IManager ;


public abstract class ExecutiveScaffold implements IExecutive {

	private static final Logger Logger = LoggerFactory.getLogger(ExecutiveScaffold.class) ;


	public static final String Props_AppTitle = "executive.app.title" ;
	public static final String Props_AppWindowBounds = "executive.app.window.bounds" ;
	public static final String Props_ManagerStartupClassname = "executive.manager.startup.classname" ;


	Class<? extends ExecutiveScaffold> executiveClass ;
	IManager manager ;
	String[] args ;
	Properties props ;
	String settings ;

	JFrame appWindow ;
	MonitorGUI gui ;


	@Log
	public ExecutiveScaffold(final String[] args) {

		this.args = args ;
		executiveClass = this.getClass() ;

		props = Props.load(executiveClass) ;
		settings = Props.loadAsString(executiveClass) ;

		appWindow = new JFrame() ;
		gui = new MonitorGUI() ;


		try {
			final String classname = props.getProperty(Props_ManagerStartupClassname) ;
			Logger.debug("{}: {}", Props_ManagerStartupClassname, classname) ;

			manager = (IManager) Reflection.constructor() //
					.withParameterTypes(String[].class, Properties.class, MonitorGUI.class) //
					.in(Reflection.type(classname).load()) //
					.newInstance(args, props, gui) ;

			Logger.debug("manager: {}", manager) ;
		}
		catch ( final ReflectionError e ) {
			e.printStackTrace() ;
		}
	}


	@Log
	public void bootstrapSwing(final JFrame appWindow, final Properties props, final IManager manager, final String settings) {

		SwingUtilities.invokeLater(() -> createGUI(appWindow, props, manager, settings)) ;
	}


	@Log
	void createGUI(final JFrame window, final Properties props, final IManager manager, final String settings) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
			window.setDefaultCloseOperation(EXIT_ON_CLOSE) ;

			window.setTitle(props.getProperty(Props_AppTitle, "Executive")) ;

			final String[] bounds_ = props.getProperty(Props_AppWindowBounds, "110,50,600,600").split("[,]") ;
			final int[] bounds = new int[bounds_.length] ;
			for ( int i = 0; i < bounds_.length; i++ )
				bounds[i] = Integer.parseInt(bounds_[i]) ;
			window.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]) ;

			window.addWindowListener(new WindowListner()) ;

			window.add(createGUIBanner(props), PAGE_START) ;
			window.add(createGUIDasboards()) ;

			((CardLayout) gui.getDashboards().getLayout()).show(gui.getDashboards(), gui.getCurrentDashboard()) ;
			window.revalidate() ;
			window.repaint() ;

			window.setVisible(true) ;
		}
		catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignore ) {}
	}


	@Log
	Component createGUIBanner(final Properties props) {

		final JPanel banner = new JPanel(new BorderLayout()) ;

		{
			final JPanel panel = new JPanel() ;

			// Create the Copyright ...
			panel.add(new JLabel("Engineered by BDS Essentials  (Emergence)"), CENTER) ;

			// Create the Settings button from an image ...
			try {
				final String icon = "/" + (ExecutiveScaffold.class.getPackage().getName()).replaceAll("[\\.]", "/") + "/settings.png" ;
				final JButton settingsButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource(icon)))) ;
				settingsButton.addActionListener(new SettingsListener()) ;
				panel.add(settingsButton, CENTER) ;
			}
			catch ( final IOException e ) {}

			banner.add(panel, EAST) ;
		}

		{
			banner.add(gui.getBillboards(), CENTER) ;
		}


		return banner ;
	}


	@Log
	Component createGUIDasboards() {

		gui.getDashboards().add(new JPanel(), "blank") ;
		gui.getDashboards().add(createGUISettings(settings), "settings") ;

		return gui.getDashboards() ;
	}


	@Log
	Component createGUISettings(final String settings) {

		final JPanel editor = new JPanel(new BorderLayout()) ;

		editor.add(new JScrollPane(new JTextArea(settings)), CENTER) ;

		// footer
		{
			final JPanel footer = new JPanel() ;

			// Create the Save button ...
			final JButton save = new JButton("Save") ;
			save.addActionListener(new SettingsSaveListener()) ;
			footer.add(save, CENTER) ;

			// Create the Cancel button ...
			final JButton concel = new JButton("Cancel") ;
			concel.addActionListener(new SettingsCancelListener()) ;
			footer.add(concel, CENTER) ;

			editor.add(footer, PAGE_END) ;
		}


		return editor ;
	}


	//
	// IExecutive implementation ...
	//

	@Override
	public Properties getProps() {
		return props ;
	}


	@Override
	public String getSettings() {
		return settings ;
	}

	@Override
	public IManager getManager() {
		return manager ;
	}

	@Override
	public JFrame getWindow() {
		return appWindow ;
	}


	//
	// Swing listeners ...
	//

	class SettingsListener implements ActionListener {

		@Log
		@Override
		public void actionPerformed(final ActionEvent event) {
			((CardLayout) gui.getDashboards().getLayout()).show(gui.getDashboards(), "settings") ;
		}
	}

	class SettingsSaveListener implements ActionListener {

		@Log
		@Override
		public void actionPerformed(final ActionEvent event) {

			Props.saveFromString(executiveClass) ;

			((CardLayout) gui.getDashboards().getLayout()).show(gui.getDashboards(), gui.getCurrentDashboard()) ;
		}
	}

	class SettingsCancelListener implements ActionListener {

		@Log
		@Override
		public void actionPerformed(final ActionEvent event) {
			((CardLayout) gui.getDashboards().getLayout()).show(gui.getDashboards(), gui.getCurrentDashboard()) ;
		}
	}


	class WindowListner extends WindowAdapter {

		@Log(value = Level.INFO)
		@Override
		public void windowClosing(final WindowEvent e) {
			e.getWindow().dispose() ;
		}
	}
}
