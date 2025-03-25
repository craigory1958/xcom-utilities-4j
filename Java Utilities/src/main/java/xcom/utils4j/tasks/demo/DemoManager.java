

package xcom.utils4j.tasks.demo ;


import static java.awt.BorderLayout.CENTER ;

import java.awt.CardLayout ;
import java.awt.Image ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.io.IOException ;
import java.lang.annotation.Annotation ;
import java.lang.reflect.InvocationTargetException ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Properties ;
import java.util.Set ;

import javax.imageio.ImageIO ;
import javax.swing.ImageIcon ;
import javax.swing.JButton ;
import javax.swing.JComboBox ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

import org.fest.reflect.core.Reflection ;
import org.fest.reflect.exception.ReflectionError ;
import org.reflections.Reflections ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.ManagerScaffold ;
import xcom.utils4j.tasks.MonitorGUI ;
import xcom.utils4j.tasks.api.interfaces.IWorkable ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


public class DemoManager extends ManagerScaffold {

	private static final Logger Logger = LoggerFactory.getLogger(DemoManager.class) ;


	public static final String Props_AnnotatedManagerClassname = "annotated.manager.scan.annotation.classname" ;
	public static final String Props_AnaotatedManagerButtonName = "annotated.manager.button.name" ;
	public static final String Props_AnaotatedManagerScanClasspath = "annotated.manager.scan.classpath" ;


	Map<String, Class<?>> launchables ;
	boolean hasMonitor ;

	JComboBox<String> dropdown ;


	@Log
	public DemoManager(final String[] args, final Properties props, final MonitorGUI gui) {

		super(args, props, gui) ;

		final String classpath = props.getProperty(Props_AnaotatedManagerScanClasspath) ;
		final String annotation = props.getProperty(Props_AnnotatedManagerClassname) ;
		launchables = scanAndLoadAnnotatedLaunchers(classpath, annotation) ;

		{
			try {
				final JPanel _dashboard = new JPanel(new CardLayout()) ;

				final String resSpec = "/" + (this.getClass().getPackage().getName()).replaceAll("\\.", "/") + "/under_construction.png" ;
				Logger.debug("under_construction.png: @{}", resSpec) ;

				final ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResource(resSpec))) ;
				final ImageIcon scaled = new ImageIcon(image.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)) ;

				_dashboard.add(new JLabel(scaled), CENTER) ;

				gui.setDashboard(_dashboard) ;
			}
			catch ( final IOException e ) {
				e.printStackTrace() ;
			}
		}

		{
			final JPanel _billboard = new JPanel() ;

			dropdown = new JComboBox<>() ;
			dropdown.addActionListener(new LaunchableComboBoxListener()) ;
			for ( final String manager : launchables.keySet() )
				dropdown.addItem(manager) ;
			_billboard.add(dropdown, CENTER) ;

			final JButton launch = new JButton(props.getProperty(Props_AnaotatedManagerButtonName, "Launch")) ;
			launch.addActionListener(new LaunchButtonListener()) ;
			_billboard.add(launch, CENTER) ;

			gui.setBillboard(_billboard) ;
		}
	}


	@Log
	static Map<String, Class<?>> scanAndLoadAnnotatedLaunchers(final String classpath, final String annotation) {

		Logger.info("Scanning classpath '{}.*' for {} ...", classpath, annotation) ;

		final Map<String, Class<?>> launchables = new HashMap<>() ;

		try {
			@SuppressWarnings("unchecked")
			final Set<Class<?>> _launchables = new Reflections(classpath).getTypesAnnotatedWith((Class<? extends Annotation>) Class.forName(annotation)) ;
			Logger.info("found {} annotations in classpath: |{}|", _launchables.size(), _launchables) ;

			for ( final Class<?> _launchable : _launchables )
				for ( final Annotation a : _launchable.getAnnotations() )
					if ( a.annotationType().getName().equals(annotation) )
						if ( !(Boolean) a.annotationType().getDeclaredMethod("disabled").invoke(a, (Object[]) null) )
							launchables.put((String) a.annotationType().getDeclaredMethod("selector").invoke(a, (Object[]) null), _launchable) ;
		}
		catch ( ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e ) {
			e.printStackTrace() ;
		}

		Logger.debug("launchables: |{}|", launchables) ;

		return launchables ;
	}


	@Log
	@Override
	public void observeIWorkerObserver(final IWorkerObserverEvent event) {}


	//
	// Swing listeners ...
	//

	class LaunchableComboBoxListener implements ActionListener {

		@Log
		@Override
		public void actionPerformed(final ActionEvent event) {}
	}


	class LaunchButtonListener implements ActionListener {

		@Log
		@Override
		public void actionPerformed(final ActionEvent event) {

			try {
				final String classname = launchables.get(dropdown.getSelectedItem()).getName() ;
				Logger.debug("dropdown: {}", classname) ;

				final IWorkable launchable = (IWorkable) Reflection.constructor() //
						.withParameterTypes(String[].class, Properties.class) //
						.in(Reflection.type(classname).load()) //
						.newInstance(getArgs(), getProps()) ;
				Logger.debug("launchable: {}", launchable) ;

				launchable.work(getListener()) ;
			}
			catch ( final ReflectionError e ) {
				e.printStackTrace() ;
			}


			if ( !hasMonitor )
				try {
					final String classname = getProps().getProperty("supervisor.monitor.classname") ;
					Logger.debug("{}: {}", "supervisor.monitor.classname", classname) ;

					final ManagerScaffold monitor = (ManagerScaffold) Reflection.constructor() //
							.withParameterTypes(String[].class, Properties.class, MonitorGUI.class) //
							.in(Reflection.type(classname).load()) //
							.newInstance(getArgs(), getProps(), getGUI()) ;
					Logger.debug("monitor: {}", monitor) ;

					hasMonitor = true ;
				}
				catch ( final ReflectionError e ) {
					e.printStackTrace() ;
				}
		}
	}
}
