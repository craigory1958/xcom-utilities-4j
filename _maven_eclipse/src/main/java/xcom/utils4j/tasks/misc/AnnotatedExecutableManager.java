

package xcom.utils4j.tasks.misc ;


import static java.awt.BorderLayout.CENTER ;

import java.awt.CardLayout ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.lang.annotation.Annotation ;
import java.lang.reflect.InvocationTargetException ;
import java.util.Map ;
import java.util.Properties ;
import java.util.Set ;
import java.util.TreeMap ;

import javax.swing.JButton ;
import javax.swing.JComboBox ;
import javax.swing.JComponent ;
import javax.swing.JPanel ;

import org.fest.reflect.core.Reflection ;
import org.fest.reflect.exception.ReflectionError ;
import org.reflections.Reflections ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.tasks.ManagerScaffold ;
import xcom.utils4j.tasks.MonitorGUI ;
import xcom.utils4j.tasks.api.interfaces.IWorkerObserverEvent ;


@Log
public class AnnotatedExecutableManager extends ManagerScaffold {

	private static final Logger Logger = LoggerFactory.getLogger(AnnotatedExecutableManager.class) ;


	public static final String Props_AnnotatedManagerClassname = "annotated.manager.scan.annotation.classname" ;
	public static final String Props_AnaotatedManagerButtonName = "annotated.manager.button.name" ;
	public static final String Props_AnaotatedManagerScanClasspath = "annotated.manager.scan.classpath" ;


	Map<String, Class<?>> launchables ;
	boolean hasMonitor ;

	JComboBox<String> dropdown ;


	public AnnotatedExecutableManager(final String[] args, final Properties props, final MonitorGUI gui) {

		super(args, props, gui) ;

		final String classpath = props.getProperty(Props_AnaotatedManagerScanClasspath) ;
		final String annotation = props.getProperty(Props_AnnotatedManagerClassname) ;
		launchables = scanAndLoadAnnotatedLaunchers(classpath, annotation) ;

		gui.setBillboard(createGUIBillboard(props)) ;
		gui.setDashboard(createGUIDashboard(props)) ;
	}


	public JComponent createGUIBillboard(final Properties props) {

		final JPanel _billboard = new JPanel() ;

		dropdown = new JComboBox<>() ;
		dropdown.addActionListener(new LaunchableComboBoxListener()) ;
		for ( final String manager : launchables.keySet() )
			dropdown.addItem(manager) ;
		_billboard.add(dropdown, CENTER) ;

		final JButton launch = new JButton(props.getProperty(Props_AnaotatedManagerButtonName, "Launch")) ;
		launch.addActionListener(new LaunchButtonListener()) ;
		_billboard.add(launch, CENTER) ;


		return _billboard ;
	}


	public JComponent createGUIDashboard(final Properties props) {
		return new JPanel(new CardLayout()) ;
	}


	@SuppressWarnings("unchecked")
	static Map<String, Class<?>> scanAndLoadAnnotatedLaunchers(final String classpath, final String annotation) {

		Logger.info("Scanning classpath '{}.*' for {} ...", classpath, annotation) ;

		final Map<String, Class<?>> launchables = new TreeMap<>() ;

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


	@Override
	public void observeIWorkerObserver(final IWorkerObserverEvent event) {}


	//
	// Swing listeners ...
	//

	@Log
	class LaunchableComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {}
	}


	@Log
	class LaunchButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {


			if ( !hasMonitor )
				try {
					final String classname = launchables.get(dropdown.getSelectedItem()).getName() ;
					Logger.debug("dropdown: {}", classname) ;

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
